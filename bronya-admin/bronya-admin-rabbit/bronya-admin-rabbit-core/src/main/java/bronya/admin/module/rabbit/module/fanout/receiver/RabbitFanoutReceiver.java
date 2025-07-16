package bronya.admin.module.rabbit.module.fanout.receiver;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.dromara.hutool.core.thread.ThreadUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.rabbitmq.client.Channel;

import bronya.admin.module.rabbit.commons.MqConstant;
import bronya.admin.module.rabbit.module.fanout.IFanoutMq;
import bronya.admin.module.rabbit.module.fanout.repository.RabbitFanoutRepository;
import bronya.admin.module.rabbit.module.fanout.util.FanoutUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitFanoutReceiver implements ApplicationListener<ApplicationReadyEvent> {
    private final ConnectionFactory connectionFactory;
    private final RabbitFanoutRepository fanoutRepository;

    @SneakyThrows
    @Override
    public void onApplicationEvent(@NotNull ApplicationReadyEvent event) {
        log.info("接收到 ApplicationReadyEvent，初始化 Fanout RabbitFanoutReceiverBean...");
        final List<RabbitFanoutReceiverClient> FANOUT_CLIENTS = Lists.newArrayList();
        Multimap<Class<?>, IFanoutMq<?>> rabbitFanout = MqConstant.RABBIT_FANOUT;
        Map<Class<?>, Collection<IFanoutMq<?>>> map = rabbitFanout.asMap();
        fanoutRepository.appStartedInitData(map);
        try (Connection connection = connectionFactory.createConnection()) {
            log.info("Fanout RabbitMQ 连接已建立。");
            for (Map.Entry<Class<?>, Collection<IFanoutMq<?>>> entry : map.entrySet()) {
                Class<?> fanoutParam = entry.getKey();
                String exchangeName = FanoutUtil.getExchangeName(fanoutParam);
                Channel channel = connection.createChannel(false);
                log.info("Fanout 声明交换机：{}", exchangeName);
                // 相同交换机注册一次
                channel.exchangeDeclare(exchangeName, "fanout");

                // 分批注册队列
                Collection<IFanoutMq<?>> iFanoutMqs = entry.getValue();
                RabbitFanoutReceiverClient rabbitFanoutReceiverClient = new RabbitFanoutReceiverClient(channel, exchangeName, iFanoutMqs);
                rabbitFanoutReceiverClient.registerFanout();
                FANOUT_CLIENTS.add(rabbitFanoutReceiverClient);
                log.info("为交换机 {} 注册了 FanoutClient", exchangeName);
            }
        } catch (Exception e) {
            log.error("Fanout RabbitMQ 设置过程中出错", e);
            throw e;
        }

        // 启动一个线程进行轮训是否channel断开
        ThreadUtil.execute(() -> {
            log.info("启动 Fanout 通道监控线程...");
            while (true) {
                ThreadUtil.sleep(5, TimeUnit.SECONDS);
                for (RabbitFanoutReceiverClient rabbitFanoutReceiverClient : FANOUT_CLIENTS) {
                    boolean open = rabbitFanoutReceiverClient.isOpen();
                    if (!open) {
                        log.info("Fanout 通道连接断开，尝试重新连接...");
                        try {
                            rabbitFanoutReceiverClient.registerFanout();
                            log.info("Fanout 交换机 {} 重新连接成功");
                        } catch (Exception e) {
                            log.warn("Fanout 重新绑定异常", e);
                        }
                    }
                }
            }
        });
    }
}
