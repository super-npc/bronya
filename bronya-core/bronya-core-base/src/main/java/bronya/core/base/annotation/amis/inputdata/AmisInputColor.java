package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.inputColor.InputColorFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputColor extends AmisComponents {

    public AmisInputColor(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 请选择 hex、hls、rgb或者rgba。 */
    private InputColorFormat format;
    // /** 选择器底部的默认颜色，数组内为空则不显示默认颜色 */
    // private String presetColors;
    /** 为false时只能选择颜色，使用 presetColors 设定颜色选择范围 */
    private Boolean allowCustomColor;
    /** 是否显示清除按钮 */
    private Boolean clearable;
    // /** 清除后，表单项值调整成该值 */
    // private String resetValue;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputColor {
        /** 请选择 hex、hls、rgb或者rgba。 */
        InputColorFormat format() default InputColorFormat.rgb;

        // /** 选择器底部的默认颜色，数组内为空则不显示默认颜色 */
        // Color presetColors() default Color.纯黑;
        /** 为false时只能选择颜色，使用 presetColors 设定颜色选择范围 */
        boolean allowCustomColor() default true;

        /** 是否显示清除按钮 */
        boolean clearable() default true;
        // /** 清除后，表单项值调整成该值 */
        // Color resetValue() default Color.纯黑;
    }

    public static AmisInputColor change(Boolean required,InputColor annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputColor temp = new AmisInputColor(required,type, name, label,disabled,width,remark);
        temp.setFormat(annotation.format());
        // temp.setPresetColors(annotation.presetColors().getHex());
        temp.setAllowCustomColor(annotation.allowCustomColor());
        temp.setClearable(annotation.clearable());
        // temp.setResetValue(annotation.resetValue().getHex());
        return temp;
    }
}