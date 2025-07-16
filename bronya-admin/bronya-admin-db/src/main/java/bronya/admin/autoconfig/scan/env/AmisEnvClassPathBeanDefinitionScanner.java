package bronya.admin.autoconfig.scan.env;

import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import bronya.admin.annotation.AmisEnv;

public class AmisEnvClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public AmisEnvClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @NotNull
    @Override
    public Set<BeanDefinitionHolder> doScan(@NotNull String... basePackages) {
        // 添加过滤条件，这里是只添加了@NRpcServer的注解才会被扫描到
//        addIncludeFilter(new AnnotationTypeFilter(Amis.class));
        addIncludeFilter(new AnnotationTypeFilter(AmisEnv.class));
        // 调用spring的扫描
        return super.doScan(basePackages);
    }
}