package bronya.admin.module.scheduled.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.scheduled.autoconfig.JobBeanPostProcessor;
import bronya.admin.module.scheduled.domain.SysScheduled;
import bronya.admin.module.scheduled.domain.SysScheduled.SysScheduledExt;
import bronya.admin.module.scheduled.dto.CronRunningDto;
import bronya.admin.module.scheduled.repository.SysScheduledRepository;
import bronya.admin.module.scheduled.service.SysScheduledService;
import bronya.core.base.dto.DataProxy;
import bronya.shared.module.util.Md;
import com.alibaba.cola.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steppschuh.markdowngenerator.text.quote.Quote;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.cron.CronUtil;
import org.dromara.hutool.cron.Scheduler;
import org.dromara.hutool.cron.pattern.CronPattern;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysScheduledProxy extends DataProxy<SysScheduled> {
    private final SysScheduledRepository sysScheduledRepository;
    private final SysScheduledService scheduledService;

    @Override
    public SysScheduled beforeUpdate(SysScheduled sysScheduled) {
        log.info("准备更新定时任务: {}", sysScheduled.getBeanName());
        Assert.isTrue(CronExpression.isValidExpression(sysScheduled.getCron()), () -> new BizException(STR."\{sysScheduled.getBeanName()}表达式有误:\{sysScheduled.getCron()}"));
        return super.beforeUpdate(sysScheduled);
    }

    @Override
    public void afterUpdate(SysScheduled sysScheduled) {
        super.afterUpdate(sysScheduled);
        String beanName = sysScheduled.getBeanName();
        log.info("已更新定时任务: {}", beanName);
        CronRunningDto cronRunningDto = JobBeanPostProcessor.CRON_RUNNING_MAP.get(sysScheduled.getId());
        if (sysScheduled.getEnable()) {
            if (cronRunningDto != null) {
                // 跟内存的表达式比较,不同则需要重新生成
                String hutoolScheduleId = cronRunningDto.getHutoolScheduleId();
                CronUtil.getScheduler().updatePattern(hutoolScheduleId, CronPattern.of(sysScheduled.getCron()));
                log.info("{}更新定时任务的调度模式: {}", beanName, sysScheduled.getCron());
            }
        } else {
            // 禁用了
            scheduledService.stopTask(sysScheduled);
            log.info("定时任务已禁用: {}", beanName);
        }
    }

    @Override
    public void table(Map<String, Object> map) {
        SysScheduled sysScheduled = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SysScheduled.class);
        SysScheduledExt sysScheduledExt = new SysScheduledExt();
        CronRunningDto cronRunningDto = JobBeanPostProcessor.CRON_RUNNING_MAP.get(sysScheduled.getId());

        Md md = new Md();
        md.appendLn(new Quote("基础信息"));
        if (cronRunningDto != null) {
            String sid = cronRunningDto.getHutoolScheduleId();
            Scheduler scheduler = CronUtil.getScheduler();
            CronPattern pattern = scheduler.getPattern(sid);

            md.appendLn(STR." - 任务id: \{sid}");
            md.appendLn(STR." - 参数: \{sysScheduled.getParams()}");
            sysScheduledExt.setProcess(cronRunningDto.getProcess());
            // 异常数据
            if (!pattern.toString().equals(sysScheduled.getCron())) {
                md.appendLn(new Quote("异常数据"));
                md.appendLn(STR." - 实际cron:\{pattern.toString()}");
            }
            md.appendLn(this.nextTime(cronRunningDto));
        } else {
            md.appendLn(STR." - 不存在任务 \{Md.emojiBool(false)}");
        }
        sysScheduledExt.setRunning(md.toString());
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SysScheduled.class, sysScheduledExt));
    }


    private String nextTime(CronRunningDto cronRunningDto) {
        String sid = cronRunningDto.getHutoolScheduleId();
        Scheduler scheduler = CronUtil.getScheduler();
        CronPattern pattern = scheduler.getPattern(sid);

        Md md = new Md();
        md.appendLn(new Quote("最近执行"));

//        Task task = scheduler.getTask(sid);
        Date date = new Date();
        for (int i = 0; i < 5; i++) {
            Calendar calendar = pattern.nextMatch(DateUtil.toCalendar(date));
            date = DateUtil.offsetSecond(DateUtil.date(calendar).toJdkDate(), 1);
            md.appendLn(STR." - \{DateUtil.formatDateTime(DateUtil.date(calendar))}");
        }
        return md.toString();
    }

}
