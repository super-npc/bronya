package bronya.core.base.annotation.amis.page;

public @interface Btns {
    boolean add() default true;

    boolean edit() default true;

    boolean delete() default true;

    boolean detail() default true;

    boolean exportCsv() default true;

    /**
     * 全部已读
     */
    boolean readAll() default false;
}