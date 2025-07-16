package bronya.admin.module.db.shell.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import bronya.admin.annotation.AmisEnv;
import bronya.core.base.annotation.amis.AmisField;
import lombok.Data;

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "container-invoking-host-env")
@AmisEnv(description = "容器调用宿主机环境变量")
public class ContainerInvokingHostEnv {

    @AmisField(comment = "sshId", envValue = "-1")
    private Integer sshDoId;
}
