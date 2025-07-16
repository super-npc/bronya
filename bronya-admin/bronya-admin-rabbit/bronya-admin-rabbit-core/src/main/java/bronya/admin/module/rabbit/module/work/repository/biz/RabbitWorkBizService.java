package bronya.admin.module.rabbit.module.work.repository.biz;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;

import bronya.admin.module.rabbit.module.work.domain.RabbitWork;
import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;
import bronya.admin.module.rabbit.module.work.repository.RabbitWorkMsgRepository;
import bronya.admin.module.rabbit.module.work.repository.RabbitWorkRepository;
import bronya.admin.module.rabbit.type.MqStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitWorkBizService {
    private final RabbitWorkMsgRepository msgRepository;
    private final RabbitWorkRepository workRepository;
    public RabbitWorkMsg saveSendMsg(String mdcTradeId, Object o){
        String objName = o.getClass().getName();
        RabbitWork rabbitWork = workRepository.findByClassName(objName);
        RabbitWorkMsg rabbitWorkMsg = new RabbitWorkMsg();
        rabbitWorkMsg.setStatus(MqStatus.CREATE);
        rabbitWorkMsg.setEntityClass(objName);
        rabbitWorkMsg.setTradeId(mdcTradeId);
        rabbitWorkMsg.setMsg(JSONObject.toJSONString(o));
        rabbitWorkMsg.setRabbitWorkId(rabbitWork.getId());
        msgRepository.save(rabbitWorkMsg);
        return rabbitWorkMsg;
    }
}
