package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisSwitch extends AmisComponents {
    public AmisSwitch(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    private Boolean value;
    private Boolean disabled;
    /** 选项说明 */
    private String option;
    /** 开启时开关显示的内容 */
    private String onText;
    /** 关闭时开关显示的内容 */
    private String offText;
    /** 标识真值 */
    private String trueValue;
    /** 标识假值 */
    private String falseValue;
    /** 开关大小 */
    private String size;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Switch {
        /** 选项说明 */
        String option() default "";

        /** 开启时开关显示的内容 */
        String onText() default "";

        /** 关闭时开关显示的内容 */
        String offText() default "";

        /** 标识真值 */
        String trueValue() default "";

        /** 标识假值 */
        String falseValue() default "";

        /** 开关大小 "sm" | "md" */
        String size() default "md";
    }

    public static AmisSwitch change(Boolean required,Switch annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisSwitch temp = new AmisSwitch(required,type, name, label,disabled,width,remark);
        temp.setOption(annotation.option());
        temp.setOnText(annotation.onText());
        temp.setOffText(annotation.offText());
        temp.setTrueValue(annotation.trueValue());
        temp.setFalseValue(annotation.falseValue());
        temp.setSize(annotation.size());
        return temp;
    }

    @Override
    public AmisComponents headSearch() {
        return super.headSearch();
    }
}