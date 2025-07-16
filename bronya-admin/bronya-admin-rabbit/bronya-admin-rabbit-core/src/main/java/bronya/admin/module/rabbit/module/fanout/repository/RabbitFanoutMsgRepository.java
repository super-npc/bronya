package bronya.admin.module.rabbit.module.fanout.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanoutMsg;
import bronya.admin.module.rabbit.module.fanout.mapper.RabbitFanoutMsgMapper;

@Repository
public class RabbitFanoutMsgRepository extends BaseRepository<RabbitFanoutMsgMapper, RabbitFanoutMsg> {

    public void createMsg(){

    }
}
