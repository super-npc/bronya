//package bronya.admin.base.filter;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.CommonsRequestLoggingFilter;
//
//@Configuration
//public class RequestLoggingFilter {
//    @Bean
//    public CommonsRequestLoggingFilter logFilter() {
//        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
//        filter.setIncludeQueryString(true);
//        filter.setIncludePayload(true);
//        filter.setIncludeHeaders(true);
//        filter.setIncludeClientInfo(true);
//        filter.setAfterMessagePrefix("请求-");
//        return filter;
//    }
//}
