package bronya.admin.module.rabbit.module.delayed.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayed;
import bronya.admin.module.rabbit.module.delayed.mapper.RabbitDelayedMapper;

@Repository
public class RabbitDelayedRepository extends BaseRepository<RabbitDelayedMapper, RabbitDelayed> {

    public void saveEntityClass(String entityClass) {
        RabbitDelayed byEntityClass = this.findByEntityClass(entityClass);
        if (byEntityClass == null) {
            byEntityClass = new RabbitDelayed();
            byEntityClass.setEntityClass(entityClass);
            this.save(byEntityClass);
        }
    }

    public RabbitDelayed findByEntityClass(String entityClass){
        LambdaQueryWrapper<RabbitDelayed> query = new LambdaQueryWrapper<>();
        query.eq(RabbitDelayed::getEntityClass,entityClass);
        return this.getOne(query);
    }
}
