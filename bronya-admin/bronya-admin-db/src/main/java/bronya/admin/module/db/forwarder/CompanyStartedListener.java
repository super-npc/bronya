package bronya.admin.module.db.forwarder;

import bronya.admin.module.db.forwarder.service.LocalPortForwarderService;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CompanyStartedListener implements ApplicationListener<ApplicationReadyEvent> {
    private final LocalPortForwarderService localPortForwarderService;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        localPortForwarderService.bindAll();
    }
}
