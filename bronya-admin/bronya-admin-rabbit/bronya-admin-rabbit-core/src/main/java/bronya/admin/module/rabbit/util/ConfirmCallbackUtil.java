package bronya.admin.module.rabbit.util;

import org.springframework.amqp.rabbit.connection.CorrelationData;

import com.alibaba.fastjson2.JSONObject;

import bronya.admin.module.rabbit.dto.ConfirmCallbackReq;
import bronya.admin.module.rabbit.type.MsgType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfirmCallbackUtil {

    public CorrelationData createConfirmCallbackReq(String tradeId,MsgType msgType, Long primaryId){
        ConfirmCallbackReq confirmCallbackReq = new ConfirmCallbackReq(tradeId,msgType,primaryId);
        return new CorrelationData(JSONObject.toJSONString(confirmCallbackReq));
    }
}
