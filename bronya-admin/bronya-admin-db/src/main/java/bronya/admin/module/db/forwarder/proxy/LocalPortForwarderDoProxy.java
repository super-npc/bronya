package bronya.admin.module.db.forwarder.proxy;

import java.net.ServerSocket;
import java.util.Map;
import java.util.Optional;

import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo.ForwarderStatus;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo.LocalPortForwarderExt;
import bronya.admin.module.db.forwarder.dto.BindLocalDto;
import bronya.admin.module.db.forwarder.repository.LocalPortForwarderDoRepository;
import bronya.admin.module.db.forwarder.util.LocalPortForwarderUtil;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalPortForwarderDoProxy extends DataProxy<LocalPortForwarderDo> {
    private final LocalPortForwarderDoRepository localPortForwarderDoRepository;

    @Override
    public void table(Map<String, Object> map) {
        LocalPortForwarderDo localPortForwarderDo = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), LocalPortForwarderDo.class);
        LocalPortForwarderExt localPortForwarderExt = new LocalPortForwarderExt();

        Optional<BindLocalDto> bindLocalDtoOpt = LocalPortForwarderUtil.get(localPortForwarderDo);
        localPortForwarderExt.setStatus(ForwarderStatus.INIT);
        bindLocalDtoOpt.ifPresent(bindLocalDto -> {
            SSHClient sshClient = bindLocalDto.getSshClient();
            if (!sshClient.isAuthenticated() || !sshClient.isConnected()) {
                localPortForwarderExt.setStatus(ForwarderStatus.SSH_ERROR);
                return;
            }
            LocalPortForwarder localPortForwarder = bindLocalDto.getLocalPortForwarder();
            boolean running = localPortForwarder.isRunning();
            if (!running) {
                localPortForwarderExt.setStatus(ForwarderStatus.FORWARDER_NOT_RUNNING);
                return;
            }
            ServerSocket serverSocket = bindLocalDto.getServerSocket();
            boolean bound = serverSocket.isBound();
            if (!bound) {
                localPortForwarderExt.setStatus(ForwarderStatus.SS_NOT_BOUND);
                return;
            }
            boolean closed = serverSocket.isClosed();
            if (closed) {
                localPortForwarderExt.setStatus(ForwarderStatus.SS_CLOSE);
                return;
            }
            localPortForwarderExt.setStatus(ForwarderStatus.SUCCESS);
        });
        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(LocalPortForwarderDo.class, localPortForwarderExt));
    }
}
