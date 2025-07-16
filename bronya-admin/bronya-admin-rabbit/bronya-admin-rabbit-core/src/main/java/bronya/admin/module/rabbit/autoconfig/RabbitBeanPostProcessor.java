package bronya.admin.module.rabbit.autoconfig;


import java.lang.reflect.Type;
import java.util.List;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Assert;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import bronya.admin.base.cfg.SpringYaml;
import bronya.admin.module.rabbit.commons.MqConstant;
import bronya.admin.module.rabbit.dto.RabbitFanoutExchangeEntity;
import bronya.admin.module.rabbit.module.delayed.IDelayedMq;
import bronya.admin.module.rabbit.module.fanout.IFanoutMq;
import bronya.admin.module.rabbit.module.work.IWorkMq;
import bronya.admin.module.rabbit.module.work.annotation.RabbitWorkEntity;
import bronya.admin.module.rabbit.sdk.RabbitSdk;
import bronya.shared.module.util.MyRefUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局类加载器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private final SpringYaml springYaml;
    /**
     * ps: 无法在这里操作数据库,因为持久化还未注入容器无法拿到服务
     */
    @SneakyThrows
    @NotNull
    @Override
    public Object postProcessAfterInitialization(@NotNull Object bean, @NotNull String beanName) {
        if (bean instanceof IDelayedMq<?> iDelayedMq) {
            List<Type> allGenericClassByExtendClass = MyRefUtil.getAllGenericClassByExtendClass(bean.getClass());
            Type genericsType = allGenericClassByExtendClass.getFirst();
            Assert.notNull(genericsType, "订阅实现类未指定泛型:{}", iDelayedMq.getClass().getName());
            Class<?> genericClass = Class.forName(genericsType.getTypeName());
            MqConstant.RABBIT_DELAYED.add(genericClass);
        } else if (bean instanceof IFanoutMq<?> fanoutMq) {
            List<Type> allGenericClassByExtendClass = MyRefUtil.getAllGenericClassByExtendClass(fanoutMq.getClass());
            Type genericsType = CollUtil.getFirst(allGenericClassByExtendClass);
            Assert.notNull(genericsType, "订阅实现类未指定泛型:{}", fanoutMq.getClass().getName());
            // 发送实体/多个接收实现类
            Class<?> fanoutExchangeEntityClass = Class.forName(genericsType.getTypeName());
            RabbitFanoutExchangeEntity annotation =
                AnnotationUtil.getAnnotation(fanoutExchangeEntityClass, RabbitFanoutExchangeEntity.class);
            Assert.notNull(annotation, "发布订阅模式的传输实体作为Exchange未加上注解RabbitFanoutExchangeEntity:{}",
                fanoutExchangeEntityClass.getName());
            MqConstant.RABBIT_FANOUT.put(fanoutExchangeEntityClass, fanoutMq);
        } else if (bean instanceof IWorkMq<?> workMq) {
            List<Type> allGenericClassByExtendClass = MyRefUtil.getAllGenericClassByExtendClass(workMq.getClass());
            Type genericsType = CollUtil.getFirst(allGenericClassByExtendClass);
            Assert.notNull(genericsType, "订阅实现类未指定泛型:{}", workMq.getClass().getName());
            // 发送实体/多个接收实现类
            Class<?> workExchangeEntityClass = Class.forName(genericsType.getTypeName());
            RabbitWorkEntity annotation = AnnotationUtil.getAnnotation(workExchangeEntityClass, RabbitWorkEntity.class);
            Assert.notNull(annotation, "工作模式模式的传输实体作为Queue未加上注解RabbitWorkEntity:{}", workExchangeEntityClass.getName());
            MqConstant.RABBIT_WORK.put(workExchangeEntityClass, workMq);
        }
        return bean;
    }

    @NotNull
    @Override
    public Object postProcessBeforeInitialization(@NotNull Object bean, @NotNull String beanName) {
        return bean;
    }

    @SneakyThrows
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
        SpringYaml.Rabbitmq rabbitmq = springYaml.getRabbitmq();
        String virtualHost = rabbitmq.getVirtualHost();
        RabbitSdk rabbitSdk = new RabbitSdk(rabbitmq.getHost(), 15672, rabbitmq.getUsername(), rabbitmq.getPassword());
        rabbitSdk.addVirtualHost(virtualHost);
        log.info("创建rabbit.VirtualHost:{}", virtualHost);
    }
}
