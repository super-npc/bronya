package bronya.admin.rabbit.mqtt.core.cfg;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * mqtt配置类，获取mqtt连接
 */
@Data
@Component
@Configuration
@ConfigurationProperties("mqtt")
public class MqttCfg {
    //指定配置文件application-local.properties中的属性名前缀
    private String host;
    private String deviceId;
    private String userName;
    private String password;
    private String topic;
    private int timeout;
    private int keepAlive;
}