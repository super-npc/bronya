package bronya.admin.rabbit.mqtt.core.type;

import bronya.admin.rabbit.mqtt.core.req.server.Live;
import bronya.admin.rabbit.mqtt.core.req.server.SubscribesUpdate;
import com.alibaba.cola.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 服务端接收的topic
 */
@Getter
@AllArgsConstructor
public enum MqttServerTopic {
    LIVE("service/live", Live.class),
    /**
     * 客户端向服务端报告自己订阅了什么
     */
    SUBSCRIBES("service/subscribes", SubscribesUpdate.class),

    ;

    private final String topic;
    private final Class<?> serializable;

    public static MqttServerTopic find(String topic) {
        return Arrays.stream(MqttServerTopic.values()).filter(temp -> temp.getTopic().equals(topic)).findFirst()
            .orElseThrow(() -> new BizException(STR."topic not found: \{topic}"));
    }
}