package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisTextarea extends AmisComponents {

    public AmisTextarea(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 最小行数 */
    private Integer minRows;
    /** 最大行数 */
    private Integer maxRows;
    /** 是否去除首尾空白文本 */
    private Boolean trimContents;
    /** 是否只读 */
    private Boolean readOnly;
    /** 是否显示计数器 */
    private Boolean showCounter;
    /** 限制最大字数 */
    private Integer maxLength;
    /** 是否可清除 */
    private Boolean clearable;
    /** 清除后设置此配置项给定的值。 */
    private String resetValue;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Textarea {
        /** 最小行数 */
        int minRows() default 3;

        /** 最大行数 */
        int maxRows() default 20;

        /** 是否去除首尾空白文本 */
        boolean trimContents() default true;

        /** 是否只读 */
        boolean readOnly() default false;

        /** 是否显示计数器 */
        boolean showCounter() default true;

        /** 限制最大字数 */
        int maxLength() default 999;

        /** 是否可清除 */
        boolean clearable() default true;

        /** 清除后设置此配置项给定的值。 */
        String resetValue() default "";
    }

    public static AmisTextarea change(Boolean required,Textarea annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisTextarea temp = new AmisTextarea(required,type, name, label,disabled,width,remark);
        temp.setMinRows(annotation.minRows());
        temp.setMaxRows(annotation.maxRows());
        temp.setTrimContents(annotation.trimContents());
        temp.setReadOnly(annotation.readOnly());
        temp.setShowCounter(annotation.showCounter());
        temp.setMaxLength(annotation.maxLength());
        temp.setClearable(annotation.clearable());
        temp.setResetValue(annotation.resetValue());
        return temp;
    }
}