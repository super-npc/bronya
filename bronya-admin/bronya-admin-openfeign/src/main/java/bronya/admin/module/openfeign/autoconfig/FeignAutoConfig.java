package bronya.admin.module.openfeign.autoconfig;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class FeignAutoConfig {

    // @LoadBalanced 与 Ribbon 的集成
//    @LoadBalanced
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClient() {
//        return WebClient.builder();
//    }

    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL; // 日志级别为BASIC
    }


    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default(100, // 初始等待时间（毫秒）
                1000, // 最大等待时间（毫秒）
                3);   // 最大重试次数
    }
}
