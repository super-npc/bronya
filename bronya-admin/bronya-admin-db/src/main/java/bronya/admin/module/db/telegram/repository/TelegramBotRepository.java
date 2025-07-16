package bronya.admin.module.db.telegram.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.db.telegram.domain.TelegramBot;
import bronya.admin.module.db.telegram.mapper.TelegramBotMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TelegramBotRepository extends BaseRepository<TelegramBotMapper, TelegramBot> {
    public List<TelegramBot> listEnable() {
        LambdaQueryWrapper<TelegramBot> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TelegramBot::getEnable, true);
        return this.list(wrapper);
    }
}
