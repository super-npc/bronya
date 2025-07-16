package bronya.admin.module.db.distributed.service;

import bronya.admin.module.db.distributed.repository.SysDistributedLockRepository;
import bronya.admin.module.db.distributed.domain.SysDistributedLock;
import bronya.admin.module.db.distributed.mapper.SysDistributedLockMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MySqlDistributeLockService {
    private final SysDistributedLockRepository lockRepository;
    private final SysDistributedLockMapper lockMapper;


    public boolean tryLock(final String lockName,final Integer seconds) {
        try {
            // 有超时锁,需要删除
            lockMapper.deleteTimeOutLock(lockName,seconds);
            SysDistributedLock sysDistributedLock = new SysDistributedLock();
            sysDistributedLock.setLockKey(lockName);
            return lockRepository.save(sysDistributedLock);
        } catch (Exception e) {
            return false;
        }
    }

    public void unLock(final String lockName) {
        LambdaQueryWrapper<SysDistributedLock> query = new LambdaQueryWrapper<>();
        query.eq(SysDistributedLock::getLockKey,lockName);
        lockRepository.remove(query);
    }
}
