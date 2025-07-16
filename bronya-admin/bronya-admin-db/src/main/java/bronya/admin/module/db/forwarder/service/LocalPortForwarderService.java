package bronya.admin.module.db.forwarder.service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.List;
import java.util.Optional;

import bronya.admin.module.db.forwarder.dto.BindLocalDto;
import bronya.admin.module.db.ssh.util.SshCache;
import bronya.admin.module.db.ssh.util.SshCli;
import com.alibaba.cola.exception.BizException;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.repository.SshDoRepository;
import bronya.admin.module.db.ssh.service.SshService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import bronya.admin.module.db.forwarder.repository.LocalPortForwarderDoRepository;
import bronya.admin.module.db.forwarder.util.LocalPortForwarderUtil;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;
import net.schmizz.sshj.connection.channel.direct.Parameters;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalPortForwarderService {
    private final LocalPortForwarderDoRepository localPortForwarderDoRepository;
    private final SshDoRepository sshDoRepository;
    private final SshService sshService;

    public void bindAll() {
        List<LocalPortForwarderDo> localPortForwarderDos = localPortForwarderDoRepository.listEnable();
        for (LocalPortForwarderDo localPortForwarderDo : localPortForwarderDos) {
            this.bind(localPortForwarderDo);
        }
    }

    // 重新绑定
    public void rebind(LocalPortForwarderDo localPortForwarderDo){
        Optional<BindLocalDto> bindLocalDto = LocalPortForwarderUtil.get(localPortForwarderDo);
        bindLocalDto.ifPresent(bind ->{
            // 如果有绑定则解绑
            LocalPortForwarder localPortForwarder = bind.getLocalPortForwarder();
            try {
                localPortForwarder.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ServerSocket serverSocket = bind.getServerSocket();
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.bind(localPortForwarderDo);
    }

    public void bind(LocalPortForwarderDo localPortForwarderDo) {
        if (LocalPortForwarderUtil.checkRunning(localPortForwarderDo)) {
            log.info("本地端口转发运行中:{}", localPortForwarderDo);
            return;
        }


        try {
            SshDo sshDo = sshDoRepository.getOptById(localPortForwarderDo.getSshId()).orElseThrow(() -> new BizException("sshId记录不存在"));
            SshCli sshCli = SshCache.get(sshDo).orElseThrow(() -> new BizException("sshDo记录不存在"));
            SSHClient sshClient = sshCli.getCli();

            ServerSocket ss = new ServerSocket();

            // 创建一个反向隧道
            // 目标服务器
            String targetHost = localPortForwarderDo.getTargetHost();
            int targetPort = localPortForwarderDo.getTargetPort();

            // 本地服务器
            String localHost = localPortForwarderDo.getLocalHost();
            int localPort = localPortForwarderDo.getLocalPort();
            Parameters params = new Parameters(localHost, localPort, targetHost, targetPort);
            ss.setReuseAddress(true);
            ss.bind(new InetSocketAddress(params.getLocalHost(), params.getLocalPort()));
            LocalPortForwarder localPortForwarder = sshClient.newLocalPortForwarder(params, ss);
            LocalPortForwarderUtil.put(sshClient,localPortForwarderDo,ss,localPortForwarder);
            log.info("启动 本地端口转发:{}", localPortForwarderDo);
            ThreadUtil.execute(() -> {
                try {
                    localPortForwarder.listen();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            log.warn(STR."本地端口转发失败:\{localPortForwarderDo}",e);
        }
//            sshClient.getRemotePortForwarder().bind(new RemotePortForwarder.Forward(3308),
//                new SocketForwardingConnectListener(new InetSocketAddress(targetHost, targetPort)));
//            sshClient.getTransport().join();
//            log.info("端口转发");

    }
}
