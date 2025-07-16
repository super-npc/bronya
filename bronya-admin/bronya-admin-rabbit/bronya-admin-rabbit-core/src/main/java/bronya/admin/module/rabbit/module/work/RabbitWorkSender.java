package bronya.admin.module.rabbit.module.work;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.lang.Assert;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.cola.exception.BizException;

import bronya.admin.module.rabbit.module.fanout.util.FanoutUtil;
import bronya.admin.module.rabbit.module.work.annotation.RabbitWorkEntity;
import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;
import bronya.admin.module.rabbit.module.work.repository.biz.RabbitWorkBizService;
import bronya.admin.module.rabbit.type.MsgType;
import bronya.admin.module.rabbit.util.ConfirmCallbackUtil;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.TraceId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitWorkSender<T> {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitWorkBizService workBizService;

    public void send(T t) {
        String mdcTradeId = TraceId.getMdcTradeId();
        this.send(mdcTradeId, t, 0);
    }

    public void send(T t, int priority) {
        String mdcTradeId = TraceId.getMdcTradeId();
        this.send(mdcTradeId, t, priority);
    }

    public void send(String tradeId, T t, int priority) {
        Assert.checkBetween(priority, 0, 10, () -> new BizException(STR."rabbit.mq优先级[0-10]:\{priority}"));
        RabbitWorkEntity annotation = AnnotationUtil.getAnnotation(t.getClass(), RabbitWorkEntity.class);
        Assert.notNull(annotation, "发布订阅模式的传输实体作为Exchange未加上注解RabbitWorkEntity::{}", t.getClass().getName());
        String queueName = FanoutUtil.getExchangeName(t.getClass());
        RabbitWorkMsg rabbitWorkMsg = workBizService.saveSendMsg(tradeId, t);
        CorrelationData correlationData =
            ConfirmCallbackUtil.createConfirmCallbackReq(tradeId, MsgType.work, rabbitWorkMsg.getId());
        rabbitTemplate.convertAndSend("", queueName, rabbitWorkMsg.getId(), msg -> {
            msg.getMessageProperties().setPriority(priority);
            msg.getMessageProperties().setHeader(AdminBaseConstant.TRACE_ID, tradeId);
            return msg;
        }, correlationData);
        log.info("发送mq.work,msg:{}", t);
    }

}
