package bronya.admin.module.scheduled.autoconfig;

import java.util.Map;

import bronya.admin.base.constant.AmisBaseConstant;
import bronya.admin.module.scheduled.domain.SysScheduled;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.cron.CronUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import bronya.admin.module.scheduled.dto.CronRunningDto;
import bronya.admin.module.scheduled.dto.ScheduledConfig;
import bronya.admin.module.scheduled.dto.ScheduledHandler;
import bronya.admin.module.scheduled.repository.SysScheduledRepository;
import bronya.admin.module.scheduled.service.SysScheduledService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobBeanPostProcessor implements BeanPostProcessor {
    public final static Map<String, ScheduledHandler> CRON_HANDLER = Maps.newHashMap();
    public final static Map<Long, CronRunningDto> CRON_RUNNING_MAP = Maps.newHashMap();
    private final SysScheduledService sysScheduledService;
    private final SysScheduledRepository scheduledRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if(!AmisBaseConstant.ENABLE_SCHEDULED){
            return;
        }
        CRON_HANDLER.forEach(this::initJob);
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        log.info("------ 启动 cron ------");
    }

    @NotNull
    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) throws BeansException {
        if (AmisBaseConstant.ENABLE_SCHEDULED && bean instanceof ScheduledHandler handler) {
            CRON_HANDLER.put(beanName, handler);
        }
        return bean;
    }

    private void initJob(String beanName, ScheduledHandler handler) {
        ScheduledConfig config = handler.config();
        SysScheduled sysScheduled = scheduledRepository.findByBeanName(beanName);
        if (sysScheduled == null) {
            sysScheduled = new SysScheduled();
            sysScheduled.setBeanName(beanName);
            sysScheduled.setRemark(config.getRemark());
            sysScheduled.setCron(config.getDefaultCron()); // 初始化
            sysScheduled.setParams(config.getParams());
            sysScheduled.setEnable(true);
            scheduledRepository.save(sysScheduled);
        }
        boolean validExpression = CronExpression.isValidExpression(config.getDefaultCron());
        log.info(STR."--检查定时器:\{beanName} - 表达式:\{config.getDefaultCron()}, 检测合法性:\{validExpression}");
            Assert.isTrue(validExpression, STR."表达式不合法:\{beanName}");
        sysScheduledService.newTask(beanName);
    }

    @NotNull
    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName)
        throws BeansException {
        return bean;
    }
}
