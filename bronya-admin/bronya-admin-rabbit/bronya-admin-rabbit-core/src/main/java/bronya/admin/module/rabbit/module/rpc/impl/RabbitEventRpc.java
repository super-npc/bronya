package bronya.admin.module.rabbit.module.rpc.impl;

import org.springframework.stereotype.Service;

import bronya.admin.module.rabbit.dto.ConfirmCallbackReq;
import bronya.admin.module.rabbit.module.IRabbitEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitEventRpc implements IRabbitEvent {
    @Override
    public void confirmCallback(ConfirmCallbackReq confirmCallbackReq, boolean ack, String cause) {

    }
}
