package bronya.admin.rabbit.mqtt.core.constant;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import bronya.admin.rabbit.mqtt.core.type.MqttServerTopic;

public interface MqttServerFinalConstant {

    /**
     * 服务端基础必填的topic
     */
    Set<String> TOPIC_SERVER_LIST =
        Arrays.stream(MqttServerTopic.values()).map(MqttServerTopic::getTopic).collect(Collectors.toSet());;
}
