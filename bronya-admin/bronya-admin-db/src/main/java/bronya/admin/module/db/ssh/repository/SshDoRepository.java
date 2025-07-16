package bronya.admin.module.db.ssh.repository;

import java.util.List;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.module.db.ssh.domain.SshDo;
import bronya.admin.module.db.ssh.mapper.SshDoMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SshDoRepository extends BaseRepository<SshDoMapper, SshDo> {

    public List<SshDo> listEnable(){
        LambdaQueryWrapper<SshDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SshDo::getEnable, true);
        return this.list(wrapper);
    }

    public SshDo findByHostPort(String host, int port){
        LambdaQueryWrapper<SshDo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SshDo::getHost, host);
        queryWrapper.eq(SshDo::getPort, port);
        return this.getOne(queryWrapper);
    }
}
