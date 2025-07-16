package bronya.admin.rabbit.mqtt.client.callback;

import bronya.admin.rabbit.mqtt.core.MqttCli;
import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;
import bronya.admin.rabbit.mqtt.core.util.MsgStatusUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttActionListener;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttClientListener implements MqttActionListener {
    private final String topicStatus = MqttServerTopic.LIVE.getTopic();

    @SneakyThrows
    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        log.info("mqtt5 连接成功");
        String deviceId = iMqttToken.getClient().getClientId();
        final MqttCli mqttCli = SpringUtil.getBean(MqttCli.class);
        mqttCli.getClient().publish(topicStatus, MsgStatusUtil.getStatusMsg(deviceId, MqttLiveStatus.online));
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        log.info("mqtt5 连接失败");
    }
}