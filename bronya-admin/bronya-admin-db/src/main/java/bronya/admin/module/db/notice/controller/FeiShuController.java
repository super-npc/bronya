package bronya.admin.module.db.notice.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.db.amis.dto.AmisIdsDto;
import bronya.admin.module.db.notice.domain.FeiShuBot;
import bronya.admin.module.db.notice.repository.FeiShuBotRepository;
import bronya.admin.module.db.notice.service.FeiShuBizService;
import bronya.shared.module.common.type.LevelType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import npc.bulinke.external.module.feishu.req.MarkdownReq;
import npc.bulinke.external.module.feishu.req.MarkdownReq.CardDTO.Elements;

@Slf4j
@RestController
@RequestMapping("/admin/fei-shu")
@RequiredArgsConstructor
public class FeiShuController {
    private final FeiShuBotRepository feiShuRepository;
    private final FeiShuBizService feiShuBizService;

    /**
     * 验证飞书发送
     */
    @GetMapping("/send-test")
    public boolean sendTest(@AmisIds AmisIdsDto idsDto) {
        List<FeiShuBot> feiShuBotList = feiShuRepository.listByIds(idsDto.getIds());
        for (FeiShuBot feiShuBot : feiShuBotList) {
            ArrayList<Elements> elements = Lists.newArrayList(new Elements("- 测试机器人连通性"),
                new Elements(StrUtil.format("- {}", DateUtil.formatDateTime(new Date()))));
            MarkdownReq markdownReq = new MarkdownReq(StrUtil.format("{} - 测试发送", feiShuBot.getName()), elements);
            feiShuBizService.send(feiShuBot, LevelType.info, markdownReq);
        }
        return true;
    }

}
