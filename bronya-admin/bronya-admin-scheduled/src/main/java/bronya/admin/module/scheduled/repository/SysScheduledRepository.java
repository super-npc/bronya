package bronya.admin.module.scheduled.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import bronya.admin.module.scheduled.domain.SysScheduled;
import bronya.admin.module.scheduled.mapper.SysScheduledMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysScheduledRepository extends CrudRepository<SysScheduledMapper, SysScheduled> {
    public SysScheduled findByBeanName(String beanName){
        LambdaQueryWrapper<SysScheduled> query = new LambdaQueryWrapper<>();
        query.eq(SysScheduled::getBeanName,beanName);
        return this.getOne(query);
    }
}
