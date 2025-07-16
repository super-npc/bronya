package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.List;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.constant.IConditionBuilderTarget;
import lombok.*;

@Getter
@Setter
public class AmisConditionBuilder extends AmisComponents {

    public AmisConditionBuilder(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /**
     * 外层 dom 类名
     */
    private String className;
    private Boolean disabled;
    /**
     * 输入字段的类名
     */
    private String fieldClassName;
    /**
     * 通过远程拉取配置项
     */
    private AmisApi source;
    /**
     * 内嵌展示
     */
    private Boolean embed;
    /**
     * 弹窗配置的顶部标题
     */
    private String title;
    /**
     * 字段配置
     */
    private List<ConditionColumn> fields;
    /**
     * 用于 simple 模式下显示切换按钮
     */
    private Boolean showANDOR;
    /**
     * 是否显示「非」按钮
     */
    private Boolean showNot;
    /**
     * 是否可拖拽
     */
    private Boolean draggable;
    /**
     * 字段是否可搜索
     */
    private Boolean searchable;
    /**
     * 组合条件左侧选项类型。'chained'模式需要3.2.0及以上版本
     */
    private Object selectMode;
    /**
     * 表达式：控制按钮“添加条件”的显示。参数为depth、breadth，分别代表深度、长度。表达式需要返回boolean类型
     */
    private String addBtnVisibleOn;
    /**
     * 表达式：控制按钮“添加条件组”的显示。参数为depth、breadth，分别代表深度、长度。表达式需要返回boolean类型
     */
    private String addGroupBtnVisibleOn;
    // /** 开启公式编辑模式时的输入控件类型 */
    // private Object inputSettings;
    // /** 字段输入控件变成公式编辑器。 */
    // private Object formula;
    // /** 开启后条件中额外还能配置启动条件。 */
    // private Boolean showIf;
    // /** 给 showIF 表达式用的公式信息 */
    // private Object formulaForIf;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface ConditionBuilder {
        Class<?> target() default Void.class;

        /**
         * target / targetDynamic 二选一
         * targetDynamic 要做成source动态规则字段
         */
        Class<? extends IConditionBuilderTarget> targetDynamic() default IConditionBuilderTarget.class;

//        /**
//         * 通过远程拉取配置项 与 target只能2选1 (由)
//         */
//        @Deprecated
//        String source() default "";

        /**
         * 查询拼接参数
         */
        String url() default "";

        /**
         * 外层 dom 类名
         */
        String className() default "";

        /**
         * 输入字段的类名
         */
        String fieldClassName() default "";

        /**
         * 内嵌展示
         */
        boolean embed() default true;

        /**
         * 弹窗配置的顶部标题
         */
        String title() default "";
        // /** 字段配置 */
        // Object[] fields();

        /**
         * 用于 simple 模式下显示切换按钮
         */
        boolean showANDOR() default false;

        /**
         * 是否显示「非」按钮
         */
        boolean showNot() default false;

        /**
         * 是否可拖拽
         */
        boolean draggable() default true;

        /**
         * 字段是否可搜索
         */
        boolean searchable() default false;

        /**
         * 组合条件左侧选项类型。'chained'模式需要3.2.0及以上版本 'list' | 'tree' | 'chained'
         */
        String selectMode() default "list";

        /**
         * 表达式：控制按钮“添加条件”的显示。参数为depth、breadth，分别代表深度、长度。表达式需要返回boolean类型
         */
        String addBtnVisibleOn() default "";

        // /**
        // * 表达式：控制按钮“添加条件组”的显示。参数为depth、breadth，分别代表深度、长度。表达式需要返回boolean类型
        // */
        // String addGroupBtnVisibleOn() default ""; // 限制两层

        /**
         * 深度
         */
        int depth() default 1;
        // /** 开启公式编辑模式时的输入控件类型 */
        // Object inputSettings() default "";
        // /** 字段输入控件变成公式编辑器。 */
        // Object formula() default "";
        // /** 开启后条件中额外还能配置启动条件。 */
        // boolean showIf() default false;
        // /** 给 showIF 表达式用的公式信息 */
        // Object formulaForIf() default "";
    }

    public static AmisConditionBuilder change(Boolean required,ConditionBuilder annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisConditionBuilder temp = new AmisConditionBuilder(required,type, name, label,disabled,width,remark);
        temp.setClassName(annotation.className());
        temp.setFieldClassName(annotation.fieldClassName());
//        temp.setSource(annotation.source());
        temp.setEmbed(annotation.embed());
        temp.setTitle(annotation.title());
        // temp.setFields(annotation.fields());
        temp.setShowANDOR(annotation.showANDOR());
        temp.setShowNot(annotation.showNot());
        temp.setDraggable(annotation.draggable());
        temp.setSearchable(annotation.searchable());
        temp.setSelectMode(annotation.selectMode());
        temp.setAddBtnVisibleOn(annotation.addBtnVisibleOn());
        temp.setAddGroupBtnVisibleOn(STR."this.depth < \{annotation.depth()}");
        // temp.setInputSettings(annotation.inputSettings());
        // temp.setFormula(annotation.formula());
        // temp.setShowIf(annotation.showIf());
        // temp.setFormulaForIf(annotation.formulaForIf());
        return temp;
    }

    @NoArgsConstructor
    @Data
    public static class ConditionColumn {
        private String label;
        private String type;
        private String name;
        private List<OptionsDTO> options;
        private AmisApi source;

        private String defaultOp;
        private List<String> operators;

        // number
        private Integer precision;
        private String minimum; // 超过范围前端无法接收,使用String
        private String maximum;
        private Integer step;

        // 下拉 远程匹配
        // "searchable": true,
        // "autoComplete": "/amis/api/mock2/options/autoComplete?term=$term"

        @NoArgsConstructor
        @AllArgsConstructor
        @Data
        public static class OptionsDTO {
            private String label;
            private String value;
        }
    }
}