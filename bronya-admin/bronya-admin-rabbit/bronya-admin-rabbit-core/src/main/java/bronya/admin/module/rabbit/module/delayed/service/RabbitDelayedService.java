package bronya.admin.module.rabbit.module.delayed.service;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import bronya.admin.module.rabbit.commons.MqConstant;
import bronya.admin.module.rabbit.module.delayed.repository.RabbitDelayedRepository;
import lombok.RequiredArgsConstructor;

@Service
@Order
@RequiredArgsConstructor
public class RabbitDelayedService implements ApplicationRunner {
    private final RabbitDelayedRepository rabbitDelayedService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (Class<?> rabbitDelayedClass : MqConstant.RABBIT_DELAYED) {
            rabbitDelayedService.saveEntityClass(rabbitDelayedClass.getName());
        }
    }
}
