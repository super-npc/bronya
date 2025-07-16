package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.button.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisButton extends AmisComponents {

    public AmisButton(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

//    public AmisButton(String name, String label) {
//        super("button", name, label);
//    }

    /** 指定添加 button 类名 */
    private String className;
    /** 点击跳转的地址，指定此属性 button 的行为和 a 链接一致 */
    private String url;
    /** 设置按钮大小 */
    private Size size;
    /** 设置按钮类型 */
    private ActionType actionType;
    /** 设置按钮样式 */
    private Operation.BtnLevelType level;
    /** 气泡提示内容 */
    private Tooltip tooltip;
    /** 气泡框位置器 */
    private TooltipPlacement tooltipPlacement;
    /** 触发 tootip */
    private TooltipTrigger tooltipTrigger;
    /** 按钮失效状态 */
    private Boolean disabled;
    /** 按钮失效状态下的提示 */
    private Tooltip disabledTip;
    /** 将按钮宽度调整为其父宽度的选项 */
    private Boolean block;
    /** 显示按钮 loading 效果 */
    private Boolean loading;
    /** 显示按钮 loading 表达式 */
    private String loadingOn;
    private Object dialog;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Button {
        /** 指定添加 button 类名 */
        String className() default "";

        /** 点击跳转的地址，指定此属性 button 的行为和 a 链接一致 */
        String url() default "";

        /** 设置按钮大小 */
        Size size() default Size.xs;

        /** 设置按钮类型 */
        ActionType actionType() default ActionType.button;

        /** 设置按钮样式 */
        Operation.BtnLevelType level() default Operation.BtnLevelType.light;

        /** 气泡提示内容 */
        Tooltip tooltip() default Tooltip.string;

        /** 气泡框位置器 */
        TooltipPlacement tooltipPlacement() default TooltipPlacement.top;

        /** 触发 tootip */
        TooltipTrigger tooltipTrigger() default TooltipTrigger.focus;

        /** 按钮失效状态 */
        boolean disabled() default false;

        /** 按钮失效状态下的提示 */
        Tooltip disabledTip() default Tooltip.string;

        /** 将按钮宽度调整为其父宽度的选项 */
        boolean block() default false;

        /** 显示按钮 loading 效果 */
        boolean loading() default false;

        /** 显示按钮 loading 表达式 */
        String loadingOn() default "string";
    }

    public static AmisButton change(Boolean required,Button annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisButton temp = new AmisButton(required,type, name, label,disabled,width,remark);
        temp.setClassName(annotation.className());
        temp.setUrl(annotation.url());
        temp.setSize(annotation.size());
        temp.setActionType(annotation.actionType());
        temp.setLevel(annotation.level());
        temp.setTooltip(annotation.tooltip());
        temp.setTooltipPlacement(annotation.tooltipPlacement());
        temp.setTooltipTrigger(annotation.tooltipTrigger());
        temp.setDisabled(annotation.disabled());
        temp.setDisabledTip(annotation.disabledTip());
        temp.setBlock(annotation.block());
        temp.setLoading(annotation.loading());
        temp.setLoadingOn(annotation.loadingOn());
        return temp;
    }
}