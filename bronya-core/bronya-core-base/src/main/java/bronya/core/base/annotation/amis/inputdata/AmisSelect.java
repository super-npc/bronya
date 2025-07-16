package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.List;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.annotation.amis.type.AmisColumnNameLabel;
import bronya.core.base.annotation.amis.type.AmisOptions;
import bronya.core.base.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisSelect extends AmisComponents {

    public AmisSelect(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 选项组 */
    private AmisOptions options;
    /** 动态选项组 */
    private AmisApi source;
    /** 自动提示补全 */
    private AmisApi autoComplete;
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
    /** 是否支持全选 */
    private Boolean checkAll;
    /** 全选的文字 */
    private String checkAllLabel;
    /** 有检索时只全选检索命中的项 */
    private Boolean checkAllBySearch;
    /** 默认是否全选 */
    private Boolean defaultCheckAll;
    /** 新增选项 */
    private Boolean creatable;
    /** 多选 */
    private Boolean multiple;
    /** 检索 */
    private Boolean searchable;
    /** 新增选项 */
    private String createBtnLabel;
    // /** 自定义新增表单项 */
    // private Object addControls;
    /** 配置新增选项接口 */
    private AmisApi addApi;
    /** 编辑选项 */
    private Boolean editable;
    // /** 自定义编辑表单项 */
    // private Object editControls;
    /** 配置编辑选项接口 */
    private AmisApi editApi;
    /** 删除选项 */
    private Boolean removable;
    /** 配置删除选项接口 */
    private AmisApi deleteApi;
    // /** 自动填充 */
    // private Object autoFill;
    /** 支持配置自定义菜单 */
    private String menuTpl;
    /** 单选模式下是否支持清空 */
    private Boolean clearable;
    /** 隐藏已选选项 */
    private Boolean hideSelected;
    /** 移动端浮层类名 */
    private String mobileClassName;
    /**
     * 可选：group、table、tree、chained、associated。分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个
     * tree）。
     */
    private String selectMode;
    /** 如果不设置将采用 selectMode 的值，可以单独配置，参考 selectMode，决定搜索结果的展示形式。 */
    private String searchResultMode;
    /** 当展示形式为 table 可以用来配置展示哪些列，跟 table 中的 columns 配置相似，只是只有展示功能。 ps: 对应tr实现表格展示 */
    private List<AmisColumnNameLabel> columns;
    /** 当展示形式为 associated 时用来配置左边的选项集。 */
    private Object leftOptions;
    /** 当展示形式为 associated 时用来配置左边的选择形式，支持 list 或者 tree。默认为 list。 */
    private String leftMode;
    /** 当展示形式为 associated 时用来配置右边的选择形式，可选：list、table、tree、chained。 */
    private String rightMode;
    /** 标签的最大展示数量，超出数量后以收纳浮层的方式展示，仅在多选模式开启后生效 */
    private Integer maxTagCount;
    /** 收纳浮层的配置属性，详细配置参考Tooltip */
    private Object overflowTagPopover;
    /** 选项 CSS 类名 */
    private String optionClassName;
    /** 弹层挂载位置选择器，会通过querySelector获取 */
    private String popOverContainerSelector;
    /** 弹层宽度与对齐方式 2.8.0 以上版本 */
    private Object overlay;
    /** 选项值与选项组不匹配时选项值是否飘红 */
    private Boolean showInvalidMatch;


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Select {
        boolean enable() default false;
        /** 选项组 */
        // AmisOptions options() default "";
        /** 动态选项组 */
        // Object source() default "";

        /** 拼接符 */
        String delimiter() default "";

        /** 选项标签字段 */
        String labelField() default "";

        /** 选项值字段 */
        String valueField() default "";

        /** 拼接值 */
        boolean joinValues() default true;

        /** 提取值 */
        boolean extractValue() default false;

        /** 是否支持全选 */
        boolean checkAll() default false;

        /** 全选的文字 */
        String checkAllLabel() default "";

        /** 有检索时只全选检索命中的项 */
        boolean checkAllBySearch() default true;

        /** 默认是否全选 */
        boolean defaultCheckAll() default false;

        /** 新增选项 */
        boolean creatable() default false;

        /** 多选 */
        boolean multiple() default false;

        /** 检索 */
        boolean searchable() default true;

        /** 新增选项 */
        String createBtnLabel() default "";
        /** 自定义新增表单项 */
        // Object addControls() default "";
        /** 配置新增选项接口 */

        // Object addApi() default "";
        /** 编辑选项 */
        boolean editable() default false;

        // /** 自定义编辑表单项 */
        // Object editControls() default "";
        // /** 配置编辑选项接口 */
        // Object editApi() default "";
        /** 删除选项 */
        boolean removable() default false;

        // /** 配置删除选项接口 */
        // Object deleteApi() default "";
        // /** 自动填充 */
        // Object autoFill() default "";
        /** 支持配置自定义菜单 */
        String menuTpl() default "";

        /** 隐藏已选选项 */
        boolean hideSelected() default false;

        /** 移动端浮层类名 */
        String mobileClassName() default "";

        /**
         * 可选：group、table、tree、chained、associated。分别为：列表形式、表格形式、树形选择形式、级联选择形式，关联选择形式（与级联选择的区别在于，级联是无限极，而关联只有一级，关联左边可以是个
         * tree）。
         */
        String selectMode() default "table";

        /** 如果不设置将采用 selectMode 的值，可以单独配置，参考 selectMode，决定搜索结果的展示形式。 */
        String searchResultMode() default "";

        /** 当展示形式为 table 可以用来配置展示哪些列，跟 table 中的 columns 配置相似，只是只有展示功能。 */
        // Object columns() default "";
        // /** 当展示形式为 associated 时用来配置左边的选项集。 */
        // Object leftOptions() default "";
        /** 当展示形式为 associated 时用来配置左边的选择形式，支持 list 或者 tree。默认为 list。 */
        String leftMode() default "list";

        /** 当展示形式为 associated 时用来配置右边的选择形式，可选：list、table、tree、chained。 */
        String rightMode() default "list";

        /** 标签的最大展示数量，超出数量后以收纳浮层的方式展示，仅在多选模式开启后生效 */
        int maxTagCount() default 0;

        // /** 收纳浮层的配置属性，详细配置参考Tooltip */
        // Object overflowTagPopover() default ;
        /** 选项 CSS 类名 */
        String optionClassName() default "";

        /** 弹层挂载位置选择器，会通过querySelector获取 */
        String popOverContainerSelector() default "";

        /** 是否展示清空图标 */
        boolean clearable() default false;

        // /** 弹层宽度与对齐方式 2.8.0 以上版本 */
        // Object overlay() default "";
        /** 选项值与选项组不匹配时选项值是否飘红 */
        boolean showInvalidMatch() default true;

        /** 表头 */
        Tr[] tr() default {@Tr(name = "label", label = "主键"), @Tr(name = "columnName", label = "字段")};

        /** 自动提示补全 */
        AutoComplete autoComplete() default @AutoComplete();

        @interface AutoComplete {
            String getUrl() default "/admin/curd/select-auto-complete";

            /** 数据库检索字段 */
            String searchColumn() default BaseEntity.Fields.id;
        }

        @interface Tr {
            String name() default "";

            String label() default "";
        }
    }

    public static AmisSelect change(Boolean required,Select annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisSelect temp = new AmisSelect(required,type, name, label,disabled,width,remark);
        // temp.setOptions(annotation.options());
        // temp.setSource(annotation.source());
        // temp.setAutoComplete(annotation.autoComplete());
        temp.setDelimiter(annotation.delimiter());
        temp.setLabelField(annotation.labelField());
        temp.setValueField(annotation.valueField());
        temp.setJoinValues(annotation.joinValues());
        temp.setExtractValue(annotation.extractValue());
        temp.setCheckAll(annotation.checkAll());
        temp.setCheckAllLabel(annotation.checkAllLabel());
        temp.setCheckAllBySearch(annotation.checkAllBySearch());
        temp.setDefaultCheckAll(annotation.defaultCheckAll());
        temp.setCreatable(annotation.creatable());
        temp.setMultiple(annotation.multiple());
        temp.setSearchable(annotation.searchable());
        temp.setCreateBtnLabel(annotation.createBtnLabel());
        // temp.setAddControls(annotation.addControls());
        // temp.setAddApi(annotation.addApi());
        temp.setEditable(annotation.editable());
        // temp.setEditControls(annotation.editControls());
        // temp.setEditApi(annotation.editApi());
        temp.setRemovable(annotation.removable());
        // temp.setDeleteApi(annotation.deleteApi());
        // temp.setAutoFill(annotation.autoFill());
        temp.setMenuTpl(annotation.menuTpl());
        temp.setClearable(annotation.clearable());
        temp.setHideSelected(annotation.hideSelected());
        temp.setMobileClassName(annotation.mobileClassName());
        temp.setSelectMode(annotation.selectMode());
        temp.setSearchResultMode(annotation.searchResultMode());
        // temp.setColumns(annotation.columns());
        // temp.setLeftOptions(annotation.leftOptions());
        temp.setLeftMode(annotation.leftMode());
        temp.setRightMode(annotation.rightMode());
        temp.setMaxTagCount(annotation.maxTagCount());
        // temp.setOverflowTagPopover(annotation.overflowTagPopover());
        temp.setOptionClassName(annotation.optionClassName());
        temp.setPopOverContainerSelector(annotation.popOverContainerSelector());
        temp.setClearable(annotation.clearable());
        // temp.setOverlay(annotation.overlay());
        temp.setShowInvalidMatch(annotation.showInvalidMatch());
        return temp;
    }
}