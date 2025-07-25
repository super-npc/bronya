package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputDate extends AmisComponents {

    public AmisInputDate(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 默认值 */
    private String value;
    /** 日期选择器值格式，更多格式类型请参考 文档 */
    private String valueFormat;
    /** 日期选择器显示格式，即时间戳格式，更多格式类型请参考 文档 */
    private String displayFormat;
    /** 点选日期后，是否马上关闭选择框 */
    private Boolean closeOnSelect;
    /** 占位文本 */
    private String placeholder;
    /** 日期快捷键，字符串格式为预设值，对象格式支持写表达式 */
    private Object shortcuts;
    /** 限制最小日期 */
    private String minDate;
    /** 限制最大日期 */
    private String maxDate;
    /** 保存 utc 值 */
    private Boolean utc;
    /** 是否可清除 */
    private Boolean clearable;
    /** 是否内联模式 */
    private Boolean embed;
    /** 用字符函数来控制哪些天不可以被点选 */
    private String disabledDate;

    public static AmisInputDate change(Boolean required,InputDate annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputDate temp = new AmisInputDate(required,type, name, label,disabled,width,remark);
        temp.setValue(annotation.value());
        temp.setValueFormat(annotation.valueFormat());
        temp.setDisplayFormat(annotation.displayFormat());
        temp.setCloseOnSelect(annotation.closeOnSelect());
        temp.setPlaceholder(annotation.placeholder());
        // temp.setShortcuts(annotation.shortcuts());
        temp.setMinDate(annotation.minDate());
        temp.setMaxDate(annotation.maxDate());
        temp.setUtc(annotation.utc());
        temp.setClearable(annotation.clearable());
        temp.setEmbed(annotation.embed());
        temp.setDisabledDate(annotation.disabledDate());
        return temp;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputDate {
        /** 默认值 */
        String value() default "";

        /** 日期选择器值格式，更多格式类型请参考 文档 */
        String valueFormat() default "";

        /** 日期选择器显示格式，即时间戳格式，更多格式类型请参考 文档 */
        String displayFormat() default "";

        /** 点选日期后，是否马上关闭选择框 */
        boolean closeOnSelect() default false;

        /** 占位文本 */
        String placeholder() default "";

        // /** 日期快捷键，字符串格式为预设值，对象格式支持写表达式 */
        // Object shortcuts() default "";
        /** 限制最小日期 */
        String minDate() default "";

        /** 限制最大日期 */
        String maxDate() default "";

        /** 保存 utc 值 */
        boolean utc() default false;

        /** 是否可清除 */
        boolean clearable() default false;

        /** 是否内联模式 */
        boolean embed() default false;

        /** 用字符函数来控制哪些天不可以被点选 */
        String disabledDate() default "";
    }
}