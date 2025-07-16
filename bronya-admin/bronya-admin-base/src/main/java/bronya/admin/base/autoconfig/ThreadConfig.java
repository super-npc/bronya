package bronya.admin.base.autoconfig;

import java.util.concurrent.Executors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

/**
 * 配置是用于稍后测试，spring.virtual-thread=true是使用虚拟线程，false时还是使用默认的普通线程
 */
@Configuration
@ConditionalOnProperty(prefix = "spring", name = "virtual-thread", havingValue = "true")
public class ThreadConfig {

    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newVirtualThreadPerTaskExecutor());
    }

    // @Bean
    // public TomcatProtocolHandlerCustomizer<?> protocolHandlerCustomizer() {
    // return protocolHandler -> {
    // protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    // };
    // }
}