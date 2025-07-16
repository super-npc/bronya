package bronya.admin.module.db.ssh.service;

import java.net.Proxy;
import java.util.Optional;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.alibaba.cola.exception.BizException;

import bronya.admin.module.db.proxy.repository.ProxyDoRepository;
import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.repository.SshDoRepository;
import bronya.admin.module.db.ssh.util.SshCache;
import bronya.admin.module.db.ssh.util.SshCli;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SshService implements ApplicationListener<ApplicationReadyEvent> {
    private final ProxyDoRepository proxyDoRepository;
    private final SshDoRepository sshDoRepository;

    @SneakyThrows
    public void initAndCacheSshClient(SshDo sshDo) {
        Optional<SshCli> sshDtoOpt = SshCache.get(sshDo);
        if (sshDtoOpt.isPresent()) {
            log.info("跳板机-已有对象,跳过初始化:{}", sshDo);
            return;
        }
        if (sshDo.getProxyEnable()) {
            Proxy proxy = proxyDoRepository.findProxy(sshDo.getProxyId())
                .orElseThrow(() -> new BizException(STR."代理不存在:\{sshDo.getProxyId()}"));
                    SshCli sshCli = new SshCli(sshDo, proxy);
            SshCache.put(sshDo, sshCli);
        } else {
            SshCli sshCli = new SshCli(sshDo);
            SshCache.put(sshDo, sshCli);
        }
        log.info("初始化跳板机:{}", sshDo);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String host = "172.17.0.1";
        int port = 22;
        SshDo byHostPort = sshDoRepository.findByHostPort(host, port);
        if (byHostPort != null) {
            return;
        }
        SshDo sshDo = new SshDo();
        sshDo.setEnable(true);
        sshDo.setName("容器内部连接宿主机");
        sshDo.setHost(host);
        sshDo.setPort(port);
        sshDo.setAuthType(SshDo.AuthType.AUTH_PASSWORD);
        sshDo.setUserName("root");
        sshDo.setPassword("Temp12138.");
        sshDo.setProxyEnable(false);
        sshDo.setProxyId(-1L);
        sshDoRepository.save(sshDo);
        log.info("初始化宿主机跳板机:{}", sshDo);
    }
}
