package bronya.core.base.annotation.amis.gencode.table;

import java.lang.annotation.*;

@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MiddleJoinCondition {
    String selfField();

    String joinField();
}
