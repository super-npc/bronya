package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisColor extends AmisComponents {

    public AmisColor(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    // /** 如果在 Table、Card 和 List 中，为"color"；在 Form 中用作静态展示，为"static-color" */
    // private String type;
    /** 外层 CSS 类名 */
    private String className;
    // /** 显示的颜色值 */
    // private String value;
    // /** 在其他组件中，时，用作变量映射 */
    // private String name;
    // /** 默认颜色值 */
    // private String defaultColor;
    /** 是否显示右边的颜色值 */
    private Boolean showValue;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Color {
        // /** 如果在 Table、Card 和 List 中，为"color"；在 Form 中用作静态展示，为"static-color" */
        // String type() default "";
        /** 外层 CSS 类名 */
        String className() default "";

        // /** 显示的颜色值 */
        // String value() default "";
        // /** 在其他组件中，时，用作变量映射 */
        // String name() default "";
        // /** 默认颜色值 */
        // String defaultColor() default ;
        /** 是否显示右边的颜色值 */
        boolean showValue() default true;
    }

    public static AmisColor change(Boolean required,Color annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisColor temp = new AmisColor(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        temp.setClassName(annotation.className());
        // temp.setValue(annotation.value());
        // temp.setName(annotation.name());
        // temp.setDefaultColor(annotation.defaultColor());
        temp.setShowValue(annotation.showValue());
        return temp;
    }
}