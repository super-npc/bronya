package bronya.admin.module.rabbit.module.fanout.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;

import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanout;
import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanoutMsg;
import bronya.admin.module.rabbit.module.fanout.repository.RabbitFanoutMsgRepository;
import bronya.admin.module.rabbit.module.fanout.repository.RabbitFanoutRepository;
import bronya.admin.module.rabbit.type.MqStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitFanoutBizService {
    private final RabbitFanoutRepository fanoutRepository;
    private final RabbitFanoutMsgRepository msgRepository;

    public RabbitFanoutMsg saveSendMsg(String mdcTradeId, Object o) {
        String objName = o.getClass().getName();
        RabbitFanout fanout = fanoutRepository.findByClassName(objName);

        RabbitFanoutMsg rabbitWorkMsg = new RabbitFanoutMsg();
        rabbitWorkMsg.setStatus(MqStatus.CREATE);
        rabbitWorkMsg.setEntityClass(objName);
        rabbitWorkMsg.setTradeId(mdcTradeId);
        rabbitWorkMsg.setMsg(JSONObject.toJSONString(o));
        rabbitWorkMsg.setRabbitFanoutId(fanout.getId());
        msgRepository.save(rabbitWorkMsg);
        return rabbitWorkMsg;
    }
}
