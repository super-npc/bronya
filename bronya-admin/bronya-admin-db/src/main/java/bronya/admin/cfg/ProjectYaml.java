package bronya.admin.cfg;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import bronya.shared.module.common.type.FilePlatformType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "project")
@PropertySource(value = "classpath:application-project.yml")
public class ProjectYaml {
    private ProxyCnf proxy;
    private MediaCnf mediaCnf;

    @Data
    public static class MediaCnf{
        private FilePlatformType filePlatformType;
    }

    public Proxy getProxyCnf() {
        if (this.proxy.isEnabled()) {
            Proxy proxy1 = new Proxy(this.proxy.getType(), new InetSocketAddress(this.proxy.getIp(), this.proxy.getPort()));
            log.debug("---使用代理:{}", proxy1);
            return proxy1;
        }
        return null;
    }

    @Data
    public static class ProxyCnf {
        private boolean enabled;
        private String ip;
        private Integer port;
        private Proxy.Type type;
    }
}
