package bronya.core.base.annotation.amis.page;

public @interface Menu {
    /**
     * 是否展示菜单
     */
    boolean show() default true;

    String module();

    // "属于哪个app下")
    String group();

    // "一级菜单")
    String menu();

    // http://www.fontawesome.com.cn/
    String icon() default "";
}