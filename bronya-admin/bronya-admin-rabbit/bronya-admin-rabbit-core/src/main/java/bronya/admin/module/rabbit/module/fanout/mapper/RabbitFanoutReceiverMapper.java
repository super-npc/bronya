package bronya.admin.module.rabbit.module.fanout.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanoutReceiver;

@Mapper
public interface RabbitFanoutReceiverMapper extends BaseMapper<RabbitFanoutReceiver> {
}
