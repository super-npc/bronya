package bronya.core.base.annotation.amis.page;

public @interface CurdUrl {
    String list() default "";

    String detail() default "";

    String save() default "";

    String update() default "";

    String del() default "";
}