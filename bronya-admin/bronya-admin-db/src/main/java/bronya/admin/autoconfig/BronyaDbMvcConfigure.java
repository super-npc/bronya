package bronya.admin.autoconfig;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import bronya.admin.resolver.AmisBeanResolver;
import bronya.admin.resolver.AmisIdsResolver;
import bronya.admin.resolver.SiteResolver;
import bronya.admin.resolver.UserResolver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Order(-2000)
@EnableWebMvc
public class BronyaDbMvcConfigure implements WebMvcConfigurer {
    private final AmisBeanResolver amisBeanResolver;
    private final SiteResolver siteResolver;
    private final AmisIdsResolver idsResolver;
    private final UserResolver userResolver;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(amisBeanResolver);
        resolvers.add(siteResolver);
        resolvers.add(idsResolver);
        resolvers.add(userResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @PostConstruct
    public void init() {
        log.info("初始化-mvc");
    }
}
