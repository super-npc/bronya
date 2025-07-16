package bronya.admin.rabbit.mqtt.server.callback.impl;

import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;
import org.eclipse.paho.mqttv5.common.MqttMessage;

public interface IMqttCallback {
    void process(MqttServerTopic mqttServerTopic, MqttMessage message);
}
