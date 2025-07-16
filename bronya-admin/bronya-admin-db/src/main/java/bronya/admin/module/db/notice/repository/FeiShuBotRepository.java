package bronya.admin.module.db.notice.repository;

import bronya.admin.module.db.notice.mapper.FeiShuBotMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.db.notice.domain.FeiShuBot;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FeiShuBotRepository extends BaseRepository<FeiShuBotMapper, FeiShuBot> {

    public Optional<FeiShuBot> findByName(String name) {
        LambdaQueryWrapper<FeiShuBot> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FeiShuBot::getName, name);
        return this.getOneOpt(wrapper);
    }
}
