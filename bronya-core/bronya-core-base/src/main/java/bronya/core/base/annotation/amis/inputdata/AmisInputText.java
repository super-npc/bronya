package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.inputTest.InputTextBorderMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputText extends AmisComponents {
    public AmisInputText(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    private String value;
    private Boolean disabled;

    /** 选项组 */
    private Object options;
    /** 动态选项组 */
    private Object source;
    /** 自动补全 */
    private Object autoComplete;
    /** 是否多选 */
    private Boolean multiple;
    /** 拼接符 */
    private String delimiter;
    /** 选项标签字段 */
    private String labelField;
    /** 选项值字段 */
    private String valueField;
    /** 拼接值 */
    private Boolean joinValues;
    /** 提取值 */
    private Boolean extractValue;
    /** 是否去除首尾空白文本。 */
    private Boolean trimContents;
    /** 文本内容为空时去掉这个值 */
    private Boolean clearValueOnEmpty;
    /** 是否可以创建，默认为可以，除非设置为 false 即只能选择选项中的值 */
    private Boolean creatable;
    /** 是否可清除 */
    private Boolean clearable;
    /** 清除后设置此配置项给定的值。 */
    private String resetValue;
    /** 前缀 */
    private String prefix;
    /** 后缀 */
    private String suffix;
    /** 是否显示计数器 */
    private Boolean showCounter;
    /** 限制最小字数 */
    private Integer minLength;
    /** 限制最大字数 */
    private Integer maxLength;
    /** 自动转换值，可选 transform: { lowerCase: true, upperCase: true } */
    private Object transform;
    /** 输入框边框模式，全边框，还是半边框，或者没边框。 */
    private InputTextBorderMode borderMode;
    /** control 节点的 CSS 类名 */
    private String inputControlClassName;
    /** 原生 input 标签的 CSS 类名 */
    private String nativeInputClassName;
    /** 原生 input 标签的 autoComplete 属性，比如配置集成 new-password */
    private String nativeAutoComplete;

    public static AmisInputText change(Boolean required,InputText annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputText temp = new AmisInputText(required,type, name, label,disabled,width,remark);
        // temp.setOptions(annotation.options());
        // temp.setSource(annotation.source());
        // temp.setAutoComplete(annotation.autoComplete());
        temp.setMultiple(annotation.multiple());
        temp.setDelimiter(annotation.delimiter());
        temp.setLabelField(annotation.labelField());
        temp.setValueField(annotation.valueField());
        temp.setJoinValues(annotation.joinValues());
        temp.setExtractValue(annotation.extractValue());
        temp.setTrimContents(annotation.trimContents());
        temp.setClearValueOnEmpty(annotation.clearValueOnEmpty());
        temp.setCreatable(annotation.creatable());
        temp.setClearable(annotation.clearable());
        temp.setResetValue(annotation.resetValue());
        temp.setPrefix(annotation.prefix());
        temp.setSuffix(annotation.suffix());
        temp.setShowCounter(annotation.showCounter());
        temp.setMinLength(annotation.minLength());
        temp.setMaxLength(annotation.maxLength());
        // temp.setTransform(annotation.transform());
        // temp.setBorderMode(annotation.borderMode());
        temp.setInputControlClassName(annotation.inputControlClassName());
        temp.setNativeInputClassName(annotation.nativeInputClassName());
        temp.setNativeAutoComplete(annotation.nativeAutoComplete());
        return temp;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputText {
        // /** 选项组 */
        // Object options() default "";
        // /** 动态选项组 */
        // Object source() default "";
        // /** 自动补全 */
        // Object autoComplete() default "";
        /** 是否多选 */
        boolean multiple() default false;

        /** 拼接符 */
        String delimiter() default "";

        /** 选项标签字段 */
        String labelField() default "";

        /** 选项值字段 */
        String valueField() default "";

        /** 拼接值 */
        boolean joinValues() default false;

        /** 提取值 */
        boolean extractValue() default false;

        /** 是否去除首尾空白文本。 */
        boolean trimContents() default false;

        /** 文本内容为空时去掉这个值 */
        boolean clearValueOnEmpty() default false;

        /** 是否可以创建，默认为可以，除非设置为 false 即只能选择选项中的值 */
        boolean creatable() default false;

        /** 是否可清除 */
        boolean clearable() default false;

        /** 清除后设置此配置项给定的值。 */
        String resetValue() default "";

        /** 前缀 */
        String prefix() default "";

        /** 后缀 */
        String suffix() default "";

        /** 是否显示计数器 */
        boolean showCounter() default false;

        /** 限制最小字数 */
        int minLength() default 0;

        /** 限制最大字数 */
        int maxLength() default 9999;

        // /** 自动转换值，可选 transform: { lowerCase: true, upperCase: true } */
        // Object transform() default "";
        // /** 输入框边框模式，全边框，还是半边框，或者没边框。 */
        InputTextBorderMode borderMode() default InputTextBorderMode.full;

        /** control 节点的 CSS 类名 */
        String inputControlClassName() default "";

        /** 原生 input 标签的 CSS 类名 */
        String nativeInputClassName() default "";

        /** 原生 input 标签的 autoComplete 属性，比如配置集成 new-password */
        String nativeAutoComplete() default "";
    }
}