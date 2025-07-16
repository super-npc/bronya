package bronya.core.base.annotation.amis.page;

import java.lang.annotation.*;

import bronya.core.base.dto.DataProxy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Amis {
    /** 审计 **/
    boolean audit() default false;

    Class<? extends DataProxy> dataProxy() default DataProxy.class;

    /** 拓展字段 */
    Class<?> extBean() default void.class;
}