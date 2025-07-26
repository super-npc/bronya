package bronya.admin.module.db.threadpool.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import bronya.admin.module.db.threadpool.domain.SysThreadPool;
import bronya.admin.module.db.threadpool.mapper.SysThreadPoolMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysThreadPoolRepository extends CrudRepository<SysThreadPoolMapper, SysThreadPool> {

    public SysThreadPool findByThreadPrefixName(final String threadPrefixName){
        LambdaQueryWrapper<SysThreadPool> query = new LambdaQueryWrapper<>();
        query.eq(SysThreadPool::getPrefixName,threadPrefixName);
        return this.getOne(query);
    }
}
