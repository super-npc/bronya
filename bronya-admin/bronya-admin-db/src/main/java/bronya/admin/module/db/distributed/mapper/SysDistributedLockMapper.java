package bronya.admin.module.db.distributed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bronya.admin.module.db.distributed.domain.SysDistributedLock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysDistributedLockMapper extends BaseMapper<SysDistributedLock> {

    @Delete("DELETE FROM sys_distributed_lock WHERE lock_key = #{lockKey} AND create_time < NOW() - INTERVAL #{timeOut} SECOND;")
    void deleteTimeOutLock(String lockKey,Integer timeOut);

    /**
     * 尝试获取锁
     * 如果返回 1，表示成功释放锁。
     * 如果返回 0，表示锁不存在或当前会话未持有该锁。
     * 如果返回 NULL，表示锁已经被其他会话释放。
     */
    @Select("SELECT GET_LOCK(#{lockKey}, #{timeOut}) AS lock_result;")
    Integer tryLock(String lockKey,Integer timeOut);

    @Select("SELECT RELEASE_LOCK(#{lockKey}) AS release_result;")
    Integer unLock(String lockKey);
}

