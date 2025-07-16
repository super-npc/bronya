package bronya.core.base.annotation.amis.gencode.table;

import java.lang.annotation.*;

import org.dromara.mpe.bind.metadata.annotation.JoinOrderBy;

/**
 * 绑定字段 （one to one）（one to many）
 *
 * @author don
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//@TableField(exist = false)
@Inherited
@Documented
public @interface BindMany2One {

    /**
     * 被关联的Entity
     */
    Class<?> entity();

    /***
     * 被关联的Entity的具体字段
     */
    String valueField();

    /**
     * 被关联的Entity的展示字段
     */
    String labelField();

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

    /**
     * 在头部显示外键关联按钮
     */
    boolean showCrudBtn() default true;
}
