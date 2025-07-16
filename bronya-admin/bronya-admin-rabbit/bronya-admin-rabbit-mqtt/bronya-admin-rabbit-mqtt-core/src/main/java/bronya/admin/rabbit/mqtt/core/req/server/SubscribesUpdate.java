package bronya.admin.rabbit.mqtt.core.req.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通知客户端要订阅的主题
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribesUpdate {
    private List<String> topics;
}
