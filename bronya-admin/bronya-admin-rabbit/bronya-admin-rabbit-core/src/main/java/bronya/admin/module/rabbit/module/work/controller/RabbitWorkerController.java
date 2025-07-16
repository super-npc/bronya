package bronya.admin.module.rabbit.module.work.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.db.amis.dto.AmisIdsDto;
import bronya.admin.module.rabbit.module.work.RabbitWorkSender;
import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;
import bronya.admin.module.rabbit.module.work.repository.RabbitWorkMsgRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/rabbit/worker")
@RequiredArgsConstructor
public class RabbitWorkerController {
    private final RabbitWorkSender sender;
    private final RabbitWorkMsgRepository msgRepository;

    @SneakyThrows
    @GetMapping("/re-send")
    public void reSend(@AmisIds AmisIdsDto idsDto) {
        List<Long> ids = idsDto.getIds();
        for (Long id : ids) {
            RabbitWorkMsg workMsg = msgRepository.getById(id);
            Class<?> aClass = Class.forName(workMsg.getEntityClass());
            Object o = JSONObject.parseObject(workMsg.getMsg(), aClass);
            sender.send(workMsg.getTradeId(), o, 0);
        }
    }
}
