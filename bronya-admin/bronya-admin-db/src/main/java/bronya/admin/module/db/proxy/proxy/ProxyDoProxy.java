package bronya.admin.module.db.proxy.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

import bronya.shared.module.util.Md;
import net.steppschuh.markdowngenerator.text.quote.Quote;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.lang.Assert;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.proxy.constant.ProxyConstant;
import bronya.admin.module.db.proxy.domain.ProxyDo;
import bronya.admin.module.db.proxy.domain.ProxyDo.ProxyExt;
import bronya.admin.module.db.proxy.repository.ProxyDoRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyDoProxy extends DataProxy<ProxyDo> {
    private final ProxyDoRepository proxyDoRepository;

    @Override
    public ProxyDo beforeAdd(ProxyDo proxyDo) {
        ProxyDo.ProxyType proxyType = proxyDo.getProxyType();
        String name = STR."\{proxyType.getDesc()}-\{proxyDo.getHost()}:\{proxyDo.getPort()}";
            proxyDo.setName(name);
        return super.beforeAdd(proxyDo);
    }

    @Override
    public void beforeDelete(Long id) {
        // 校验是否为默认代理
        ProxyDo proxyDo = proxyDoRepository.getById(id);
        Assert.isTrue(!proxyDo.getName().equals(ProxyConstant.proxyDo.getHost()), "默认代理不能删除");
        super.beforeDelete(id);
    }

    @Override
    public void table(Map<String, Object> map) {
        ProxyDo proxyDo = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), ProxyDo.class);
        ProxyExt proxyExt = new ProxyExt();
        proxyExt.setRunning(this.runningProxy(proxyDo));
        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(ProxyDo.class, proxyExt));
    }

    public String runningProxy(ProxyDo proxy) {
        Md md = new Md();
        md.appendLn(new Quote("代理"));
        boolean flag;
        try (Socket socket = new Socket()) {
            // 设置连接超时时间（例如5秒）
            socket.connect(new InetSocketAddress(proxy.getHost(), proxy.getPort()), 300);
            flag = true;
        } catch (IOException e) {
            flag = false;
        }
        md.appendLn(STR." - \{proxy.getProxyType()} \{proxy.getHost()}:\{proxy.getPort()} \{Md.emojiBool(flag)}");
        return md.toString();
    }
}
