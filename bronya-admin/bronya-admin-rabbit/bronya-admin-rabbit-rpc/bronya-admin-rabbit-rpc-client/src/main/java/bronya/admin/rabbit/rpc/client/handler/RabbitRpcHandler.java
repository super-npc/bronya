package bronya.admin.rabbit.rpc.client.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.alibaba.fastjson2.JSONObject;

import bronya.admin.module.rabbit.module.rpc.util.RabbitRpcUtil;
import bronya.admin.module.rabbit.type.MsgType;
import bronya.admin.module.rabbit.util.ConfirmCallbackUtil;
import bronya.shared.module.util.TraceId;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RabbitRpcHandler implements InvocationHandler {
    private final String name;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这里可以实现调用远程服务的逻辑
        // 比如通过 HTTP 请求调用远程接口
//        Assert.isTrue(args == null || args.length <= 1, () -> new BizException(STR."参数数量>1:\{args.length}")); // 无参或只有一个参数

        String corrId = UUID.randomUUID().toString();
        MessageProperties messageProperties = MessagePropertiesBuilder.newInstance().setCorrelationId(corrId).build();
        // 注意 这边如果使用sendAndReceive不指定replyTo回调队列 spring会默认帮我们添加一个回调队列
        // 格式默认 "amq.rabbitmq.reply-to" 前缀
        String traceId = TraceId.getTraceId();
        Long primaryId = -1L;
        CorrelationData correlationData = ConfirmCallbackUtil.createConfirmCallbackReq(traceId, MsgType.rpc, primaryId);
        final RabbitTemplate rabbitTemplate = SpringUtil.getBean(RabbitTemplate.class);
        String msg = "";
        if (CollUtil.size(args) > 0) {
            msg = JSONObject.toJSONString(args);
        }
        String rpcQueueName = RabbitRpcUtil.getRpcQueueName(method.getDeclaringClass(), method);
        Message message = rabbitTemplate.sendAndReceive("", rpcQueueName,
            new Message(msg.getBytes(StandardCharsets.UTF_8), messageProperties), correlationData);
        Class<?> returnType = method.getReturnType();
        if (void.class != returnType) {
            if (message == null) {
                return new Object();
            }
            String receiveJson = new String(message.getBody(), StandardCharsets.UTF_8);
            return JSONObject.parseObject(receiveJson, returnType);
        }
        return new Object();
    }
}