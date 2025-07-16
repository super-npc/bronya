package bronya.admin.module.rabbit.module.delayed;

import java.util.Date;
import java.util.Map;

import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.date.DateTime;
import org.dromara.hutool.core.date.DateUnit;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Maps;
import com.rabbitmq.client.Channel;

import bronya.admin.module.rabbit.module.delayed.annotation.MqDelayed;
import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayed;
import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayedMsg;
import bronya.admin.module.rabbit.module.delayed.repository.RabbitDelayedMsgRepository;
import bronya.admin.module.rabbit.module.delayed.repository.RabbitDelayedRepository;
import bronya.admin.module.rabbit.type.MqStatus;
import bronya.admin.module.rabbit.type.MsgType;
import bronya.admin.module.rabbit.util.ConfirmCallbackUtil;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.TraceId;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 公共的延迟队列
 */
@Slf4j
@Component
@RequiredArgsConstructor
// @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "prd", matchIfMissing = true)
public class RabbitDelayedQueue<T> {
    private final RabbitDelayedRepository delayedMqService;
    private final RabbitDelayedMsgRepository msgRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String queueName = "_delay";
    private final String exchange = "_exchange_delay";
    private final String routingKey = "_route_delay";

    @SneakyThrows
    @RabbitListener(queuesToDeclare = @Queue(queueName), concurrency = "1")
    public void receive(Message message, @Payload String msgId, Channel channel) {
         log.info("收到延迟id:{}", msgId);
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        Object o = headers.get(AdminBaseConstant.TRACE_ID);
        TraceId.setMdcTraceId(o.toString());

        RabbitDelayedMsg msg = msgRepository.getById(ConvertUtil.toLong(msgId));
        IDelayedMq bean = null;
        Object object = null;
        try {
            RabbitDelayed mqDelayedDo = delayedMqService.getById(msg.getRabbitDelayedId());
            String entityClass = mqDelayedDo.getEntityClass();
            Class<?> entity = Class.forName(entityClass);
            object = JSONObject.parseObject(msg.getJson(), entity);
            MqDelayed mqDelayed = entity.getAnnotation(MqDelayed.class);

            Class<? extends IDelayedMq> execClass = mqDelayed.execClass();
            bean = SpringUtil.getBean(execClass);

            msg.setExecTime(new Date());
            bean.execute(object);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 签收
            msg.setStatus(MqStatus.SPENT_SUCCESS);
        } catch (Exception e) {
            log.error("执行延迟内容异常", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            msg.setStatus(MqStatus.SPENT_FAILED);
            if (null != bean) {
                bean.exception(object, e);
            }
        } finally {
            channel.close();
            msgRepository.updateById(msg);
            if (null != bean) {
                bean.finalExec(object);
            }
            TraceId.remove();
        }
    }

    public void send(T obj) {
        MqDelayed mqDelayed = obj.getClass().getAnnotation(MqDelayed.class);
        DateUnit unit = mqDelayed.timeUnit();
        int time = mqDelayed.time();
        switch (mqDelayed.type()) {
            case onTime -> {
                this.send(obj, unit, time);
                DateTime dateTime = this.calOffset(unit, time)._2;
                log.info("定时.延迟任务:{} - {}", DateUtil.formatDateTime(dateTime), obj);
            }
            case random -> {
                int max = Math.max(mqDelayed.timeMax(), time);
                int i = RandomUtil.randomInt(time, max);
                this.send(obj, unit, i);
                DateTime dateTime = this.calOffset(unit, i)._2;
                log.info("随机:{}({} ~ {}) = {}.延迟任务:{} - {}", unit, time, max, i, DateUtil.formatDateTime(dateTime),
                    obj);
            }
        }
    }

    public void send(Object obj, DateUnit unit, int time) {
        Tuple2<Long, DateTime> integerDateTimeTuple2 = this.calOffset(unit, time);
        DateTime dateTime = integerDateTimeTuple2._2;
        // todo 事务问题
        RabbitDelayedMsg msg = this.newMqDelayed(obj, integerDateTimeTuple2);
        // 绑定一个id,这样ConfirmCallback回调就能收到
        String mdcTradeId = TraceId.getMdcTradeId();
        CorrelationData correlationData =
            ConfirmCallbackUtil.createConfirmCallbackReq(mdcTradeId, MsgType.delayed, msg.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, msg.getId(), correlationDate -> {
            Long offMillis = integerDateTimeTuple2._1;
            correlationDate.getMessageProperties().setDelayLong(offMillis);
            correlationDate.getMessageProperties().getHeaders().put(AdminBaseConstant.TRACE_ID, mdcTradeId);
            return correlationDate;
        }, correlationData);

        log.info("∴新增延迟任务:{}:预计执行:{},[延迟id:{}]", obj.getClass().getSimpleName(), DateUtil.formatDateTime(dateTime),
            correlationData.getId());
    }

    public RabbitDelayedMsg newMqDelayed(Object obj, Tuple2<Long, DateTime> integerDateTimeTuple2) {
        String entityClass = obj.getClass().getName();
        RabbitDelayed rabbitDelayed = delayedMqService.findByEntityClass(entityClass);
        if (rabbitDelayed == null) {
            rabbitDelayed = new RabbitDelayed();
            rabbitDelayed.setEntityClass(entityClass);
            delayedMqService.save(rabbitDelayed);
        }
        String json = JSONObject.toJSONString(obj);
        RabbitDelayedMsg mqDelayedEvent = new RabbitDelayedMsg();
        mqDelayedEvent.setRabbitDelayedId(rabbitDelayed.getId());
        DateTime dateTime = integerDateTimeTuple2._2;
        mqDelayedEvent.setDelayedTime(dateTime);
        mqDelayedEvent.setStatus(MqStatus.CREATE);
        mqDelayedEvent.setJson(json);
        msgRepository.save(mqDelayedEvent);
        return mqDelayedEvent;
    }

    /**
     * // 计算预计执行时间
     */
    private Tuple2<Long, DateTime> calOffset(DateUnit unit, int time) {
        long offMillis = unit.getMillis() * time;
        DateTime dateTime = DateUtil.offsetSecond(new Date(), (int)(offMillis / 1000));
        return Tuple.of(offMillis, dateTime);
    }

    @Bean
    public org.springframework.amqp.core.Queue generalQueue() {
        return new org.springframework.amqp.core.Queue(queueName);
    }

    @Bean
    CustomExchange generalExchange() {
        Map<String, Object> map = Maps.newConcurrentMap();
        // Direct：定向，把消息交给符合指定routing key 的队列
        map.put("x-delayed-type", "direct");
        /**
         * 1.交换机名称 2.交换机类型 3.是否需要持久化 4.是否需要自动删除 5.其他参数
         */
        return new CustomExchange(exchange, "x-delayed-message", true, false, map);
    }

    // 绑定 将队列和交换机绑定, 并设置用于匹配键

    @Bean
    Binding bindingDirect(@Qualifier("generalExchange") CustomExchange exchange,
        @Qualifier("generalQueue") org.springframework.amqp.core.Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }
}
