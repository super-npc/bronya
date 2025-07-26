package bronya.admin.module.db.distributed.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import bronya.admin.module.db.distributed.domain.SysDistributedLock;
import bronya.admin.module.db.distributed.mapper.SysDistributedLockMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SysDistributedLockRepository extends CrudRepository<SysDistributedLockMapper, SysDistributedLock> {
//    public void initLockKey(String lockKey){
//        if (this.existsLockKey(lockKey)) {
//            return;
//        }
//        SysDistributedLock sysDistributedLock = new SysDistributedLock();
//        sysDistributedLock.setLockKey(lockKey);
//        this.save(sysDistributedLock);
//    }

    public SysDistributedLock findLockTimeByName(final String lockName){
        LambdaQueryWrapper<SysDistributedLock> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(SysDistributedLock::getCreateTime);
        wrapper.eq(SysDistributedLock::getLockKey,lockName);
        return this.getOne(wrapper);
    }
    public boolean existsLockKey(String lockKey){
        LambdaQueryWrapper<SysDistributedLock> query = new LambdaQueryWrapper<>();
        query.eq(SysDistributedLock::getLockKey,lockKey);
        return this.exists(query);
    }
}
