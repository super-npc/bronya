package bronya.admin.module.rabbit.module.fanout.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanoutReceiver;
import bronya.admin.module.rabbit.module.fanout.mapper.RabbitFanoutReceiverMapper;

@Repository
public class RabbitFanoutReceiverRepository extends BaseRepository<RabbitFanoutReceiverMapper, RabbitFanoutReceiver> {
}
