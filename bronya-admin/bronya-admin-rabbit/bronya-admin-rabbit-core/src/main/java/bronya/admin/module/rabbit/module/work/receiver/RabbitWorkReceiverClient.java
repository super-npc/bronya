package bronya.admin.module.rabbit.module.work.receiver;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.json.JSONUtil;

import com.rabbitmq.client.*;

import bronya.admin.module.rabbit.dto.MqDtoBase;
import bronya.admin.module.rabbit.module.work.IWorkMq;
import bronya.admin.module.rabbit.module.work.domain.RabbitWorkMsg;
import bronya.admin.module.rabbit.module.work.repository.RabbitWorkMsgRepository;
import bronya.admin.module.rabbit.type.MqStatus;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.MyRefUtil;
import bronya.shared.module.util.TraceId;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RabbitWorkReceiverClient {
    private final Channel channel;
    private final String queueName; // 要使用相同的队列名称
    private final Collection<IWorkMq<?>> iWorkMqs;
    private final RabbitWorkMsgRepository msgRepository = SpringUtil.getBean(RabbitWorkMsgRepository.class);

    public boolean isOpen() {
        return channel.isOpen();
    }

    @SneakyThrows
    public void registerWork() {
        for (IWorkMq<?> iWorkMq : iWorkMqs) {
            // 交换机名称.实现类名称
            DeliverCallback deliverCallback = new DeliverCallback() {
                @SneakyThrows
                @Override
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    AMQP.BasicProperties properties = delivery.getProperties();
                    Object o = properties.getHeaders().get(AdminBaseConstant.TRACE_ID);
                    TraceId.setMdcTraceId(o.toString());

                    String body = StrUtil.str(delivery.getBody(), Charset.defaultCharset());
                    RabbitWorkMsg msg = msgRepository.getById(body);
                    List<Type> allGenericClassByExtendClass =
                        MyRefUtil.getAllGenericClassByExtendClass(iWorkMq.getClass());
                    Type genericsType = CollUtil.getFirst(allGenericClassByExtendClass);
                    Class<?> objectClass = Class.forName(genericsType.getTypeName());
                    Object bean = JSONUtil.parseObj(msg.getMsg()).toBean(objectClass);
                    MqDtoBase objectMqDtoBase = new MqDtoBase<>();
                    objectMqDtoBase.setReceiveTime(new Date());
                    objectMqDtoBase.setData(bean);
                    try {
                        iWorkMq.deliverCallback(consumerTag, delivery, objectMqDtoBase);
                        msg.setStatus(MqStatus.SPENT_SUCCESS);
                    } catch (Exception e) {
                        iWorkMq.deliverCallbackException(objectMqDtoBase, e);
                        msg.setStatus(MqStatus.SPENT_FAILED);
                    } finally {
                        // 手动提交
                        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        msgRepository.updateById(msg);
                        TraceId.remove();
                    }
                }
            };
            CancelCallback cancelCallback = new CancelCallback() {
                @Override
                public void handle(String consumerTag) throws IOException {
                    // 处理取消的回调
                    iWorkMq.cancelCallback(consumerTag);
                }
            };
            // 手动确认
            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);
        }
    }
}
