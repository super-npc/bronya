package bronya.admin.module.db.telegram.service;

import bronya.admin.module.db.telegram.domain.TelegramBot;
import bronya.admin.module.db.telegram.domain.TelegramBotChat;
import bronya.admin.module.db.telegram.repository.TelegramBotChatRefRepository;
import bronya.admin.module.db.telegram.repository.TelegramBotChatRepository;
import bronya.admin.module.db.telegram.repository.TelegramBotRepository;
import bronya.admin.module.db.proxy.repository.ProxyDoRepository;
import com.alibaba.cola.exception.BizException;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import npc.bulinke.external.module.telegram.TelegramBotCli;
import npc.bulinke.external.module.telegram.req.SendMessageReq;
import npc.bulinke.external.module.telegram.resp.GetMeResp;
import npc.bulinke.external.module.telegram.resp.GetUpdateResp;
import npc.bulinke.external.module.telegram.resp.SendMessageResp;
import npc.bulinke.external.module.telegram.resp.TelegramRespBase;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Assert;
import org.springframework.stereotype.Service;

import java.net.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotService {
    private final TelegramBotRepository telegramBotRepository;
    private final ProxyDoRepository proxyDoRepository;
    private final TelegramBotChatRepository telegramBotChatRepository;
    private final TelegramBotChatRefRepository telegramBotChatRefRepository;

    public void bindChatRef(TelegramBot bot, List<TelegramBotChat> telegramBotChats) {
        List<TelegramBotChat> groupChats = telegramBotChats.stream().filter(chat -> Lists.newArrayList("supergroup", "group").contains(chat.getType())).toList();
        for (TelegramBotChat groupChat : groupChats) {
            telegramBotChatRefRepository.bind(bot, groupChat);
        }
    }

    public void updateByGetMe(TelegramBot bot) {
        TelegramBotCli telegramBotCli = this.getCli(bot);
        TelegramRespBase<GetMeResp> me = telegramBotCli.getMe(bot.getToken());
        Assert.isTrue(me.getOk(), "读取getMe异常");
        GetMeResp result = me.getResult();
        bot.setFirstName(result.getFirstName());
        bot.setUsername(result.getUsername());
        telegramBotRepository.updateById(bot);
    }

    public void refreshByGetUpdates(TelegramBot bot) {
        TelegramRespBase<GetUpdateResp[]> updates = this.getUpdates(bot);
        Assert.isTrue(updates.getOk(), () -> new BizException(STR."code:\{updates.getErrorCode()} \{updates.getDescription()}"));
        GetUpdateResp[] result = updates.getResult();
        List<GetUpdateResp.Chat> chats = Arrays.stream(result).map(getUpdateResp -> {
            GetUpdateResp.Message message = getUpdateResp.getMessage();
            if (message == null) {
                return null;
            }
            return message.getChat();
        }).filter(Objects::nonNull).toList();
        chats = CollUtil.distinct(chats);
        if (CollUtil.isNotEmpty(chats)) {
            List<TelegramBotChat> telegramBotChats = BeanUtil.copyToList(chats, TelegramBotChat.class);
            boolean b = telegramBotChatRepository.saveOrUpdateBatch(telegramBotChats, 1000);
            log.info("更新chats:{}", b);
            this.bindChatRef(bot, telegramBotChats); // 绑定chat
        }
    }

    public TelegramRespBase<GetUpdateResp[]> getUpdates(TelegramBot bot) {
        TelegramBotCli telegramBotCli = this.getCli(bot);
        return telegramBotCli.getUpdates(bot.getToken());
    }

    public TelegramRespBase<SendMessageResp> sendMessage(TelegramBot bot, SendMessageReq sendMessageReq) {
        TelegramBotCli telegramBotCli = this.getCli(bot);
        return telegramBotCli.sendMessage(bot.getToken(), sendMessageReq);
    }

    public TelegramBotCli getCli(TelegramBot telegramBot) {
        Proxy proxy = null;
        if (telegramBot.getProxyEnable()) {
            proxy = proxyDoRepository.findProxy(telegramBot.getProxyId()).orElse(null);
        }
        return new TelegramBotCli(proxy);
    }
}
