package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisCheckboxes extends AmisComponents {

    public AmisCheckboxes(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 选项组 */
    private Object options;
    /** 动态选项组 */
    private Object source;
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
    /** 选项按几列显示，默认为一列 */
    private Integer columnsCount;
    /** 支持自定义选项渲染 */
    private String menuTpl;
    /** 是否支持全选 */
    private Boolean checkAll;
    /** 是否显示为一行 */
    private Boolean inline;
    /** 默认是否全选 */
    private Boolean defaultCheckAll;
    /** 新增选项 */
    private Boolean creatable;
    /** 新增选项 */
    private String createBtnLabel;
    /** 自定义新增表单项 */
    private Object addControls;
    /** 配置新增选项接口 */
    private Object addApi;
    /** 编辑选项 */
    private Boolean editable;
    /** 自定义编辑表单项 */
    private Object editControls;
    /** 配置编辑选项接口 */
    private Object editApi;
    /** 删除选项 */
    private Boolean removable;
    /** 配置删除选项接口 */
    private Object deleteApi;
    /** 按钮模式 */
    private Object optionType;
    /** 选项样式类名 */
    private String itemClassName;
    /** 选项标签样式类名 */
    private String labelClassName;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Checkboxes {
        /** 拼接符 */
        String delimiter() default ",";

        /** 选项标签字段 */
        String labelField() default "label";

        /** 选项值字段 */
        String valueField() default "value";

        /** 拼接值 */
        boolean joinValues() default true;

        /** 提取值 */
        boolean extractValue() default false;

        /** 选项按几列显示，默认为一列 */
        int columnsCount() default 1;

        /** 支持自定义选项渲染 */
        String menuTpl() default "";

        /** 是否支持全选 */
        boolean checkAll() default false;

        /** 是否显示为一行 */
        boolean inline() default true;

        /** 默认是否全选 */
        boolean defaultCheckAll() default false;

        /** 新增选项 */
        boolean creatable() default false;

        /** 新增选项 */
        String createBtnLabel() default "新增选项";

        // /** 自定义新增表单项 */
        // Object addControls() default "";
        // /** 配置新增选项接口 */
        // Object addApi() default "";
        // /** 编辑选项 */
        // boolean editable() default ;
        // /** 自定义编辑表单项 */
        // Object editControls() default "";
        // /** 配置编辑选项接口 */
        // Object editApi() default "";
        /** 删除选项 */
        boolean removable() default false;
        // /** 配置删除选项接口 */
        // Object deleteApi() default "";
        // /** 按钮模式 */
        // Object optionType() default default;
        // /** 选项样式类名 */
        // String itemClassName() default "";
        // /** 选项标签样式类名 */
        // String labelClassName() default "";
    }

    public static AmisCheckboxes change(Boolean required,Checkboxes annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisCheckboxes temp = new AmisCheckboxes(required,type, name, label,disabled,width,remark);
        // temp.setOptions(annotation.options());
        // temp.setSource(annotation.source());
        temp.setDelimiter(annotation.delimiter());
        temp.setLabelField(annotation.labelField());
        temp.setValueField(annotation.valueField());
        temp.setJoinValues(annotation.joinValues());
        temp.setExtractValue(annotation.extractValue());
        temp.setColumnsCount(annotation.columnsCount());
        temp.setMenuTpl(annotation.menuTpl());
        temp.setCheckAll(annotation.checkAll());
        temp.setInline(annotation.inline());
        temp.setDefaultCheckAll(annotation.defaultCheckAll());
        temp.setCreatable(annotation.creatable());
        temp.setCreateBtnLabel(annotation.createBtnLabel());
        // temp.setAddControls(annotation.addControls());
        // temp.setAddApi(annotation.addApi());
        // temp.setEditable(annotation.editable());
        // temp.setEditControls(annotation.editControls());
        // temp.setEditApi(annotation.editApi());
        temp.setRemovable(annotation.removable());
        // temp.setDeleteApi(annotation.deleteApi());
        // temp.setOptionType(annotation.optionType());
        // temp.setItemClassName(annotation.itemClassName());
        // temp.setLabelClassName(annotation.labelClassName());
        return temp;
    }
}