package bronya.admin.rabbit.mqtt.server;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import bronya.admin.rabbit.mqtt.core.MqttCli;
import bronya.admin.rabbit.mqtt.core.constant.MqttServerFinalConstant;
import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqttServerReadyEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final MqttCli mqttCliServer;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        mqttCliServer.createMqttCliInstance();
        mqttCliServer.forkTopic(MqttServerFinalConstant.TOPIC_SERVER_LIST);
    }
}
