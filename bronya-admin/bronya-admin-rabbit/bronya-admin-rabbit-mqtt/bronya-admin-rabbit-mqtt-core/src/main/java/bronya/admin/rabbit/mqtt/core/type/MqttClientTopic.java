package bronya.admin.rabbit.mqtt.core.type;

import java.util.Arrays;

import org.dromara.hutool.core.text.StrUtil;

import com.alibaba.cola.exception.BizException;

import jodd.util.Wildcard;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务端接收的topic
 */
@Getter
@AllArgsConstructor
public enum MqttClientTopic {
    /**
     * 服务端通知更新topic
     */
    UPDATE_SUBSCRIBE("device/{}/update-subscribe"),

    ;

    private final String topic;

    public String getDeviceTopic(String deviceId){
        return StrUtil.format(this.getTopic(),deviceId);
    }

    public static MqttClientTopic find(String topic) {
        return Arrays.stream(MqttClientTopic.values()).filter(item -> {
            String pattern = StrUtil.format(item.getTopic(), "*");
            return Wildcard.match(item.getTopic(), pattern);
        }).findFirst().orElseThrow(() -> new BizException(STR."topic not found:\{topic}"));
    }
}
