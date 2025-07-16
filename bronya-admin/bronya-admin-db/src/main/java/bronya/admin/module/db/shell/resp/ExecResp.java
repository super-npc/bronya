package bronya.admin.module.db.shell.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.schmizz.sshj.connection.channel.direct.Session;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecResp {
    private Session.Command exec;
    private Integer exitStatus;
    private String result;
    private String error;
}
