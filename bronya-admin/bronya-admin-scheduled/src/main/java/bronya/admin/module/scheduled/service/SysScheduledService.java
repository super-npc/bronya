package bronya.admin.module.scheduled.service;

import bronya.admin.module.db.distributed.domain.SysDistributedLock;
import bronya.admin.module.db.distributed.repository.SysDistributedLockRepository;
import bronya.admin.module.db.distributed.service.MySqlDistributeLockService;
import bronya.admin.module.scheduled.dto.TaskLog;
import bronya.shared.module.common.type.ProcessStatus;
import bronya.shared.module.util.TraceId;
import bronya.admin.module.scheduled.autoconfig.JobBeanPostProcessor;
import bronya.admin.module.scheduled.domain.SysScheduled;
import bronya.admin.module.scheduled.dto.CronRunningDto;
import bronya.admin.module.scheduled.dto.ScheduledConfig;
import bronya.admin.module.scheduled.dto.ScheduledHandler;
import bronya.admin.module.scheduled.repository.SysScheduledRepository;
import bronya.admin.module.scheduled.type.TriggerType;
import com.alibaba.ttl.TtlRecursiveTask;
import com.alibaba.ttl.TtlRunnable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.DateTime;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.date.StopWatch;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.io.watch.WatchUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.cron.CronUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysScheduledService {
    private final SysScheduledRepository scheduledRepository;
    private final SysDistributedLockRepository lockRepository;
    private final MySqlDistributeLockService mySqlDistributeLockService;

    private static final Logger taskLogger = LoggerFactory.getLogger("SCHEDULED_FILE_NAME");

    public void stopTask(SysScheduled sysScheduled) {
        CronRunningDto cronRunningDto = this.getCronInMemory(sysScheduled);
        if (cronRunningDto == null) {
            return;
        }
        boolean remove = CronUtil.remove(cronRunningDto.getHutoolScheduleId());
        if (remove) {
            JobBeanPostProcessor.CRON_RUNNING_MAP.remove(sysScheduled.getId());
        }
    }

    public void newTask(String beanName) {
        SysScheduled sysScheduled = scheduledRepository.findByBeanName(beanName);
        CronRunningDto cronRunningDto = JobBeanPostProcessor.CRON_RUNNING_MAP.get(sysScheduled.getId());
        if (cronRunningDto != null) {
            log.info("无法创建新任务,已经存在该任务:{}", beanName);
            return;
        }
        if (!sysScheduled.getEnable()) {
            log.info("数据库禁用定时器:{}", beanName);
            return;
        }
        ScheduledHandler handler = JobBeanPostProcessor.CRON_HANDLER.get(beanName);

        ScheduledConfig config = handler.config();
        String cron = StrUtil.isNotBlank(sysScheduled.getCron()) ? sysScheduled.getCron() : config.getDefaultCron();

        String cronId = CronUtil.schedule(cron, () -> {
            String name = STR."\{beanName},cron:\{handler.config().getDefaultCron()}";
            // StopWatch stopWatch = StopWatch.of(name);
            // stopWatch.start(traceIdStr);
                String mdcTradeId = TraceId.getMdcTradeId();
            if (StrUtil.isBlank(mdcTradeId)) {
                mdcTradeId = STR."task_\{IdUtil.objectId()}";
            }
                    TraceId.setMdcTraceId(mdcTradeId);
            boolean lockRes = false;
            CronRunningDto cronRunning = JobBeanPostProcessor.CRON_RUNNING_MAP.get(sysScheduled.getId());
            TaskLog taskLog = null;
            try {
                int lockTime = 30;
                lockRes = mySqlDistributeLockService.tryLock(beanName, lockTime);
                if (!lockRes) {
                    SysDistributedLock lockTimeByName = lockRepository.findLockTimeByName(beanName);
                    if (lockTimeByName == null) {
                        log.info("未获得锁,上个任务已经消费完成");
                        return;
                    }
                    DateTime unLockTime = DateUtil.offsetSecond(lockTimeByName.getCreateTime(), lockTime); // 超时时间
                    String timeout = DateUtil.formatBetween(new Date(), unLockTime);
                    log.info("未获得锁,上个任务挂锁:{},剩余解锁:{}", DateUtil.formatDateTime(lockTimeByName.getCreateTime()),
                        timeout);
                    return;
                }
                if (cronRunning != null) {
                    cronRunning.setProcess(ProcessStatus.RUNNING);
                }
                taskLog = new TaskLog();
                taskLog.setProcess(ProcessStatus.CREATED);
                taskLog.setBeanName(sysScheduled.getBeanName());
                taskLog.setCron(sysScheduled.getCron());
                taskLog.setParams(sysScheduled.getParams());
                taskLog.setStart(System.currentTimeMillis());
                Object execRes = handler.exec(TriggerType.automatic, sysScheduled.getParams());
                // taskStrong.after(taskLog, execRes);
                handler.onSuccess(execRes);
                taskLog.setProcess(ProcessStatus.SUCCESS);
                if (cronRunning != null) {
                    cronRunning.setProcess(ProcessStatus.SUCCESS);
                }
            } catch (Exception e) {
                // taskStrong.exception(name, taskLog, e);
                if (cronRunning != null) {
                    cronRunning.setProcess(ProcessStatus.FAIL);
                }
                if (taskLog != null) {
                    taskLog.setProcess(ProcessStatus.FAIL);
                }
                try {
                    handler.onFailure(e);
                } catch (Exception ex) {
                    log.error(STR."处理定时器onFailure错误:\{beanName}", e);
                }
            } finally {
                // taskStrong.afterFinally(taskLog);
                if (lockRes) {
                    // 只有锁住成功才需要删除锁,不然删除后下次就可以获取到锁了
                    try {
                        mySqlDistributeLockService.unLock(beanName);
                        handler.onFinally();
                    } catch (Exception e) {
                        log.error(STR."处理定时器onFinally错误:\{beanName}", e);
                    }
                }
                if(taskLog != null){
                    taskLog.setEnd(System.currentTimeMillis());
                    taskLogger.info(taskLog.toString());
                }

                log.info(STR."执行完成:\{name}");
                TraceId.remove();
            }
        });
        Assert.notBlank(cronId, "任务执行有误,无法拿到返回任务ID");
        log.info("启动定时器:{},id:{}", beanName, cronId);
        JobBeanPostProcessor.CRON_RUNNING_MAP.put(sysScheduled.getId(), new CronRunningDto(cronId, config));
    }

    public CronRunningDto getCronInMemory(SysScheduled sysScheduled) {
        return JobBeanPostProcessor.CRON_RUNNING_MAP.get(sysScheduled.getId());
    }

}
