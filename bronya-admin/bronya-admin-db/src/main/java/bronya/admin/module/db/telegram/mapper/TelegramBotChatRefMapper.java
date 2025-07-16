package bronya.admin.module.db.telegram.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bronya.admin.module.db.telegram.domain.TelegramBotChatRef;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TelegramBotChatRefMapper extends BaseMapper<TelegramBotChatRef> {
}
