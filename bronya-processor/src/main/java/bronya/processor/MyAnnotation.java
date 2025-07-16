package bronya.processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE) // 注解仅在源代码级别保留，编译时不会保留
@Target(ElementType.TYPE) // 注解只能用于类或接口
public @interface MyAnnotation {
    String value(); // 注解的唯一元素，返回一个字符串
}