package bronya.admin.module.db.telegram.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.telegram.domain.TelegramBotChat;
import bronya.admin.module.db.telegram.domain.TelegramBotChat.TelegramBotChatExt;
import bronya.admin.module.db.telegram.repository.TelegramBotChatRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotChatProxy extends DataProxy<TelegramBotChat> {
    private final TelegramBotChatRepository telegramBotChatRepository;

    @Override
    public void table(Map<String, Object> map) {
        TelegramBotChat telegramBotChat = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), TelegramBotChat.class);
        TelegramBotChatExt telegramBotChatExt = new TelegramBotChatExt();

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(TelegramBotChat.class, telegramBotChatExt));
    }
}
