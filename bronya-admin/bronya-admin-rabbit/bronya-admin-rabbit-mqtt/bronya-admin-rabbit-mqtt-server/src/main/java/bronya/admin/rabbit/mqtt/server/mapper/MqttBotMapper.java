package bronya.admin.rabbit.mqtt.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bronya.admin.rabbit.mqtt.server.domain.MqttBot;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MqttBotMapper extends BaseMapper<MqttBot> {
}
