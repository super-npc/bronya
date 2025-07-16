package bronya.admin.rabbit.mqtt.core.util;

import bronya.admin.rabbit.mqtt.core.req.MsgBody;
import bronya.admin.rabbit.mqtt.core.req.server.Live;
import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import com.alibaba.fastjson2.JSONObject;

import lombok.Data;
import lombok.experimental.UtilityClass;

@Data
@UtilityClass
public class MsgStatusUtil {
    public MqttMessage getStatusMsg(String deviceId, MqttLiveStatus status) {
        MsgBody<Live> objectMsgBody = new MsgBody<>();
        objectMsgBody.setDeviceId(deviceId);
        objectMsgBody.setData(new Live(status));

        MqttMessage willMessage = new MqttMessage();
        willMessage.setPayload(JSONObject.toJSONString(objectMsgBody).getBytes());
        willMessage.setQos(1);
        willMessage.setRetained(true);
        return willMessage;
    }
}
