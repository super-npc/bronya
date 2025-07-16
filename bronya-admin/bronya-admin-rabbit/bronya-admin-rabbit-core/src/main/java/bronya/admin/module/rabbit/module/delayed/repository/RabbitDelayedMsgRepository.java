package bronya.admin.module.rabbit.module.delayed.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayedMsg;
import bronya.admin.module.rabbit.module.delayed.mapper.RabbitDelayedMsgMapper;

@Repository
public class RabbitDelayedMsgRepository extends BaseRepository<RabbitDelayedMsgMapper, RabbitDelayedMsg> {
}
