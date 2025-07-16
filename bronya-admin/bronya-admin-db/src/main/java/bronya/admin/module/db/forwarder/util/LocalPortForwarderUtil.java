package bronya.admin.module.db.forwarder.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import bronya.admin.module.db.forwarder.constant.ForwarderConstant;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import bronya.admin.module.db.forwarder.dto.BindLocalDto;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;

@Slf4j
@UtilityClass
public class LocalPortForwarderUtil {

    public boolean checkRunning(LocalPortForwarderDo localPortForwarderDo) {
        Optional<BindLocalDto> bindLocalDtoOpt = LocalPortForwarderUtil.get(localPortForwarderDo);
        if (bindLocalDtoOpt.isEmpty()) {
            // 未创建
            return false;
        }
        BindLocalDto bindLocalDto = bindLocalDtoOpt.get();
        LocalPortForwarder localPortForwarder = bindLocalDto.getLocalPortForwarder();
        return localPortForwarder.isRunning();
    }

    public void put(SSHClient sshClient,LocalPortForwarderDo localPortForwarderDo, ServerSocket ss, LocalPortForwarder localPortForwarder){
        ForwarderConstant.RUNNING_BIND_LOCAL_MAP.put(localPortForwarderDo.getId(), new BindLocalDto(sshClient,localPortForwarderDo, ss, localPortForwarder));
    }


    public Optional<BindLocalDto> get(LocalPortForwarderDo localPortForwarderDo) {
        BindLocalDto bindLocalDto = ForwarderConstant.RUNNING_BIND_LOCAL_MAP.get(localPortForwarderDo.getId());
        return Optional.ofNullable(bindLocalDto);
    }

    public void unbind(LocalPortForwarderDo localPortForwarderDo) {
        BindLocalDto bindLocalDto = ForwarderConstant.RUNNING_BIND_LOCAL_MAP.get(localPortForwarderDo.getId());
        if (bindLocalDto == null) {
            return;
        }
        LocalPortForwarder localPortForwarder = bindLocalDto.getLocalPortForwarder();
        try {
            localPortForwarder.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ServerSocket serverSocket = bindLocalDto.getServerSocket();
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
