package bronya.admin.rabbit.mqtt.server.callback;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttActionListener;
import org.springframework.stereotype.Component;

@Data
@Slf4j
@Component
@RequiredArgsConstructor
public class MqttServerListener implements MqttActionListener {

    @Override
    public void onSuccess(IMqttToken iMqttToken) {
        log.info("mqtt5 连接成功");
    }

    @Override
    public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
        log.info("mqtt5 连接失败");
    }
}