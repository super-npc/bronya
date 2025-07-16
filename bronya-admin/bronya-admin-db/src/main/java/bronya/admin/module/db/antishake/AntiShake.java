package bronya.admin.module.db.antishake;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 该注解只能用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保留，这样才能在AOP中被检测到
public @interface AntiShake {
    long value() default 1000L; // 默认防抖时间为1秒
}