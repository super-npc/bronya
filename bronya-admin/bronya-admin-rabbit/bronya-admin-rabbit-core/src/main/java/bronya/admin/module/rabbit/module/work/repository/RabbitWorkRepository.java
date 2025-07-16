package bronya.admin.module.rabbit.module.work.repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import bronya.admin.module.rabbit.module.work.IWorkMq;
import bronya.admin.module.rabbit.module.work.domain.RabbitWork;
import bronya.admin.module.rabbit.module.work.mapper.RabbitWorkMapper;
import bronya.shared.module.common.type.ActivationStatus;

@Repository
public class RabbitWorkRepository extends BaseRepository<RabbitWorkMapper, RabbitWork> {
    public void appStartedInitData(Map<Class<?>, Collection<IWorkMq<?>>> map) {
        this.resetActivationStatus();
        for (Map.Entry<Class<?>, Collection<IWorkMq<?>>> entry : map.entrySet()) {
            Class<?> clasz = entry.getKey();
            // Collection<IWorkMq<?>> list = entry.getValue(); //todo 好像弄错了,work需要多实例才能发挥出作用
            // System.out.println("list = " + list);
            String name = clasz.getName();
            RabbitWork rabbitWork = Optional.ofNullable(this.findByClassName(name)).orElse(new RabbitWork());
            rabbitWork.setEntityClass(name);
            rabbitWork.setActivationStatus(ActivationStatus.ENABLE);
            this.saveOrUpdate(rabbitWork);
        }
    }

    public void resetActivationStatus() {
        LambdaUpdateWrapper<RabbitWork> query = new LambdaUpdateWrapper<>();
        query.eq(RabbitWork::getActivationStatus, ActivationStatus.ENABLE);
        RabbitWork rabbitWork = new RabbitWork();
        rabbitWork.setActivationStatus(ActivationStatus.DISABLE);
        this.update(rabbitWork, query);
    }

    public RabbitWork findByClassName(String className) {
        LambdaQueryWrapper<RabbitWork> query = new LambdaQueryWrapper<>();
        query.eq(RabbitWork::getEntityClass, className);
        return this.getOne(query);
    }
}
