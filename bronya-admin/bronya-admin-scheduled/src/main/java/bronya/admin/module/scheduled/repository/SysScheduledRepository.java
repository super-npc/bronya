package bronya.admin.module.scheduled.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.scheduled.domain.SysScheduled;
import bronya.admin.module.scheduled.mapper.SysScheduledMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysScheduledRepository extends BaseRepository<SysScheduledMapper, SysScheduled> {
    public SysScheduled findByBeanName(String beanName){
        LambdaQueryWrapper<SysScheduled> query = new LambdaQueryWrapper<>();
        query.eq(SysScheduled::getBeanName,beanName);
        return this.getOne(query);
    }
}
