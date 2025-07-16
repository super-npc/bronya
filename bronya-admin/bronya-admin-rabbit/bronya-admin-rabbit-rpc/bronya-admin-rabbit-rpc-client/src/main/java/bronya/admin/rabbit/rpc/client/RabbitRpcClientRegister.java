package bronya.admin.rabbit.rpc.client;

import org.dromara.hutool.extra.aop.ProxyUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import bronya.admin.rabbit.rpc.client.handler.RabbitRpcHandler;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.rabbit.MyRabbitApi;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitRpcClientRegister implements BeanFactoryPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    private void registerRabbitRpc() {
        for (Class<?> beanClass : AdminBaseConstant.CLASSES_RABBIT_RPC) {
            MyRabbitApi annotation = beanClass.getAnnotation(MyRabbitApi.class);
            String queue = annotation.queue();

            // 创建代理对象
            Object proxy = ProxyUtil.newProxyInstance(beanClass.getClassLoader(), new RabbitRpcHandler(queue), beanClass);

            // 注册到 Spring 容器
            beanFactory.registerSingleton(beanClass.getSimpleName(), proxy);
        }
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.registerRabbitRpc();
    }
}


