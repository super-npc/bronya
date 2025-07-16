package bronya.admin.module.db.ssh;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.repository.SshDoRepository;
import bronya.admin.module.db.ssh.service.SshService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class SshStartedListener implements ApplicationListener<ApplicationReadyEvent> {
    private final SshDoRepository sshDoRepository;
    private final SshService sshService;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        List<SshDo> sshDos = sshDoRepository.listEnable();
        log.info("初始化跳板机,总量:{}", sshDos.size());
        for (SshDo sshDo : sshDos) {
            try {
                sshService.initAndCacheSshClient(sshDo);
            } catch (Exception e) {
                // 不能中断main程序
                log.error(STR."初始化跳板机失败:\{sshDo}", e);
            }
        }
    }
}
