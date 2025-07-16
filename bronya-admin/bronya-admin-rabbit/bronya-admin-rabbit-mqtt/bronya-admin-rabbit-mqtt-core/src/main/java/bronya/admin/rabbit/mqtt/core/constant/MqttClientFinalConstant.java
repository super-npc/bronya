package bronya.admin.rabbit.mqtt.core.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import bronya.admin.rabbit.mqtt.core.type.MqttClientTopic;
import com.google.common.collect.Sets;

public interface MqttClientFinalConstant {

    /**
     * 客户端基础必填的topic
     */
    Set<MqttClientTopic> TOPIC_CLIENT_LIST = Sets.newHashSet(MqttClientTopic.values());


}
