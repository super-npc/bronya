package bronya.admin.module.rabbit.module.fanout;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.lang.Assert;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import bronya.admin.module.rabbit.dto.RabbitFanoutExchangeEntity;
import bronya.admin.module.rabbit.module.fanout.domain.RabbitFanoutMsg;
import bronya.admin.module.rabbit.module.fanout.service.RabbitFanoutBizService;
import bronya.admin.module.rabbit.module.fanout.util.FanoutUtil;
import bronya.admin.module.rabbit.type.MsgType;
import bronya.admin.module.rabbit.util.ConfirmCallbackUtil;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.TraceId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitFanoutSender<T> {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitFanoutBizService fanoutBizService;

    public void send(T t) {
        RabbitFanoutExchangeEntity annotation =
            AnnotationUtil.getAnnotation(t.getClass(), RabbitFanoutExchangeEntity.class);
        Assert.notNull(annotation, "发布订阅模式的传输实体作为Exchange未加上注解RabbitFanoutExchangeEntity::{}", t.getClass().getName());
        String exchangeName = FanoutUtil.getExchangeName(t.getClass());
        String mdcTradeId = TraceId.getMdcTradeId();
        RabbitFanoutMsg rabbitFanoutMsg = fanoutBizService.saveSendMsg(mdcTradeId, t);
        CorrelationData correlationData = ConfirmCallbackUtil.createConfirmCallbackReq(mdcTradeId, MsgType.fanout, rabbitFanoutMsg.getId());
        rabbitTemplate.convertAndSend(exchangeName, null, t, msg -> {
            msg.getMessageProperties().setHeader(AdminBaseConstant.TRACE_ID, mdcTradeId);
            return msg;
        }, correlationData);
    }

}
