package bronya.admin.module.rabbit.module.fanout.receiver;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.json.JSONUtil;

import com.rabbitmq.client.*;

import bronya.admin.module.rabbit.dto.MqDtoBase;
import bronya.admin.module.rabbit.module.fanout.IFanoutMq;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.MyRefUtil;
import bronya.shared.module.util.TraceId;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RabbitFanoutReceiverClient {
    private final Channel channel;
    private final String exchangeName;
    private final Collection<IFanoutMq<?>> iFanoutMqs;

    public boolean isOpen() {
        return channel.isOpen();
    }

    @SneakyThrows
    public void registerFanout() {
        for (IFanoutMq<?> iFanoutMq : iFanoutMqs) {
            // 交换机名称.实现类名称
            String queueName = STR."\{exchangeName}.\{StrUtil.lowerFirst(iFanoutMq.getClass().getSimpleName())}";
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, exchangeName, "");
            log.info("发布订阅channel绑定,交换机:{},队列:{}",exchangeName,queueName);

            DeliverCallback deliverCallback = new DeliverCallback() {
                @SneakyThrows
                @Override
                public void handle(String consumerTag, Delivery delivery) {
                    AMQP.BasicProperties properties = delivery.getProperties();
                    Object o = properties.getHeaders().get(AdminBaseConstant.TRACE_ID);
                    TraceId.setMdcTraceId(o.toString());

                    String json = StrUtil.str(delivery.getBody(), StandardCharsets.UTF_8);
                    List<Type> allGenericClassByExtendClass = MyRefUtil.getAllGenericClassByExtendClass(iFanoutMq.getClass());
                    Type genericsType = CollUtil.getFirst(allGenericClassByExtendClass);
                    Class<?> objectClass = Class.forName(genericsType.getTypeName());
                    Object bean = JSONUtil.parseObj(json).toBean(objectClass);
                    MqDtoBase objectFanoutBase = new MqDtoBase<>();
                    objectFanoutBase.setReceiveTime(new Date());
                    objectFanoutBase.setData(bean);
                    try {
                        iFanoutMq.deliverCallback(consumerTag, delivery, objectFanoutBase);
                    } catch (Exception e) {
                        iFanoutMq.deliverCallbackException(objectFanoutBase, e);
                    }finally {
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        TraceId.remove();
                    }
                }
            };
            CancelCallback cancelCallback = new CancelCallback() {
                @Override
                public void handle(String consumerTag) {
                    // 处理取消的回调
                    iFanoutMq.cancelCallback(consumerTag);
                }
            };
            // 手动确认
            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);
        }
    }
}
