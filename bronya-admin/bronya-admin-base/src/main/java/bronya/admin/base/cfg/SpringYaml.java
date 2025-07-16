package bronya.admin.base.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring")
public class SpringYaml {
    private Application application;
    private Rabbitmq rabbitmq;
    private Profiles profiles;

    public boolean isDev(){
        return "dev".equals(profiles.getActive());
    }

    public boolean isPrd(){
        return "prd".equals(profiles.getActive());
    }

    @Data
    public static class Profiles{
        private String active;
    }

    @Data
    public static class Application {
        private String name;
    }

    @Data
    public static class Rabbitmq {
        private String virtualHost;
        private String host;
        private Integer port;
        private String username;
        private String password;
    }
}
