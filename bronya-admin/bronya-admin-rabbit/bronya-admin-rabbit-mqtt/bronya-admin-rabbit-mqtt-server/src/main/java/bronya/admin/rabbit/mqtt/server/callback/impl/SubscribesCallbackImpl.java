package bronya.admin.rabbit.mqtt.server.callback.impl;

import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribesCallbackImpl implements IMqttCallback {
    @Override
    public void process(MqttServerTopic mqttServerTopic, MqttMessage message) {

    }
}
