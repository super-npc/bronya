package bronya.admin.autoconfig;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import bronya.admin.module.db.env.service.SysEnvPropertyBizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoadingEnvAutoConfig implements ApplicationListener<ApplicationReadyEvent> {
    private final SysEnvPropertyBizService sysEnvPropertyBizService;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        sysEnvPropertyBizService.loadingEnvAndRegister();
    }
}
