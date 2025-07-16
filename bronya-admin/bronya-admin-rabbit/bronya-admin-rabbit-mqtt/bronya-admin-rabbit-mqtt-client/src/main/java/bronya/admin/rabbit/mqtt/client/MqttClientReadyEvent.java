package bronya.admin.rabbit.mqtt.client;

import java.util.Set;
import java.util.stream.Collectors;

import org.dromara.hutool.core.text.StrUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import bronya.admin.rabbit.mqtt.core.MqttCli;
import bronya.admin.rabbit.mqtt.core.cfg.MqttCfg;
import bronya.admin.rabbit.mqtt.core.constant.MqttClientFinalConstant;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MqttClientReadyEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final MqttCfg mqttCfg;
    private final MqttCli mqttCliClient;

    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {

    }
}
