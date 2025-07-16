package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.annotation.amis.type.dialog.DialogSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisPicker extends AmisComponents {

    public AmisPicker(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 动态选项组 */
    private AmisApi source;
    private DialogSize size;
    /** 是否为多选。 */
    private Boolean multiple;
    /** 拼接符 */
    private Boolean delimiter;
    /** 选项标签字段 */
    private String labelField;
    /** 选项值字段 */
    private String valueField;
    /** 拼接值 */
    private Boolean joinValues;
    /** 提取值 */
    private Boolean extractValue;
    /** 自动填充 */
    private Object autoFill;
    /** 设置模态框的标题 */
    private String modalTitle;
    /** 设置 dialog 或者 drawer，用来配置弹出方式。 */
    private String modalMode;
    /** 是否使用内嵌模式 */
    private Boolean embed;
    /** 开启最大标签展示数量的相关配置 */
    private Object overflowConfig;
    private Object pickerSchema;
    private Boolean required;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Picker {
        // /** 动态选项组 */
        // Object source() default "";
        /** 是否为多选。 */
        boolean multiple() default false;

        DialogSize size() default DialogSize.xl;

        // /** 拼接符 */
        // boolean delimiter() default ;
        // /** 选项标签字段 */
        // boolean labelField() default ;
        // /** 选项值字段 */
        // boolean valueField() default ;
        // /** 拼接值 */
        // boolean joinValues() default ;
        // /** 提取值 */
        // boolean extractValue() default ;
        // /** 自动填充 */
        // Object autoFill() default "";
        /** 设置模态框的标题 */
        String modalTitle() default "请选择";

        /** 设置 dialog 或者 drawer，用来配置弹出方式。 */
        String modalMode() default "dialog";

        /** 是否使用内嵌模式 */
        boolean embed() default false;
        // /** 开启最大标签展示数量的相关配置 */
        // Object overflowConfig() default ;
    }

    public static AmisPicker change(Boolean required,Picker annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisPicker temp = new AmisPicker(required,type, name, label,disabled,width,remark);
        // temp.setSource(annotation.source());
        temp.setMultiple(annotation.multiple());
        // temp.setDelimiter(annotation.delimiter());
        // temp.setLabelField(annotation.labelField());
        // temp.setValueField(annotation.valueField());
        // temp.setJoinValues(annotation.joinValues());
        // temp.setExtractValue(annotation.extractValue());
        // temp.setAutoFill(annotation.autoFill());
        temp.setModalTitle(annotation.modalTitle());
        temp.setModalMode(annotation.modalMode());
        temp.setEmbed(annotation.embed());
        // temp.setOverflowConfig(annotation.overflowConfig());
        return temp;
    }
}