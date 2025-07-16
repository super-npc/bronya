package bronya.admin.module.db.telegram.repository;

import bronya.admin.module.db.telegram.domain.TelegramBot;
import bronya.admin.module.db.telegram.domain.TelegramBotChat;
import lombok.RequiredArgsConstructor;
import org.dromara.mpe.base.repository.BaseRepository;
import bronya.admin.module.db.telegram.domain.TelegramBotChatRef;
import bronya.admin.module.db.telegram.mapper.TelegramBotChatRefMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TelegramBotChatRefRepository extends BaseRepository<TelegramBotChatRefMapper, TelegramBotChatRef> {
    public void bind(TelegramBot bot, TelegramBotChat chat) {
        TelegramBotChatRef telegramBotChatRef = new TelegramBotChatRef();
        telegramBotChatRef.setBotId(bot.getId());
        telegramBotChatRef.setChatId(chat.getId());
        try {
            this.save(telegramBotChatRef);
        } catch (Exception e) {
            // 不采取提前查询存在,重复提交违反唯一约束,忽略该报错
        }
    }
}
