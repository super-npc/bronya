package bronya.admin.base.autoconfig;

import bronya.admin.base.util.HttpMessageConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@RequiredArgsConstructor
public class BronyaRegisterBean {

    @Bean
    @Lazy
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    @Lazy
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return HttpMessageConverterUtil.toStringConverter();
    }
}
