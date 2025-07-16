package bronya.admin.module.task;

import java.util.List;

import org.springframework.stereotype.Component;

import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import bronya.admin.module.db.forwarder.repository.LocalPortForwarderDoRepository;
import bronya.admin.module.db.forwarder.service.LocalPortForwarderService;
import bronya.admin.module.scheduled.dto.ScheduledConfig;
import bronya.admin.module.scheduled.dto.ScheduledHandler;
import bronya.admin.module.scheduled.type.TriggerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForwarderReBindTask extends ScheduledHandler {
    private final LocalPortForwarderDoRepository localPortForwarderDoRepository;
    private final LocalPortForwarderService localPortForwarderService;

    @Override
    public Object exec(TriggerType triggerType, String param) {
        List<LocalPortForwarderDo> localPortForwarderDos = localPortForwarderDoRepository.listEnable();
        for (LocalPortForwarderDo localPortForwarderDo : localPortForwarderDos) {
            localPortForwarderService.bind(localPortForwarderDo);
        }
        return null;
    }

    @Override
    public ScheduledConfig config() {
        ScheduledConfig config = new ScheduledConfig("端口映射", "");
        config.setDefaultCron("0/10 * * * * ?");
        return config;
    }
}
