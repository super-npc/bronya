package bronya.admin.module.rabbit.module.delayed.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayedMsg;

@Mapper
public interface RabbitDelayedMsgMapper extends BaseMapper<RabbitDelayedMsg> {
}
