package bronya.admin.base.autoconfig;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

import com.google.common.collect.Lists;

import bronya.admin.base.util.HttpMessageConverterUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
@Order
@EnableWebMvc
public class BronyaBaseMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new HandlerInterceptor() {
        // @Override
        // public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // if ("/favicon.ico".equals(request.getServletPath())) {
        // response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        // return false;
        // }
        // return true;
        // }
        // });
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new HttpMessageConverterUtil.StringToDateConverter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 返回json到前端,Long精度丢失问题
        converters.add(HttpMessageConverterUtil.toStringConverter());
        // 你可以根据需要添加更多的HttpMessageConverter,ResourceHttpMessageConverter会自动处理Resource类型的数据，所以在大多数情况下，它应该能处理你定义的application/vnd.apple.mpegurl类型的数据。
        converters.add(new ResourceHttpMessageConverter());
    }

    private final List<String> docs = Lists.newArrayList("/swagger-ui.html", "/swagger-ui/**", "/*.html", "/**/*.html",
        "/**/*.css", "/**/*.js", "/swagger-resources/**", "/v3/api-docs/**");

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        WebMvcConfigurer.super.addViewControllers(registry);
        registry.addRedirectViewController("/h3", "/console/login.do?language=zh_CN");
        registry.addRedirectViewController("/", "/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        // registry.addResourceHandler("/favicon.ico").addResourceLocations(STR."\{ResourceUtils.CLASSPATH_URL_PREFIX}/static/favicon.ico");

        registry.addResourceHandler("/amis/**")
            .addResourceLocations(STR."\{ResourceUtils.CLASSPATH_URL_PREFIX}/static/amis/").setCachePeriod(31556926);
        registry.addResourceHandler("/public/**")
            .addResourceLocations(STR."\{ResourceUtils.CLASSPATH_URL_PREFIX}/public/").setCachePeriod(31556926);
        registry.addResourceHandler("/**")
            .addResourceLocations(STR."\{ResourceUtils.CLASSPATH_URL_PREFIX}/static/").setCachePeriod(31556926);

        registry.addResourceHandler("/json/**")
            .addResourceLocations(STR."\{ResourceUtils.CLASSPATH_URL_PREFIX}/json/").setCacheControl(
                CacheControl.noStore().mustRevalidate());;

        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @PostConstruct
    public void init() {
        log.info("初始化-mvc");
    }
}
