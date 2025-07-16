package bronya.admin.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ApiThreadPoolConfig {
    /**
     * 后台前端线程池
     */
    @Bean
    @Lazy
    public ThreadPoolTaskExecutor adminFrontTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 核心线程数
        executor.setMaxPoolSize(50); // 最大线程数
        executor.setQueueCapacity(100); // 队列容量
        executor.setThreadNamePrefix("admin-front-"); // 线程名称前缀
        executor.initialize(); // 初始化线程池
        return executor;
    }
}