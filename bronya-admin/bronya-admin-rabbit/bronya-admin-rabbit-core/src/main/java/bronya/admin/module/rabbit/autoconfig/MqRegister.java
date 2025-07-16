package bronya.admin.module.rabbit.autoconfig;

import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.fastjson2.JSONObject;
import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;

import bronya.admin.base.cfg.SpringYaml;
import bronya.admin.module.rabbit.dto.ConfirmCallbackReq;
import bronya.admin.module.rabbit.module.IRabbitEvent;
import bronya.admin.module.rabbit.module.RabbitEventSwitch;
import bronya.admin.module.rabbit.module.delayed.domain.RabbitDelayedMsg;
import bronya.admin.module.rabbit.sdk.RabbitSdk;
import bronya.shared.module.util.TraceId;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 杂货铺
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MqRegister {
    private final SpringYaml springYaml;
    private final RabbitEventSwitch rabbitEventSwitch;

    @Bean
    public RabbitSdk rabbitSdk() {
        SpringYaml.Rabbitmq rabbitmq = springYaml.getRabbitmq();
        return new RabbitSdk(rabbitmq.getHost(), 15672, rabbitmq.getUsername(), rabbitmq.getPassword());
    }

    @Bean
    @SneakyThrows
    public Client rabbitHttpClient() {
        SpringYaml.Rabbitmq rabbitmq = springYaml.getRabbitmq();
        return new Client(new ClientParameters().url(STR."http://\{rabbitmq.getHost()}:15672/api/")
            .username(rabbitmq.getUsername()).password(rabbitmq.getPassword()));
    }

    /**
     * 设置Client properties connection_name 名称
     */
    @Bean
    public ConnectionNameStrategy defineConnectionNameStrategy() {
        return connectionFactory -> springYaml.getApplication().getName();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        // 时间有问题
        rabbitTemplate.setMessageConverter(new CustomGsonMessageConverter());

        // 当消息成功到达 RabbitMQ Broker 时，会触发 ConfirmCallback
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            Assert.notNull(correlationData, "rabbit发送未设置correlationData");
            String correlation = correlationData.getId();
            ConfirmCallbackReq confirmCallbackReq = JSONObject.parseObject(correlation, ConfirmCallbackReq.class);
            TraceId.setMdcTraceId(confirmCallbackReq.getTradeId());
            IRabbitEvent iRabbitEvent = rabbitEventSwitch.switchService(confirmCallbackReq.getMsgType());
            try {
                iRabbitEvent.confirmCallback(confirmCallbackReq, ack, cause);
            } catch (Exception e) {
                log.error(STR."处理confirmCallback异常,类型:\{confirmCallbackReq.getMsgType()}", e);
                TraceId.remove();
            }
        });
        // 当消息无法路由到队列时，会触发 ReturnCallback
        rabbitTemplate.setReturnsCallback(returned -> {
            // 当消息无法路由到队列时的处理逻辑
            Message message = returned.getMessage();
            String exchange = returned.getExchange();
            String routingKey = returned.getRoutingKey();
            int replyCode = returned.getReplyCode();
            String replyText = returned.getReplyText();
            log.error("消息无法路由到队列! Exchange: {}, RoutingKey: {}, ReplyCode: {}, ReplyText: {}, Message: {}", exchange,
                routingKey, replyCode, replyText, new String(message.getBody()));
        });
        return rabbitTemplate;
    }

    @RequiredArgsConstructor
    public static class CustomGsonMessageConverter implements MessageConverter {
        @Override
        public Message toMessage(Object object, MessageProperties messageProperties) {
            String json = JSONUtil.toJsonStr(object);
            SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();
            simpleMessageConverter.addAllowedListPatterns(RabbitDelayedMsg.class.getName());
            return simpleMessageConverter.toMessage(json, messageProperties);
        }

        @Override
        public Object fromMessage(Message message) {
            String json = new String(message.getBody());
            log.warn("奇怪,不会走到这里:{}", json);
            return null;
        }
    }
}
