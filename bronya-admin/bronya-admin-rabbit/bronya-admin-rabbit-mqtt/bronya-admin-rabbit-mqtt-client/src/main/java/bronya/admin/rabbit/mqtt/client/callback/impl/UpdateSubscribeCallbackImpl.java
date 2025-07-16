package bronya.admin.rabbit.mqtt.client.callback.impl;

import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.stereotype.Service;

import bronya.admin.rabbit.mqtt.core.type.MqttClientTopic;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateSubscribeCallbackImpl implements IMqttCallback {
    @Override
    public void process(MqttClientTopic mqttClientTopic, MqttMessage message,String json) {

    }
}
