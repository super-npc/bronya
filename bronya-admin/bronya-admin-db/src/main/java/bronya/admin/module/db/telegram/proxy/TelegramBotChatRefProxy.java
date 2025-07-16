package bronya.admin.module.db.telegram.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.telegram.domain.TelegramBotChatRef;
import bronya.admin.module.db.telegram.domain.TelegramBotChatRef.TelegramBotChatRefExt;
import bronya.admin.module.db.telegram.repository.TelegramBotChatRefRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotChatRefProxy extends DataProxy<TelegramBotChatRef> {
    private final TelegramBotChatRefRepository telegramBotChatRefRepository;

    @Override
    public void table(Map<String, Object> map) {
        TelegramBotChatRef telegramBotChatRef = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), TelegramBotChatRef.class);
        TelegramBotChatRefExt telegramBotChatRefExt = new TelegramBotChatRefExt();

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(TelegramBotChatRef.class, telegramBotChatRefExt));
    }
}
