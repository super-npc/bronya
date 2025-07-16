package bronya.admin.module.db.telegram.repository;

import lombok.RequiredArgsConstructor;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.db.telegram.domain.TelegramBotMessage;
import bronya.admin.module.db.telegram.mapper.TelegramBotMessageMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TelegramBotMessageRepository extends BaseRepository<TelegramBotMessageMapper, TelegramBotMessage> {
}
