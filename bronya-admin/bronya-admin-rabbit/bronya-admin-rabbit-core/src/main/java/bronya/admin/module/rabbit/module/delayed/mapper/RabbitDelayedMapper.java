package bronya.admin.module.rabbit.module.delayed.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayed;

@Mapper
public interface RabbitDelayedMapper extends BaseMapper<RabbitDelayed> {
}
