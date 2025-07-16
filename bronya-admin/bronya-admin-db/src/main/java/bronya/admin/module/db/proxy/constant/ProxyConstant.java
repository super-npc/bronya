package bronya.admin.module.db.proxy.constant;

import bronya.admin.module.db.proxy.domain.ProxyDo;

public class ProxyConstant {
    public static final ProxyDo proxyDo = new ProxyDo(ProxyDo.ProxyType.DIRECT, "127.0.0.1", 10000);

}
