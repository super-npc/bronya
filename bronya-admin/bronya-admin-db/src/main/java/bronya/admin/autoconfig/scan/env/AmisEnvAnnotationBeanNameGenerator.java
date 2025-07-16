package bronya.admin.autoconfig.scan.env;

import bronya.shared.module.common.constant.AdminBaseConstant;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import lombok.SneakyThrows;

/**
 * 收集所有加上注解的类
 */
public class AmisEnvAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    @SneakyThrows
    @Override
    public @NotNull String generateBeanName(BeanDefinition definition, @NotNull BeanDefinitionRegistry registry) {
        // 从自定义注解中拿name
        String beanClassName = definition.getBeanClassName();
        Class<?> aClass = Class.forName(beanClassName);
        // this.getNameByServiceAmis(aClass);
        AdminBaseConstant.CLASSES_AMIS_ENV.add(aClass);
        // 走父类的方法
        return super.generateBeanName(definition, registry);
    }

}