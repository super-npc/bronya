package bronya.admin.module.rabbit.module.work.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;
import bronya.admin.module.rabbit.module.work.mapper.RabbitWorkMsgMapper;

@Repository
public class RabbitWorkMsgRepository extends BaseRepository<RabbitWorkMsgMapper, RabbitWorkMsg> {

}
