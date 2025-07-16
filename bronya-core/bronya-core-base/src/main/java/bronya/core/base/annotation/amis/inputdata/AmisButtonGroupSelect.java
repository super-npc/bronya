package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.List;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.annotation.amis.type.AmisOptions;
import bronya.core.base.annotation.amis.type.Level;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisButtonGroupSelect extends AmisComponents {
    public AmisButtonGroupSelect(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 是否使用垂直模式 */
    private Boolean vertical;
    /** 是否使用平铺模式 */
    private Boolean tiled;
    /** 按钮样式 */
    private Level btnLevel;
    /** 选中按钮样式 */
    private Level btnActiveLevel;
    /** 选项组 */
    private List<AmisOptions> options;
    // /** 角标 */
    // private Object option.badge;
    /** 动态选项组 */
    private AmisApi source;
    /** 多选 */
    private Boolean multiple;
    /** 选项标签字段 */
    private Boolean labelField;
    /** 选项值字段 */
    private Boolean valueField;
    /** 拼接值 */
    private Boolean joinValues;
    /** 提取值 */
    private Boolean extractValue;
    // /** 自动填充 */
    // private Object autoFill;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface ButtonGroupSelect {
        /** 是否使用垂直模式 */
        boolean vertical() default false;

        /** 是否使用平铺模式 */
        boolean tiled() default false;

        /** 按钮样式 */
        Level btnLevel() default Level.secondary;

        /** 选中按钮样式 */
        Level btnActiveLevel() default Level.info;
        // /** 选项组 */
        // AmisOptions options() default {};
        // /** 角标 */
        // Object option.badge() default "";
        /** 动态选项组 */

        // Object source() default "";
        /** 多选 */
        boolean multiple() default false;

        /** 选项标签字段 */
        boolean labelField() default false;

        /** 选项值字段 */
        boolean valueField() default false;

        /** 拼接值 */
        boolean joinValues() default true;

        /** 提取值 */
        boolean extractValue() default false;
        // /** 自动填充 */
        // Object autoFill() default "";
    }

    public static AmisButtonGroupSelect change(Boolean required,ButtonGroupSelect annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisButtonGroupSelect temp = new AmisButtonGroupSelect(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        temp.setVertical(annotation.vertical());
        temp.setTiled(annotation.tiled());
        temp.setBtnLevel(annotation.btnLevel());
        temp.setBtnActiveLevel(annotation.btnActiveLevel());
        // temp.setOptions(annotation.options());
        // temp.setOption.badge(annotation.option.badge());
        // temp.setSource(annotation.source());
        temp.setMultiple(annotation.multiple());
        temp.setLabelField(annotation.labelField());
        temp.setValueField(annotation.valueField());
        temp.setJoinValues(annotation.joinValues());
        temp.setExtractValue(annotation.extractValue());
        // temp.setAutoFill(annotation.autoFill());
        return temp;
    }
}