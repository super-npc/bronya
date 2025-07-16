package bronya.admin.module.db.telegram.repository;

import lombok.RequiredArgsConstructor;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.db.telegram.domain.TelegramBotChat;
import bronya.admin.module.db.telegram.mapper.TelegramBotChatMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TelegramBotChatRepository extends BaseRepository<TelegramBotChatMapper, TelegramBotChat> {
}
