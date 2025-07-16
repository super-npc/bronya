package bronya.admin.module.scheduled.controller;

import java.util.List;

import bronya.admin.module.db.amis.dto.AmisIdsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.scheduled.autoconfig.JobBeanPostProcessor;
import bronya.admin.module.scheduled.domain.SysScheduled;
import bronya.admin.module.scheduled.dto.CronRunningDto;
import bronya.admin.module.scheduled.dto.ScheduledHandler;
import bronya.admin.module.scheduled.repository.SysScheduledRepository;
import bronya.admin.module.scheduled.service.SysScheduledService;
import bronya.admin.module.scheduled.type.TriggerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/scheduled")
@RequiredArgsConstructor
public class ScheduledController {
    private final SysScheduledRepository scheduledRepository;
    private final SysScheduledService scheduledService;

    @GetMapping("/start")
    public void start(@AmisIds AmisIdsDto idsDto) {
        List<SysScheduled> sysScheduledList = scheduledRepository.listByIds(idsDto.getIds());
        for (SysScheduled sysScheduled : sysScheduledList) {
            CronRunningDto cronRunningDto = scheduledService.getCronInMemory(sysScheduled);
            if (cronRunningDto != null) {
                continue;
            }
            scheduledService.newTask(sysScheduled.getBeanName());
        }
    }

    @GetMapping("/stop")
    public void stop(@AmisIds AmisIdsDto idsDto) {
        List<SysScheduled> sysScheduledList = scheduledRepository.listByIds(idsDto.getIds());
        for (SysScheduled sysScheduled : sysScheduledList) {
            scheduledService.stopTask(sysScheduled);
        }
    }

    @GetMapping("/trigger")
    public void trigger(@AmisIds AmisIdsDto idsDto) {
        List<SysScheduled> sysScheduledList = scheduledRepository.listByIds(idsDto.getIds());
        for (SysScheduled sysScheduled : sysScheduledList) {
            String beanName = sysScheduled.getBeanName();
            ScheduledHandler handler = JobBeanPostProcessor.CRON_HANDLER.get(beanName);
            try {
                Object execRes = handler.exec(TriggerType.manual, sysScheduled.getParams());
                handler.onSuccess(execRes);
            } catch (Exception e) {
                try {
                    handler.onFailure(e);
                } catch (Exception ex) {
                    log.error(STR."处理定时器onFailure错误:\{beanName}",e);
                }
            } finally {
                try {
                    handler.onFinally();
                } catch (Exception e) {
                    log.error(STR."处理定时器onFinally错误:\{beanName}",e);
                }
            }

//            Scheduler scheduler = CronUtil.getScheduler();
//            Task task = scheduler.getTask(hutoolScheduleId);
//            task.execute();
        }
    }
}
