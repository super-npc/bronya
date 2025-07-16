package bronya.admin.module.db.shell;

import bronya.admin.module.db.shell.resp.ExecResp;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.alibaba.cola.exception.BizException;

import bronya.admin.module.db.shell.cfg.ContainerInvokingHostEnv;
import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.repository.SshDoRepository;
import bronya.admin.module.db.ssh.service.SshService;
import bronya.admin.module.db.ssh.util.SshCache;
import bronya.admin.module.db.ssh.util.SshCli;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.schmizz.sshj.connection.channel.direct.Session;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContainerInvokingHostShellService implements ApplicationListener<ApplicationReadyEvent> {
    private final SshDoRepository sshDoRepository;
    private final SshService sshService;
    private final ContainerInvokingHostEnv containerInvokingHostEnv;

    @SneakyThrows
    public ExecResp exec(String cmd) {
        SshDo sshByHost = sshDoRepository.getOptById(containerInvokingHostEnv.getSshDoId())
            .orElseThrow(() -> new BizException("未配置宿主机ssh"));
        SshCli sshCli = SshCache.get(sshByHost).orElseThrow(() -> new BizException("未配置宿主机sshCli对象(缓存)"));
        try (Session session = sshCli.getCli().startSession()) {
            log.info("执行宿主机shell命令:{}", cmd);
            @Cleanup
            Session.Command exec = session.exec(cmd);
            // 等待命令执行完成（通过检测流是否结束）
            while (!exec.isEOF()) {
                ThreadUtil.sleep(200);
            }
            Integer exitStatus = exec.getExitStatus();
            log.info("执行状态:{}", exitStatus);
            String result = IoUtil.readUtf8(exec.getInputStream());
            String error = IoUtil.readUtf8(exec.getErrorStream());
            return new ExecResp(exec,exitStatus,result,error);
        }
    }

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {

    }
}
