package bronya.admin.module.rabbit.module.work.annotation;

import java.lang.annotation.*;

/**
 * 标注这个是rabbit交换机,为了代码清晰度加上,没有实际作用
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RabbitWorkEntity {
    String desc();
}
