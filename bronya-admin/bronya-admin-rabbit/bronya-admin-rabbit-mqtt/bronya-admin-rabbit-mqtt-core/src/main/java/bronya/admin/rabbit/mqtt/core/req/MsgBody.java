package bronya.admin.rabbit.mqtt.core.req;

import lombok.Data;

@Data
public class MsgBody<T> {
    private String deviceId;
    private T data;
}
