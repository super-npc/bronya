package bronya.admin.module.db.ssh.proxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import bronya.admin.module.db.proxy.domain.ProxyDo;
import bronya.admin.module.db.proxy.proxy.ProxyDoProxy;
import bronya.admin.module.db.proxy.repository.ProxyDoRepository;
import bronya.admin.module.db.ssh.domain.SshDo.SshStatus;
import bronya.shared.module.util.Md;
import com.google.common.collect.Lists;
import net.steppschuh.markdowngenerator.text.quote.Quote;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.domain.SshDo.SshCfgExt;
import bronya.admin.module.db.ssh.repository.SshDoRepository;
import bronya.admin.module.db.ssh.service.SshService;
import bronya.admin.module.db.ssh.util.SshCache;
import bronya.admin.module.db.ssh.util.SshCli;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class SshDoProxy extends DataProxy<SshDo> {
    private final SshDoRepository sshDoRepository;
    private final SshService sshService;
    private final ProxyDoRepository proxyDoRepository;

    @Override
    public SshDo beforeAdd(SshDo sshDo) {
        String name =
            STR."\{sshDo.getAuthType().getDesc()}-\{sshDo.getHost()}:\{sshDo.getPort()}-\{sshDo.getUserName()}";
                sshDo.setName(name);
        return super.beforeAdd(sshDo);
    }

    @SneakyThrows
    @Override
    public void afterUpdate(SshDo sshDo) {
        super.afterUpdate(sshDo);
        SshCache.delete(sshDo).ifPresent(SshCli::close);
        try {
            sshService.initAndCacheSshClient(sshDo);
        } catch (Exception e) {
            log.warn("更新ssh成功,但是连接失败,请检查",e);
        }
    }

    @Override
    public void afterDelete(Long id) {
        super.afterDelete(id);
        // 删除后是查询不到对象
        SshCache.delete(id).ifPresent(SshCli::close);
    }

    @Override
    public void afterAdd(SshDo sshDo) {
        super.afterAdd(sshDo);
        try {
            sshService.initAndCacheSshClient(sshDo);
        } catch (Exception e) {
            log.warn("更新ssh成功,但是连接失败,请检查",e);
        }
    }

    @Override
    public void table(Map<String, Object> map) {
        SshDo sshDo = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SshDo.class);
        SshCfgExt sshCfgExt = new SshCfgExt();

        String join = CollUtil.join(Lists.newArrayList(this.runningProxy(sshDo), this.runningSsh(sshDo)), "\n");
        sshCfgExt.setRunning(join);
        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SshDo.class, sshCfgExt));
    }

    private String runningSsh(SshDo sshDo) {
        Md md = new Md();
        md.appendLn(new Quote("ssh客户端"));
        AtomicReference<SshStatus> status = new AtomicReference<>(SshStatus.INIT);
        SshCache.get(sshDo).ifPresent(sshCli -> {
            SSHClient sshClient = sshCli.getCli();
            if (!sshClient.isAuthenticated()) {
                status.set(SshStatus.NOT_AUTHENTICATED);
                return;
            }
            if (!sshClient.isConnected()) {
                status.set(SshStatus.NOT_CONNECTED);
                return;
            }
            status.set(SshStatus.NORMAL);
        });
        md.appendLn(STR." - 状态: \{status.get().getDesc()} \{Md.emojiBool(SshStatus.NORMAL == status.get())}");
        return md.toString();
    }

    private String runningProxy(SshDo sshDo) {
        Boolean proxyEnable = sshDo.getProxyEnable();
        if (!proxyEnable) {
            return "";
        }

        Md md = new Md();
        Optional<ProxyDo> proxyOpt = proxyDoRepository.getOptById(sshDo.getProxyId());
        if (proxyOpt.isEmpty()) {
            md.appendLn(STR." - 数据库代理记录丢失: \{sshDo.getProxyId()} \{Md.emojiBool(false)}");
            return md.toString();
        }
        ProxyDo proxy = proxyOpt.get();
        String runningProxyMd = new ProxyDoProxy(proxyDoRepository).runningProxy(proxy);
        md.appendLn(runningProxyMd);
        return md.toString();
    }
}
