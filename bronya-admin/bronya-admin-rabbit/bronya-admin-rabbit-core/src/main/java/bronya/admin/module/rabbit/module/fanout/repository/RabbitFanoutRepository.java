package bronya.admin.module.rabbit.module.fanout.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import bronya.admin.module.rabbit.module.fanout.IFanoutMq;
import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanout;
import bronya.admin.module.rabbit.module.fanout.mapper.RabbitFanoutMapper;
import bronya.shared.module.common.type.ActivationStatus;

@Repository
public class RabbitFanoutRepository extends BaseRepository<RabbitFanoutMapper, RabbitFanout> {

    public void appStartedInitData(Map<Class<?>, Collection<IFanoutMq<?>>> map) {
        this.resetActivationStatus();
        for (Map.Entry<Class<?>, Collection<IFanoutMq<?>>> entry : map.entrySet()) {
            Class<?> clasz = entry.getKey();
            String name = clasz.getName();
            RabbitFanout rabbit = Optional.ofNullable(this.findByClassName(name)).orElse(new RabbitFanout());
            rabbit.setEntityClass(name);
            rabbit.setActivationStatus(ActivationStatus.ENABLE);
            this.saveOrUpdate(rabbit);
        }
    }

    public void resetActivationStatus() {
        LambdaUpdateWrapper<RabbitFanout> query = new LambdaUpdateWrapper<>();
        query.eq(RabbitFanout::getActivationStatus, ActivationStatus.ENABLE);
        RabbitFanout rabbit = new RabbitFanout();
        rabbit.setActivationStatus(ActivationStatus.DISABLE);
        this.update(rabbit, query);
    }

    public RabbitFanout findByClassName(String className) {
        LambdaQueryWrapper<RabbitFanout> query = new LambdaQueryWrapper<>();
        query.eq(RabbitFanout::getEntityClass, className);
        return this.getOne(query);
    }
}
