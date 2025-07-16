package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisTag extends AmisComponents {

    public AmisTag(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 展现模式 */
    private Tag.DisplayMode displayMode;
    /** 颜色主题，提供默认主题，并支持自定义颜色值 */
    private String color;
    /** status 模式下的前置图标 */
    private String icon;
    /** 自定义 CSS 样式类名 */
    private String className;
    /** 自定义样式（行内样式），优先级最高 */
    private String style;
    /** 是否展示关闭按钮 */
    private Boolean closable;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Tag {
        /** 展现模式 */
        DisplayMode displayMode() default DisplayMode.normal;

        /** 颜色主题，提供默认主题，并支持自定义颜色值 */
        String color() default "normal";

        /** 标签内容 */
        String label() default "-";

        /** status 模式下的前置图标 */
        String icon() default "";

        /** 自定义 CSS 样式类名 */
        String className() default "";

        /** 自定义样式（行内样式），优先级最高 */
        String style() default "";

        /** 是否展示关闭按钮 */
        boolean closable() default false;

        enum DisplayMode {
            normal, rounded, status
        }
    }

    public static AmisTag change(Boolean required,Tag annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisTag temp = new AmisTag(required,type, name, label,disabled,width,remark);
        temp.setDisplayMode(annotation.displayMode());
        temp.setColor(annotation.color());
        temp.setLabel(annotation.label());
        temp.setIcon(annotation.icon());
        temp.setClassName(annotation.className());
        temp.setStyle(annotation.style());
        temp.setClosable(annotation.closable());
        return temp;
    }
}