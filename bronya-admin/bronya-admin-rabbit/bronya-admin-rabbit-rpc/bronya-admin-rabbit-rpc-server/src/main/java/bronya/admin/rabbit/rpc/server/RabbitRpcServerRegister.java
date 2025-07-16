package bronya.admin.rabbit.rpc.server;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.reflect.method.MethodUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cola.exception.SysException;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import bronya.admin.module.rabbit.module.rpc.util.RabbitRpcUtil;
import bronya.admin.module.rabbit.type.MsgType;
import bronya.admin.module.rabbit.util.ConfirmCallbackUtil;
import bronya.admin.rabbit.rpc.server.dto.CorrelationDataDto;
import bronya.admin.rabbit.rpc.server.dto.QueueBeanDto;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.TraceId;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端接收客户端的消息并映射到对应的方法执行
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitRpcServerRegister {
    private final org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory;
    private final RabbitAdmin rabbitAdmin;

    private final static Map<String, QueueBeanDto> rpcQueueMap = Maps.newConcurrentMap();

    @PostConstruct
    public void createQueue() {
        for (Class<?> beanClass : AdminBaseConstant.CLASSES_RABBIT_RPC) {
            // 参数说明：
            // 1. 队列名称
            // 2. 是否持久化
            // 3. 是否排他（exclusive）
            // 4. 是否自动删除（autoDelete）
            // 5. 其他参数（如 TTL）
            Method[] methods = MethodUtil.getMethods(beanClass);
            for (Method method : methods) {
                String rpcQueueName = RabbitRpcUtil.getRpcQueueName(beanClass, method);
                // int parameterCount = method.getParameterCount();
                // Assert.isTrue(parameterCount <= 1,
                // () -> new SysException(STR."方法参数不能超过1个,\{rpcQueueName},参数量:\{parameterCount}"));
                Queue queue = new Queue(rpcQueueName, false, false, true);
                rabbitAdmin.declareQueue(queue); // 动态声明队列
                rpcQueueMap.put(rpcQueueName, new QueueBeanDto(beanClass, method));
            }
        }
    }

    // 服务端消息监听器
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);

        List<String> queues = Lists.newArrayList();
        for (Class<?> beanClass : AdminBaseConstant.CLASSES_RABBIT_RPC) {
            Method[] methods = MethodUtil.getMethods(beanClass);
            for (Method method : methods) {
                String rpcQueueName = RabbitRpcUtil.getRpcQueueName(beanClass, method);
                queues.add(rpcQueueName);
            }
        }

        container.addQueueNames(queues.toArray(new String[] {}));
        MessageListener messageListener = message -> {
            String jsonCorrelationDataDto =
                MapUtil.getStr(message.getMessageProperties().getHeaders(), "spring_returned_message_correlation");
            CorrelationDataDto correlationDataDto = JSONObject.parseObject(jsonCorrelationDataDto, CorrelationDataDto.class);
            TraceId.setMdcTraceId(correlationDataDto.getTradeId());
            try {
                String queueName = message.getMessageProperties().getConsumerQueue();
                QueueBeanDto queueBeanDto = rpcQueueMap.get(queueName);
                Assert.notNull(queueBeanDto, () -> new SysException(STR."找不到对应的队列bean:\{queueName}"));

                Map<String, ?> beansOfType = SpringUtil.getBeansOfType(queueBeanDto.getBeanClass());
                // 一个实现类+本身就两个bean,超过则是多实现了,不允许
                Assert.isTrue(beansOfType.size() <= 2,
                    () -> new SysException(STR."一个实现类+本身就两个bean,超过则是多实现了,不允许:\{beansOfType.keySet()}"));
                        AtomicReference<Object> beanOpt = new AtomicReference<>();
                beansOfType.forEach((beanName, tempBean) -> {
                    if (!queueBeanDto.getBeanClass().getSimpleName().equals(beanName)) {
                        beanOpt.set(tempBean);
                    }
                });
                // 找出非接口本身的实现类
                Object bean = beanOpt.get();
                Object result = null;
                Method method = queueBeanDto.getMethod();
                if (method.getParameterCount() == 0) {
                    result = MethodUtil.invoke(bean, method);
                } else {
                    // 有一个请求参数
                    String paramsJson = new String(message.getBody(), StandardCharsets.UTF_8);
                    log.info("Receive a message message is {}", paramsJson);
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    List<Object> reqs = Lists.newArrayList();
                    JSONArray paramArr = JSONArray.parse(paramsJson);
                    for (int i = 0, parameterTypesLength = parameterTypes.length; i < parameterTypesLength; i++) {
                        Class<?> parameterType = parameterTypes[i];
                        Object param = paramArr.get(i);
                        if (ClassUtil.isJdkClass(parameterType)) {
                            reqs.add(ConvertUtil.convert(parameterType, param));
                        } else {
                            Object req = JSONObject.parseObject(JSONObject.toJSONString(param), parameterType);
                            reqs.add(req);
                        }
                    }
                    result = MethodUtil.invoke(bean, method, reqs.toArray());
                }
                String responseMsg = JSONObject.toJSONString(result);
                MessageProperties messageProperties = MessagePropertiesBuilder.newInstance()
                    .setCorrelationId(message.getMessageProperties().getCorrelationId()).build();

                CorrelationData correlationData = ConfirmCallbackUtil.createConfirmCallbackReq(
                    correlationDataDto.getTradeId(), MsgType.rpc, correlationDataDto.getPrimaryId());
                // 响应消息 这边就是如果没有绑定交换机和队列的话 消息应该直接传到对应的队列上面
                rabbitTemplate.send("", message.getMessageProperties().getReplyTo(),
                    new Message(responseMsg.getBytes(StandardCharsets.UTF_8), messageProperties), correlationData);
            } catch (Exception e) {
                log.error("rabbit rpc error", e);
            } finally {
                TraceId.remove();
            }
        };
        // 设置监听器
        container.setMessageListener(messageListener);
        return container;
    }

    private String toUpperCase(String receiveMsg) {
        return receiveMsg.toUpperCase();
    }
}
