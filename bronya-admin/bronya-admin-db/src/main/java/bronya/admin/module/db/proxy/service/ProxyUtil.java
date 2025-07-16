package bronya.admin.module.db.proxy.service;

import java.net.InetSocketAddress;
import java.net.Proxy;

import bronya.admin.module.db.proxy.domain.ProxyDo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ProxyUtil {

    public Proxy getProxy(ProxyDo proxyDo) {
        ProxyDo.ProxyType proxyType = proxyDo.getProxyType();
        Proxy.Type type = switch (proxyType) {
            case DIRECT -> Proxy.Type.DIRECT;
            case HTTP -> Proxy.Type.HTTP;
            case SOCKS -> Proxy.Type.SOCKS;
        };
        return new Proxy(type, new InetSocketAddress(proxyDo.getHost(), proxyDo.getPort()));
    }
}
