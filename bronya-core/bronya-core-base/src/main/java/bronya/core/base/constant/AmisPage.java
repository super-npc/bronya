package bronya.core.base.constant;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.CurdUrl;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.OrderByType;
import bronya.core.base.annotation.amis.type.button.Size;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AmisPage {
    /**
     * 定时刷新
     */
    int interval() default -1;

    Menu menu();

    CurdUrl url() default @CurdUrl();

    Btns btns() default @Btns();

    Operation operation() default @Operation();

    Operation headerToolbar() default @Operation();

    OrderBy[] orderBys() default {};

    /**
     * 页面初始化允许有一个默认排序
     */
    OrderByDefaultPage orderByDefaultPage() default @OrderByDefaultPage();

    AutoGenerateFilter autoGenerateFilter() default @AutoGenerateFilter();

    /**
     * 动态表格,会与autoGenerateFilter冲突,优先使用filter
     */
    DynamicTable dynamicTable() default @DynamicTable();

    /**
     * 虚拟数据
     */
    VirtualData virtual() default @VirtualData();

    FieldSet[] fieldSets() default {};

    @interface FieldSet {
        /** 标题 */
        String title();

        /** 包裹字段 */
        String[] fields();

        /** 指定添加 css 类名 */
        String className() default "";

        /** 标题 CSS 类名 */
        String headingClassName() default "";

        /** 大小 */
        Size size() default Size.base;

        /** 展示默认，同 Form 中的模式 */
        String mode() default "";

        /** 是否可折叠 */
        boolean collapsable() default true;

        /** 默认是否折叠 */
        boolean collapsed() default false;

        /** 收起的标题 */
        String collapseTitle() default "收起";

        Position position() default Position.top;

        enum Position {
            top, bottom
        }
    }

    @interface VirtualData {
        Class curd() default Void.class;
    }

    @interface DynamicTable {
        String[] cols() default {};
    }

    @interface AutoGenerateFilter {
        /**
         * 过滤条件单行列数
         */
        int columnsNum() default 5;

        /**
         * 是否显示设置查询字段
         */
        boolean showBtnToolbar() default true;

        /**
         * 是否初始收起
         */
        boolean defaultCollapsed() default true;
    }
    // {
    // "columnsNum": 4,
    // "showBtnToolbar": false,
    // "defaultCollapsed": false
    // }

    @interface OrderByDefaultPage {
        String col() default "";

        OrderByType type() default OrderByType.DESC;
    }

    @interface OrderBy {
        OrderByType type() default OrderByType.DESC;

        String[] cols();
    }

}
