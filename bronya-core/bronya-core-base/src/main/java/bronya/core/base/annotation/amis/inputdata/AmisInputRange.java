package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.InputRange.InputRangeTooltipPlacement;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputRange extends AmisComponents {

    public AmisInputRange(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** css 类名 */
    private String className;
    /** 最小值，支持变量 */
    private Object min;
    /** 最大， 支持变量值 */
    private Object max;
    /** 是否禁用 */
    private Boolean disabled;
    /** 步长，支持变量 */
    private Object step;
    /** 是否显示步长 */
    private Boolean showSteps;
    /** 分割的块数 */
    private Object parts;
    /** 是否显示滑块标签 */
    private Boolean tooltipVisible;
    /** 滑块标签的位置，默认auto，方向自适应 前置条件：tooltipVisible 不为 false 时有效 */
    private InputRangeTooltipPlacement tooltipPlacement;
    /** 支持选择范围 */
    private Boolean multiple;
    /** 默认为 true，选择的 value 会通过 delimiter 连接起来，否则直接将以{min: 1, max: 100}的形式提交 前置条件：开启multiple时有效 */
    private Boolean joinValues;
    /** 是否可清除 前置条件：开启showInput时有效 */
    private Boolean clearable;
    /** 是否显示输入框 */
    private Boolean showInput;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputRange {
        /** css 类名 */
        String className() default "";

        /** 最小值，支持变量 */
        int min() default 0;

        /** 最大， 支持变量值 */
        int max() default 100;

        /** 是否禁用 */
        boolean disabled() default false;

        /** 步长，支持变量 */
        int step() default 1;

        /** 是否显示步长 */
        boolean showSteps() default false;

        /** 分割的块数 */
        int parts() default 1;

        /** 是否显示滑块标签 */
        boolean tooltipVisible() default true;

        /** 滑块标签的位置，默认auto，方向自适应 前置条件：tooltipVisible 不为 false 时有效 */
        InputRangeTooltipPlacement tooltipPlacement() default InputRangeTooltipPlacement.auto;

        /** 支持选择范围 */
        boolean multiple() default false;

        /** 默认为 true，选择的 value 会通过 delimiter 连接起来，否则直接将以{min: 1, max: 100}的形式提交 前置条件：开启multiple时有效 */
        boolean joinValues() default true;

        /** 是否可清除 前置条件：开启showInput时有效 */
        boolean clearable() default false;

        /** 是否显示输入框 */
        boolean showInput() default false;
    }

    public static AmisInputRange change(Boolean required,InputRange annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputRange temp = new AmisInputRange(required,type, name, label,disabled,width,remark);
        temp.setClassName(annotation.className());
        temp.setMin(annotation.min());
        temp.setMax(annotation.max());
        temp.setDisabled(annotation.disabled());
        temp.setStep(annotation.step());
        temp.setShowSteps(annotation.showSteps());
        temp.setParts(annotation.parts());
        temp.setTooltipVisible(annotation.tooltipVisible());
        temp.setTooltipPlacement(annotation.tooltipPlacement());
        temp.setMultiple(annotation.multiple());
        temp.setJoinValues(annotation.joinValues());
        temp.setClearable(annotation.clearable());
        temp.setShowInput(annotation.showInput());
        return temp;
    }
}