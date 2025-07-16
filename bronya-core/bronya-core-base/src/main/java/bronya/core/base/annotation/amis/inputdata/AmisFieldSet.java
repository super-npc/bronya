package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.List;

import bronya.core.base.annotation.amis.type.button.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AmisFieldSet {

    private final String type = "fieldSet";
    /** 标题 */
    private final String title;
    private final List<?> body;
    /** 指定添加 css 类名 */
    private String className;
    /** 标题 CSS 类名 */
    private String headingClassName;
    /** 大小 */
    private Size size;
    /** 展示默认，同 Form 中的模式 */
    private String mode;
    /** 是否可折叠 */
    private Boolean collapsable;
    /** 默认是否折叠 */
    private Boolean collapsed;
    /** 收起的标题 */
    private String collapseTitle;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Button {
        /** 指定添加 css 类名 */
        String className() default "";

        /** 标题 CSS 类名 */
        String headingClassName() default "";

        /** 标题 */
        String title() default "";

        /** 大小 */
        Size size() default Size.xs;

        /** 展示默认，同 Form 中的模式 */
        String mode() default "";

        /** 是否可折叠 */
        boolean collapsable() default false;

        /** 默认是否折叠 */
        boolean collapsed() default false;

        /** 收起的标题 */
        String collapseTitle() default "收起";
    }
}