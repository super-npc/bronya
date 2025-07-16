package bronya.admin.redis.cfg;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单机模式配置
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("mypassword")
            .setDatabase(0); // 如果需要指定数据库
        return Redisson.create(config);
    }
}