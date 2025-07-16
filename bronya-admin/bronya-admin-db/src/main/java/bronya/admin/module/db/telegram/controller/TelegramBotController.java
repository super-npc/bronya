package bronya.admin.module.db.telegram.controller;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.db.amis.dto.AmisIdsDto;
import bronya.admin.module.db.telegram.domain.TelegramBot;
import bronya.admin.module.db.telegram.repository.TelegramBotRepository;
import bronya.admin.module.db.telegram.service.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import npc.bulinke.external.module.telegram.req.SendMessageReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/telegram-bot")
@RequiredArgsConstructor
public class TelegramBotController {
    private final TelegramBotRepository telegramBotRepository;
    private final TelegramBotService telegramBotService;

    @GetMapping("/update-by-get-me")
    public boolean updateByGetMe(@AmisIds AmisIdsDto idsDto) {
        List<TelegramBot> telegramBots = telegramBotRepository.listByIds(idsDto.getIds());
        for (TelegramBot telegramBot : telegramBots) {
            telegramBotService.updateByGetMe(telegramBot);
        }
        return true;
    }

    @GetMapping("/send-test-update")
    public boolean sendTestUpdate(@AmisIds AmisIdsDto idsDto) {
        List<TelegramBot> telegramBots = telegramBotRepository.listByIds(idsDto.getIds());
        for (TelegramBot telegramBot : telegramBots) {
            telegramBotService.refreshByGetUpdates(telegramBot);
        }
        return true;
    }

    @GetMapping("/send-test")
    public boolean sendTest(@AmisIds AmisIdsDto idsDto) {
        List<TelegramBot> telegramBots = telegramBotRepository.listByIds(idsDto.getIds());
        for (TelegramBot telegramBot : telegramBots) {
            SendMessageReq sendMessageReq = new SendMessageReq("-1002571018421", "验证发送");
            telegramBotService.sendMessage(telegramBot, sendMessageReq);
        }
        return true;
    }
}
