package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputNumber extends AmisComponents {

    public AmisInputNumber(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 最小值 */
    private Double min;
    /** 最大值 */
    private Double max;
    /** 步长 */
    private Integer step;
    /** 精度，即小数点后几位，支持 0 和正整数 */
    private Integer precision;
    /** 是否显示上下点击按钮 */
    private Boolean showSteps;
    /** 只读 */
    private Boolean readOnly;
    /** 前缀 */
    private String prefix;
    /** 后缀 */
    private String suffix;
    /** 单位选项 */
    private Object unitOptions;
    /** 千分分隔 */
    private Boolean kilobitSeparator;
    /** 键盘事件（方向上下） */
    private Boolean keyboard;
    /** 是否使用大数 */
    private Boolean big;
    /** 样式类型 */
    private Object displayMode;
    /** 边框模式，全边框，还是半边框，或者没边框 */
    private Object borderMode;
    /** 清空输入内容时，组件值将设置为 resetValue */
    private Object resetValue;
    /** 内容为空时从数据域中删除该表单项对应的值 */
    private Boolean clearValueOnEmpty;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputNumber {
        /** 最小值 */
        double min() default -99999.99999;

        /** 最大值 */
        double max() default 9999999999999999999999999.99999999;

        /** 步长 */
        int step() default 0;

        /** 精度，即小数点后几位，支持 0 和正整数 */
        int precision() default 0;

        /** 是否显示上下点击按钮 */
        boolean showSteps() default true;

        /** 只读 */
        boolean readOnly() default false;

        /** 前缀 */
        String prefix() default "";

        /** 后缀 */
        String suffix() default "";

        /** 单位选项 例如传指定后缀 "px","%","em" */
        String[] unitOptions() default {};

        /** 千分分隔 */
        boolean kilobitSeparator() default false;

        /** 键盘事件（方向上下） */
        boolean keyboard() default true;

        /** 是否使用大数 */
        boolean big() default false;

        /** 样式类型 "base" | "enhance" */
        String displayMode() default "base";

        /** 边框模式，全边框，还是半边框，或者没边框 "full" | "half" | "none" */
        String borderMode() default "full";

        // /** 清空输入内容时，组件值将设置为 resetValue */
        // Object resetValue() default ;
        /** 内容为空时从数据域中删除该表单项对应的值 */
        boolean clearValueOnEmpty() default false;
    }

    public static AmisInputNumber change(Boolean required,InputNumber annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputNumber temp = new AmisInputNumber(required,type, name, label,disabled,width,remark);
        temp.setMin(annotation.min());
        temp.setMax(annotation.max());
        temp.setStep(annotation.step());
        temp.setPrecision(annotation.precision());
        temp.setShowSteps(annotation.showSteps());
        temp.setReadOnly(annotation.readOnly());
        temp.setPrefix(annotation.prefix());
        temp.setSuffix(annotation.suffix());
        temp.setUnitOptions(annotation.unitOptions());
        temp.setKilobitSeparator(annotation.kilobitSeparator());
        temp.setKeyboard(annotation.keyboard());
        temp.setBig(annotation.big());
        temp.setDisplayMode(annotation.displayMode());
        temp.setBorderMode(annotation.borderMode());
        // temp.setResetValue(annotation.resetValue());
        temp.setClearValueOnEmpty(annotation.clearValueOnEmpty());
        return temp;
    }
}
