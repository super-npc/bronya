package bronya.admin.module.rabbit.module.delayed.impl;

import org.springframework.stereotype.Service;

import bronya.admin.module.rabbit.dto.ConfirmCallbackReq;
import bronya.admin.module.rabbit.module.IRabbitEvent;
import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayedMsg;
import bronya.admin.module.rabbit.module.delayed.repository.RabbitDelayedMsgRepository;
import bronya.admin.module.rabbit.type.MqStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitEventDelayed implements IRabbitEvent {
    private final RabbitDelayedMsgRepository delayedEventService;

    @Override
    public void confirmCallback(ConfirmCallbackReq confirmCallbackReq, boolean ack, String cause) {
        log.info("broker收到[延迟id:{}]", confirmCallbackReq.getPrimaryId());
        RabbitDelayedMsg msg = delayedEventService.getById(confirmCallbackReq.getPrimaryId());
        MqStatus status = ack ? MqStatus.BROKER_ACK_SUCCESS : MqStatus.BROKER_ACK_FAILED;
        msg.setStatus(status);
        msg.setBrokerCause(cause);
        delayedEventService.updateById(msg);
    }
}
