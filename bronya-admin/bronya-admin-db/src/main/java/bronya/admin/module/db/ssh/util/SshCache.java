package bronya.admin.module.db.ssh.util;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;

import bronya.admin.module.db.ssh.domain.SshDo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class SshCache {
    public static final Map<Long, SshCli> SSH_MAP = Maps.newHashMap();

    public void put(SshDo sshDo, SshCli cli) {
        SSH_MAP.put(sshDo.getId(), cli);
    }

    public Optional<SshCli> get(SshDo sshDo) {
        return Optional.ofNullable(SSH_MAP.get(sshDo.getId()));
    }

    public Optional<SshCli> delete(SshDo sshDo) {
        return Optional.ofNullable(SSH_MAP.remove(sshDo.getId()));
    }

    public Optional<SshCli> delete(Long id) {
        return Optional.ofNullable(SSH_MAP.remove(id));
    }
}
