package bronya.admin.module.db.proxy;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import bronya.admin.module.db.proxy.constant.ProxyConstant;
import bronya.admin.module.db.proxy.domain.ProxyDo;
import bronya.admin.module.db.proxy.repository.ProxyDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProxyStartedListener implements ApplicationListener<ApplicationReadyEvent> {
    private final ProxyDoRepository proxyDoRepository;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent applicationReadyEvent) {
        // 默认记录
        ProxyDo proxyDoDefault = ProxyConstant.proxyDo;
        if (!proxyDoRepository.existsDefaultProxy(proxyDoDefault.getProxyType(), proxyDoDefault.getHost(), proxyDoDefault.getPort())) {
            ProxyDo proxyDo = new ProxyDo();
            proxyDo.setName("默认-直连");
            proxyDo.setProxyType(proxyDoDefault.getProxyType());
            proxyDo.setHost(proxyDoDefault.getHost());
            proxyDo.setPort(proxyDoDefault.getPort());
            proxyDoRepository.save(proxyDo);
            log.info("初始化默认代理");
        }
    }
}
