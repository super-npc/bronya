package bronya.admin.module.db.proxy.repository;

import java.net.Proxy;
import java.util.Optional;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.alibaba.cola.exception.SysException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.module.db.proxy.domain.ProxyDo;
import bronya.admin.module.db.proxy.domain.ProxyDo.ProxyType;
import bronya.admin.module.db.proxy.mapper.ProxyDoMapper;
import bronya.admin.module.db.proxy.service.ProxyUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProxyDoRepository extends BaseRepository<ProxyDoMapper, ProxyDo> {

    public Optional<Proxy> findProxy(Long id) {
        if(id == null){
            return Optional.empty();
        }
        ProxyDo proxyDo = this.getOptById(id).orElseThrow(() -> new SysException(STR."未找到代理配置:\{id}"));
        if(proxyDo == null){
            return Optional.empty();
        }
        return Optional.of(ProxyUtil.getProxy(proxyDo));
    }

    public boolean existsDefaultProxy(ProxyType proxyType, String host, Integer port) {
        LambdaQueryWrapper<ProxyDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProxyDo::getProxyType,proxyType);
        wrapper.eq(ProxyDo::getHost,host);
        wrapper.eq(ProxyDo::getPort,port);
        return this.exists(wrapper);
    }
}
