package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputMonthRange extends AmisComponents {

    public AmisInputMonthRange(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 日期选择器值格式 */
    private String format;
    /** 日期选择器显示格式 */
    private String inputFormat;
    /** 占位文本 */
    private String placeholder;
    /** 限制最小日期，用法同 限制范围 */
    private String minDate;
    /** 限制最大日期，用法同 限制范围 */
    private String maxDate;
    /** 限制最小跨度，如： 2days */
    private String minDuration;
    /** 限制最大跨度，如：1year */
    private String maxDuration;
    /** 保存 UTC 值 */
    private Boolean utc;
    /** 是否可清除 */
    private Boolean clearable;
    /** 是否内联模式 */
    private Boolean embed;
    /** 是否启用游标动画 */
    private Boolean animation;
    // /** 是否存成两个字段 */
    // private String extraName;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputMonthRange {
        /** 日期选择器值格式 */
        String format() default "X";

        /** 日期选择器显示格式 */
        String inputFormat() default "YYYY-DD";

        /** 占位文本 */
        String placeholder() default "请选择月份范围";

        /** 限制最小日期，用法同 限制范围 */
        String minDate() default "";

        /** 限制最大日期，用法同 限制范围 */
        String maxDate() default "";

        /** 限制最小跨度，如： 2days */
        String minDuration() default "";

        /** 限制最大跨度，如：1year */
        String maxDuration() default "";

        /** 保存 UTC 值 */
        boolean utc() default false;

        /** 是否可清除 */
        boolean clearable() default true;

        /** 是否内联模式 */
        boolean embed() default false;

        /** 是否启用游标动画 */
        boolean animation() default true;
        // /** 是否存成两个字段 */
        // String extraName() default "";
    }

    public static AmisInputMonthRange change(Boolean required,InputMonthRange annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputMonthRange temp = new AmisInputMonthRange(required,type, name, label,disabled,width,remark);
        temp.setFormat(annotation.format());
        temp.setInputFormat(annotation.inputFormat());
        temp.setPlaceholder(annotation.placeholder());
        temp.setMinDate(annotation.minDate());
        temp.setMaxDate(annotation.maxDate());
        temp.setMinDuration(annotation.minDuration());
        temp.setMaxDuration(annotation.maxDuration());
        temp.setUtc(annotation.utc());
        temp.setClearable(annotation.clearable());
        temp.setEmbed(annotation.embed());
        temp.setAnimation(annotation.animation());
        // temp.setExtraName(annotation.extraName());
        return temp;
    }
}