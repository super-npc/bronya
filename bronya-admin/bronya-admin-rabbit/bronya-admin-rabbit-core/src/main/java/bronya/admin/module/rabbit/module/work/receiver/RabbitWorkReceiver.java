package bronya.admin.module.rabbit.module.work.receiver;

import java.util.Collection;
import java.util.Map;

import org.dromara.hutool.core.map.MapUtil;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Multimap;
import com.rabbitmq.client.Channel;

import bronya.admin.module.rabbit.commons.MqConstant;
import bronya.admin.module.rabbit.module.fanout.util.FanoutUtil;
import bronya.admin.module.rabbit.module.work.IWorkMq;
import bronya.admin.module.rabbit.module.work.repository.RabbitWorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitWorkReceiver implements ApplicationListener<ApplicationReadyEvent> {
    private final ConnectionFactory connectionFactory;
    private final RabbitWorkRepository workRepository;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Multimap<Class<?>, IWorkMq<?>> rabbitFanout = MqConstant.RABBIT_WORK;
        Map<Class<?>, Collection<IWorkMq<?>>> map = rabbitFanout.asMap();
        workRepository.appStartedInitData(map);
        for (Map.Entry<Class<?>, Collection<IWorkMq<?>>> entry : map.entrySet()) {
            Class<?> fanoutParam = entry.getKey();
            Collection<IWorkMq<?>> value = entry.getValue();
            String queueName = FanoutUtil.getExchangeName(fanoutParam);
            try (Connection connection = connectionFactory.createConnection()) {
                // 设置队列参数，启用优先级功能
                Map<String, Object> arguments = MapUtil.of("x-max-priority", 10);// 设置最大优先级为10（可以根据需要调整）
                Channel channel = connection.createChannel(false);
                channel.queueDeclare(queueName, false, false, false, arguments);
                // channel.queueBind(queueName, queueName, "");
                channel.basicQos(1); // 每次只接收一条未确认的消息
                log.info("mq.工作模式channel绑定,交换机:{},队列:{}", queueName, queueName);

                new RabbitWorkReceiverClient(channel, queueName, value).registerWork();
            }catch(Exception e){
                log.error("初始化rabbit.work异常",e);
                throw e;
            }
        }
    }
}
