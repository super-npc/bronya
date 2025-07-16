package bronya.admin.rabbit.mqtt.client.callback.impl;

import org.eclipse.paho.mqttv5.common.MqttMessage;

import bronya.admin.rabbit.mqtt.core.type.MqttClientTopic;

public interface IMqttCallback {
    void process(MqttClientTopic mqttClientTopic, MqttMessage message,String json);
}
