package bronya.admin.module.db.ssh.controller;

import org.dromara.hutool.core.io.IoUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.cola.exception.BizException;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.db.amis.dto.AmisIdsDto;
import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.repository.SshDoRepository;
import bronya.admin.module.db.ssh.service.SshService;
import bronya.admin.module.db.ssh.util.SshCache;
import bronya.admin.module.db.ssh.util.SshCli;
import jodd.util.Wildcard;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.connection.channel.direct.Session;

@Slf4j
@RestController
@RequestMapping("/admin/ssh")
@RequiredArgsConstructor
public class SshController {
    private final SshDoRepository sshDoRepository;
    private final SshService sshService;

    @SneakyThrows
    @GetMapping("/exec-echo")
    public boolean execEcho(@AmisIds AmisIdsDto idsDto) {
        SshDo ssh = sshDoRepository.getById(idsDto.getIds().getFirst());
        SshCli sshCli = SshCache.get(ssh).orElseThrow(() -> new BizException("cache不存在的跳板机"));
        try (Session session = sshCli.getCli().startSession()) {
            Session.Command exec = session.exec("echo 'hello world'");
            String res = IoUtil.readUtf8(exec.getInputStream());
            log.info("验证ssh连通性, ssh:{} res:{}", ssh, res);
            return Wildcard.match(res, "*hello world*");
        }
    }
}
