package bronya.admin.module.rabbit.module.delayed.annotation;

import java.lang.annotation.*;

import org.dromara.hutool.core.date.DateUnit;

import bronya.admin.module.rabbit.module.delayed.IDelayedMq;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface MqDelayed {

    Type type() default Type.onTime;

    DateUnit timeUnit();

    int time();

    int timeMax() default 0;

    Class<? extends IDelayedMq> execClass();

    enum Type {
        onTime, random;
    }
}
