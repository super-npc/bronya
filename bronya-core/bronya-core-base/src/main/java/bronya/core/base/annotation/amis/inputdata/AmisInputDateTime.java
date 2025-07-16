package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputDateTime extends AmisComponents {

    public AmisInputDateTime(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 默认值 */
    private String value;
    /** 日期时间选择器值格式，更多格式类型请参考 文档 */
    private String valueFormat;
    /** 日期时间选择器显示格式，即时间戳格式，更多格式类型请参考 文档 */
    private String displayFormat;
    /** 占位文本 */
    private String placeholder;
    /** 日期时间快捷键 */
    private Object shortcuts;
    /** 限制最小日期时间 */
    private String minDate;
    /** 限制最大日期时间 */
    private String maxDate;
    /** 保存 utc 值 */
    private Boolean utc;
    /** 是否可清除 */
    private Boolean clearable;
    /** 是否内联 */
    private Boolean embed;
    /** 请参考 input-time 里的说明 */
    private Object timeConstraints;
    /** 如果配置为 true，会自动默认为 23:59:59 秒 */
    private Boolean isEndDate;
    /** 用字符函数来控制哪些天不可以被点选 */
    private String disabledDate;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputDateTime {
        /** 默认值 */
        String value() default "";

        /** 日期时间选择器值格式，更多格式类型请参考 文档 */
        String valueFormat() default "";

        /** 日期时间选择器显示格式，即时间戳格式，更多格式类型请参考 文档 */
        String displayFormat() default "";

        /** 占位文本 */
        String placeholder() default "";

        // /** 日期时间快捷键 */
        // Object shortcuts() default "";
        /** 限制最小日期时间 */
        String minDate() default "";

        /** 限制最大日期时间 */
        String maxDate() default "";

        /** 保存 utc 值 */
        boolean utc() default false;

        /** 是否可清除 */
        boolean clearable() default false;

        /** 是否内联 */
        boolean embed() default false;

        // /** 请参考 input-time 里的说明 */
        // Object timeConstraints() default ;
        /** 如果配置为 true，会自动默认为 23:59:59 秒 */
        boolean isEndDate() default false;

        /** 用字符函数来控制哪些天不可以被点选 */
        String disabledDate() default "";
    }

    public static AmisInputDateTime change(Boolean required,InputDateTime annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputDateTime temp = new AmisInputDateTime(required,type, name, label,disabled,width,remark);
        temp.setValue(annotation.value());
        temp.setValueFormat(annotation.valueFormat());
        temp.setDisplayFormat(annotation.displayFormat());
        temp.setPlaceholder(annotation.placeholder());
        // temp.setShortcuts(annotation.shortcuts());
        temp.setMinDate(annotation.minDate());
        temp.setMaxDate(annotation.maxDate());
        temp.setUtc(annotation.utc());
        temp.setClearable(annotation.clearable());
        temp.setEmbed(annotation.embed());
        // temp.setTimeConstraints(annotation.timeConstraints());
        temp.setIsEndDate(annotation.isEndDate());
        temp.setDisabledDate(annotation.disabledDate());
        return temp;
    }
}