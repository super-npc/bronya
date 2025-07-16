package bronya.admin.module.rabbit.module.work.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;

@Mapper
public interface RabbitWorkMsgMapper extends BaseMapper<RabbitWorkMsg> {
}
