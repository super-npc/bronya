package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.annotation.amis.type.AmisOptions;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisRadios extends AmisComponents {

    public AmisRadios(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 选项组 */
    private AmisOptions options;
    /** 动态选项组 */
    private AmisApi source;
    /** 选项标签字段 */
    private String labelField;
    /** 选项值字段 */
    private String valueField;
    /** 选项按几列显示，默认为一列 */
    private Integer columnsCount;
    /** 是否显示为一行 */
    private Boolean inline;
    /** 是否默认选中第一个 */
    private Boolean selectFirst;
    /** 自动填充 */
    private Object autoFill;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Radios {
        // /** 选项组 */
        // AmisOptions options() ;
        // /** 动态选项组 */
        // AmisApi source() default new AmisApi();
        /** 选项标签字段 */
        String labelField() default "";

        /** 选项值字段 */
        String valueField() default "";

        /** 选项按几列显示，默认为一列 */
        int columnsCount() default 1;

        /** 是否显示为一行 */
        boolean inline() default true;

        /** 是否默认选中第一个 */
        boolean selectFirst() default true;
        /** 自动填充 */
        // Object autoFill() default "";
    }

    public static AmisRadios change(Boolean required,Radios annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisRadios temp = new AmisRadios(required,type, name, label,disabled,width,remark);
        // temp.setOptions(annotation.options());
        // temp.setSource(annotation.source());
        temp.setLabelField(annotation.labelField());
        temp.setValueField(annotation.valueField());
        temp.setColumnsCount(annotation.columnsCount());
        temp.setInline(annotation.inline());
        temp.setSelectFirst(annotation.selectFirst());
        // temp.setAutoFill(annotation.autoFill());
        return temp;
    }
}