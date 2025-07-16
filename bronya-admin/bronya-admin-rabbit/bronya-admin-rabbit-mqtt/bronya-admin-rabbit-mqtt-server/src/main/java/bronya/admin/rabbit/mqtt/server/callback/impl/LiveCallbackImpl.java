package bronya.admin.rabbit.mqtt.server.callback.impl;

import bronya.admin.rabbit.mqtt.core.req.MsgBody;
import bronya.admin.rabbit.mqtt.core.req.server.Live;
import bronya.admin.rabbit.mqtt.core.req.server.SubscribesUpdate;
import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;
import bronya.admin.rabbit.mqtt.core.util.MsgBodyUtil;
import bronya.admin.rabbit.mqtt.server.domain.MqttBot;
import bronya.admin.rabbit.mqtt.server.repository.MqttBotRepository;
import com.alibaba.cola.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveCallbackImpl implements IMqttCallback {
    private final MqttBotRepository mqttBotRepository;

    @Override
    public void process(MqttServerTopic mqttServerTopic, MqttMessage message) {
        MsgBody<Live> body = MsgBodyUtil.parse(new String(message.getPayload()), Live.class);
        String deviceId = body.getDeviceId();
        MqttLiveStatus status = body.getData().getStatus();
        Optional<MqttBot> mqttBotOpt = mqttBotRepository.findByDeviceId(deviceId);
        if (mqttBotOpt.isEmpty()) {
            mqttBotRepository.createDevice(deviceId, status);
            return;
        } else {
            MqttBot mqttBot = mqttBotOpt.get();
            mqttBot.setStatus(body.getData().getStatus());
            mqttBotRepository.updateById(mqttBot);
        }
        log.info("更新设备状态, deviceId:{}, status:{}", body.getDeviceId(), status);
        // 推送私有订阅更新
        

    }
}
