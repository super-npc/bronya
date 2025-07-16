package bronya.admin.rabbit.mqtt.server.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bronya.admin.rabbit.mqtt.core.cfg.MqttCfg;
import bronya.admin.rabbit.mqtt.core.MqttCli;
import bronya.admin.rabbit.mqtt.server.callback.MqttServerCallback;
import bronya.admin.rabbit.mqtt.server.callback.MqttServerListener;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class MqttServerCfg {
    private final MqttCfg mqttCfg;
    private final MqttServerCallback mqttBotCallback;
    private final MqttServerListener mqttServerListener;

    @Bean
    public MqttCli mqttCliServer(){
        return new MqttCli(mqttCfg, mqttBotCallback, mqttServerListener);
    }
}
