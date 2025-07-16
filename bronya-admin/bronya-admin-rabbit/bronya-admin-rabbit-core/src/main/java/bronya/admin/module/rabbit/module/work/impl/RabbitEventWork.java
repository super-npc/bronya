package bronya.admin.module.rabbit.module.work.impl;

import org.springframework.stereotype.Service;

import bronya.admin.module.rabbit.dto.ConfirmCallbackReq;
import bronya.admin.module.rabbit.module.IRabbitEvent;
import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;
import bronya.admin.module.rabbit.module.work.repository.RabbitWorkMsgRepository;
import bronya.admin.module.rabbit.type.MqStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitEventWork implements IRabbitEvent {
    private final RabbitWorkMsgRepository msgRepository;

    @Override
    public void confirmCallback(ConfirmCallbackReq confirmCallbackReq, boolean ack, String cause) {
        log.info("broker收到[work.id:{}]", confirmCallbackReq.getPrimaryId());
        RabbitWorkMsg msg = msgRepository.getById(confirmCallbackReq.getPrimaryId());
        MqStatus status = ack ? MqStatus.BROKER_ACK_SUCCESS : MqStatus.BROKER_ACK_FAILED;
        msg.setStatus(status);
        msg.setBrokerCause(cause);
        msgRepository.updateById(msg);
    }
}
