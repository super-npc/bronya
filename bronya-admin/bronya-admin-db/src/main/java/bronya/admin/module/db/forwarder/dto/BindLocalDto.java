package bronya.admin.module.db.forwarder.dto;

import java.net.ServerSocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.LocalPortForwarder;

@Data
@AllArgsConstructor
public class BindLocalDto {
    private SSHClient sshClient;
    private LocalPortForwarderDo localPortForwarderDo;
    private ServerSocket serverSocket;
    private LocalPortForwarder localPortForwarder;
}
