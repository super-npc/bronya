package bronya.core.base.annotation.amis.gencode.table;

import java.lang.annotation.*;

import org.dromara.mpe.bind.metadata.annotation.JoinOrderBy;

@Inherited
@Documented
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@TableField(exist = false)
public @interface BindOne2Many {

    /**
     * 被关联的Entity，不再需要显示的指明，默认取字段上的声明类型
     */
    Class<?> entity() default Void.class;

    /**
     * 被关联的Entity所查询的字段，空表示查询全部
     */
    String[] selectFields() default {};

    /**
     * 关联Entity所需要的条件
     */
    JoinCondition condition();

    /**
     * 被关联的Entity所需要的额外条件
     * 通常指被关联的Entity自身的特殊条件，例如：enable=1 and is_deleted=0
     */
    String customCondition() default "";

    /**
     * 被关联的Entity的结果集，排序条件
     */
    JoinOrderBy[] orderBy() default {};

    /**
     * 最后的sql拼接，通常是limit ?
     */
    String last() default "";
}
