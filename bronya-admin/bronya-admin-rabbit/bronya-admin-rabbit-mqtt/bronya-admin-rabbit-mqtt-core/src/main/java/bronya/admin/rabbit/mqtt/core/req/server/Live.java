package bronya.admin.rabbit.mqtt.core.req.server;

import bronya.admin.rabbit.mqtt.core.type.MqttLiveStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Live {
    private MqttLiveStatus status;
}
