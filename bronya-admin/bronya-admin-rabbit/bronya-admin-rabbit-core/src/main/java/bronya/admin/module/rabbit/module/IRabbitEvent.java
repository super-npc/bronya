package bronya.admin.module.rabbit.module;

import bronya.admin.module.rabbit.dto.ConfirmCallbackReq;

public interface IRabbitEvent {
    void confirmCallback(ConfirmCallbackReq confirmCallbackReq, boolean ack, String cause);
}
