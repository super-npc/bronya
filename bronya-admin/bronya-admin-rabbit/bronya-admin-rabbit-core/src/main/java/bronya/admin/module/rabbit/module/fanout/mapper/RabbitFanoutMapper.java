package bronya.admin.module.rabbit.module.fanout.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanout;

@Mapper
public interface RabbitFanoutMapper extends BaseMapper<RabbitFanout> {
}
