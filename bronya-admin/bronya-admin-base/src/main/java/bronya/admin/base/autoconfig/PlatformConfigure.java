package bronya.admin.base.autoconfig;

import bronya.shared.module.platform.IPlatform;
import org.dromara.hutool.core.spi.JdkServiceLoaderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration
public class PlatformConfigure {
    @Bean
    @Lazy
    public IPlatform platformService() {
        return JdkServiceLoaderUtil.loadFirstAvailable(IPlatform.class);
    }
}
