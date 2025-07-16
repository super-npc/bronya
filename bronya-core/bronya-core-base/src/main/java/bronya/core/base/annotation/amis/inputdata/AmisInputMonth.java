package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputMonth extends AmisComponents {

    public AmisInputMonth(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    // /** 默认值 */
    // private String value;
    /** 月份选择器值格式，更多格式类型请参考 moment */
    private String valueFormat;
    /** 月份选择器显示格式，即时间戳格式，更多格式类型请参考 moment */
    private String displayFormat;
    /** 占位文本 */
    private String placeholder;
    /** 是否可清除 */
    private Boolean clearable;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputMonth {
        // /** 默认值 */
        // String value() default "";
        /** 月份选择器值格式，更多格式类型请参考 moment */
        String valueFormat() default "X";

        /** 月份选择器显示格式，即时间戳格式，更多格式类型请参考 moment */
        String displayFormat() default "YYYY-MM";

        // /** 占位文本 */
        // String placeholder() default ;
        /** 是否可清除 */
        boolean clearable() default true;
    }

    public static AmisInputMonth change(Boolean required,InputMonth annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputMonth temp = new AmisInputMonth(required,type, name, label,disabled,width,remark);
        // temp.setValue(annotation.value());
        temp.setValueFormat(annotation.valueFormat());
        temp.setDisplayFormat(annotation.displayFormat());
        // temp.setPlaceholder(annotation.placeholder());
        temp.setClearable(annotation.clearable());
        return temp;
    }
}