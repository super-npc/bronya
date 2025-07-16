package bronya.admin.annotation;

import java.lang.annotation.*;

import bronya.admin.autoconfig.AmisScannerRegistrar;
import org.springframework.context.annotation.Import;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
// spring中的注解,加载对应的类
@Import(AmisScannerRegistrar.class) // 这个是我们的关键，实际上也是由这个类来扫描的
@Documented
public @interface EnableMySchedule {
}