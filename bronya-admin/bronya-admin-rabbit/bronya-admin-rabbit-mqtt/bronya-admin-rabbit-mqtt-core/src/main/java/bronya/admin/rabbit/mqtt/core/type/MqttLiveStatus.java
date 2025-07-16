package bronya.admin.rabbit.mqtt.core.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MqttLiveStatus implements AmisEnum {
    online("在线", Color.深绿色), offline("下线", Color.深红色);

    private final String desc;
    private final Color color;
}