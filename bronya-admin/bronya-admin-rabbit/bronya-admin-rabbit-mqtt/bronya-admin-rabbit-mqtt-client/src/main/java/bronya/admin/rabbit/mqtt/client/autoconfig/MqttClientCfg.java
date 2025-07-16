package bronya.admin.rabbit.mqtt.client.autoconfig;

import bronya.admin.rabbit.mqtt.core.constant.MqttClientFinalConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bronya.admin.rabbit.mqtt.client.callback.MqttClientCallback;
import bronya.admin.rabbit.mqtt.client.callback.MqttClientListener;
import bronya.admin.rabbit.mqtt.core.cfg.MqttCfg;
import bronya.admin.rabbit.mqtt.core.MqttCli;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class MqttClientCfg {
    private final MqttCfg mqttCfg;
    private final MqttClientCallback callback;
    private final MqttClientListener listener;

    @Bean
    public MqttCli mqttCliClient() {
        MqttCli mqttCli = new MqttCli(mqttCfg, callback, listener);
        mqttCli.createMqttCliInstance();
        Set<String> topics = MqttClientFinalConstant.TOPIC_CLIENT_LIST.stream().map(temp -> temp.getDeviceTopic(mqttCfg.getDeviceId())).collect(Collectors.toSet());
        mqttCli.forkTopic(topics);
        return mqttCli;
    }
}
