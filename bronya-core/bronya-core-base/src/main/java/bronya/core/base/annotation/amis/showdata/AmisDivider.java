package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.divider.DividerDirection;
import bronya.core.base.annotation.amis.type.divider.DividerLineStyle;
import bronya.core.base.annotation.amis.type.divider.DividerTitlePosition;
import bronya.shared.module.common.type.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisDivider{
    private String type = "divider";

    /** 外层 Dom 的类名 */
    private String className;
    /** 分割线的样式，支持dashed和solid */
    private DividerLineStyle lineStyle;
    /** 分割线的方向，支持horizontal和vertical */
    private DividerDirection direction;
    /** 分割线的颜色 */
    private String color;
    /** 分割线的旋转角度 */
    private Integer rotate;
    /** 分割线的标题 */
    private Object title;
    /** 分割线的标题类名 */
    private String titleClassName;
    /** 分割线的标题位置，支持left、center和right */
    private DividerTitlePosition titlePosition;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Divider {
        /** 外层 Dom 的类名 */
        String className() default "";

        /** 分割线的样式，支持dashed和solid */
        DividerLineStyle lineStyle() default DividerLineStyle.dashed;

        /** 分割线的方向，支持horizontal和vertical */
        DividerDirection direction() default DividerDirection.horizontal;

        /** 分割线的颜色 */
        Color color() default Color.晒黑;

        /** 分割线的旋转角度 */
        int rotate() default 0;

        /** 分割线的标题 */
        String title() default "";

        /** 分割线的标题类名 */
        String titleClassName() default "";

        /** 分割线的标题位置，支持left、center和right */
        DividerTitlePosition titlePosition() default DividerTitlePosition.center;
    }

}