package bronya.admin.rabbit.mqtt.server.callback;

import java.util.Map;

import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import bronya.admin.rabbit.mqtt.core.cfg.MqttCfg;
import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;
import bronya.admin.rabbit.mqtt.server.callback.impl.IMqttCallback;
import bronya.admin.rabbit.mqtt.server.callback.impl.LiveCallbackImpl;
import bronya.admin.rabbit.mqtt.server.callback.impl.SubscribesCallbackImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
@RequiredArgsConstructor
public class MqttServerCallback implements MqttCallback {
    private final MqttCfg mqttCfg;
    private final Map<String, MqttMessage> topicLastMessages = Maps.newHashMap();
    private final LiveCallbackImpl liveCallback;
    private final SubscribesCallbackImpl subscribesCallback;

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {
        log.warn("连接断开: {}", mqttCfg.getDeviceId());
    }

    @Override
    public void mqttErrorOccurred(MqttException e) {
        log.warn("连接异常: {}", mqttCfg.getDeviceId());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        MqttServerTopic mqttServerTopic = MqttServerTopic.find(topic);
        topicLastMessages.put(topic, message);
        String assignedClientIdentifier = message.getProperties().getAssignedClientIdentifier();
        MqttProperties properties = message.getProperties();
        // // subscribe后得到的消息会执行到这里面,这里在控制台有输出
        log.info("{} 客户端: {},内容: {}", mqttCfg.getDeviceId(), topic, new String(message.getPayload()));
        String json = new String(message.getPayload());

        IMqttCallback callback = switch (mqttServerTopic) {
            case LIVE -> liveCallback;
            case SUBSCRIBES -> subscribesCallback;
        };
        callback.process(mqttServerTopic, message);
    }

    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {
        log.info("发送成功: {}", mqttCfg.getDeviceId());
    }

    @Override
    public void connectComplete(boolean b, String s) {
        log.info("连接成功: {}", mqttCfg.getDeviceId());
    }

    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {
        log.info("认证成功: {}", mqttCfg.getDeviceId());
    }
}