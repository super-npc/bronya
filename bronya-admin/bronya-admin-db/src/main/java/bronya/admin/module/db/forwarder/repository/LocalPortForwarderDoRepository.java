package bronya.admin.module.db.forwarder.repository;

import java.util.List;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.RequiredArgsConstructor;
import bronya.admin.module.db.forwarder.domain.LocalPortForwarderDo;
import bronya.admin.module.db.forwarder.mapper.LocalPortForwarderDoMapper;

@Repository
@RequiredArgsConstructor
public class LocalPortForwarderDoRepository extends BaseRepository<LocalPortForwarderDoMapper, LocalPortForwarderDo> {

    public List<LocalPortForwarderDo> listEnable() {
        LambdaQueryWrapper<LocalPortForwarderDo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LocalPortForwarderDo::getEnable, true);
        return this.list(wrapper);
    }
}
