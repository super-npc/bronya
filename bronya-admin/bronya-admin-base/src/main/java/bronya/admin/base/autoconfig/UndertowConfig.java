//package bronya.admin.base.autoconfig;
//
//import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class UndertowConfig {
//
//    @Bean
//    public UndertowServletWebServerFactory servletWebServerFactory() {
//        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
//
//        // 自定义线程池
//        factory.addBuilderCustomizers(builder -> {
//            builder.setWorkerThreads(300);  // 覆盖配置文件参数
//            builder.setIoThreads(32);       // 提升IO吞吐量
//        });
//
////        // 添加HTTP/2支持
////        factory.addHttp2Customizers(http2Builder ->
////            http2Builder.setMaxConcurrentStreams(1000));
//
//        return factory;
//    }
//}
