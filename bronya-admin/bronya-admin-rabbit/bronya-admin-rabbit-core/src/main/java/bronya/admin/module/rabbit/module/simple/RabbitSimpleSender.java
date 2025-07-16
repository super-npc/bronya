package bronya.admin.module.rabbit.module.simple;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import bronya.admin.module.rabbit.module.fanout.util.FanoutUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitSimpleSender<T> {

    private final RabbitTemplate rabbitTemplate;

    public void send(T t) {
        String exchangeName = FanoutUtil.getExchangeName(t.getClass());
        rabbitTemplate.convertAndSend(exchangeName, null, t);
    }
}
