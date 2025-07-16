package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.dialog.DialogSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisDialog extends AmisComponents {

    public AmisDialog(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

//    public AmisDialog(String name, String label) {
//        super("dialog", name, label);
//    }

    /** 弹出层标题 */
    private String title;
    /** 往 Dialog 内容区加内容 */
    private Object body;
    /** 指定 dialog 大小，支持: xs、sm、md、lg、xl、full */
    private DialogSize size;
    /** Dialog body 区域的样式类名 */
    private String bodyClassName;
    /** 是否支持按 Esc 关闭 Dialog */
    private Boolean closeOnEsc;
    /** 是否显示右上角的关闭按钮 */
    private Boolean showCloseButton;
    /** 是否在弹框左下角显示报错信息 */
    private Boolean showErrorMsg;
    /** 是否在弹框左下角显示 loading 动画 */
    private Boolean showLoading;
    /** 如果设置此属性，则该 Dialog 只读没有提交操作。 */
    private Boolean disabled;
    // /** 如果想不显示底部按钮，可以配置：[] */
    // private Object actions;
    // /** 支持数据映射，如果不设定将默认将触发按钮的上下文中继承数据。 */
    // private Object data;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Dialog {
        /** "dialog" 指定为 Dialog 渲染器 */
        String type() default "";

        /** 弹出层标题 */
        String title() default "";

        // /** 往 Dialog 内容区加内容 */
        // Object body() default "";
        /** 指定 dialog 大小，支持: xs、sm、md、lg、xl、full */
        DialogSize size() default DialogSize.md;

        /** Dialog body 区域的样式类名 */
        String bodyClassName() default "";

        /** 是否支持按 Esc 关闭 Dialog */
        boolean closeOnEsc() default true;

        /** 是否显示右上角的关闭按钮 */
        boolean showCloseButton() default true;

        /** 是否在弹框左下角显示报错信息 */
        boolean showErrorMsg() default true;

        /** 是否在弹框左下角显示 loading 动画 */
        boolean showLoading() default true;

        /** 如果设置此属性，则该 Dialog 只读没有提交操作。 */
        boolean disabled() default false;
        // /** 如果想不显示底部按钮，可以配置：[] */
        // Object actions() default ;
        // /** 支持数据映射，如果不设定将默认将触发按钮的上下文中继承数据。 */
        // Object data() default "";
    }

    public static AmisDialog change(Boolean required,Dialog annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisDialog temp = new AmisDialog(required,type, name, label,disabled,width,remark);
        temp.setType(annotation.type());
        temp.setTitle(annotation.title());
        // temp.setBody(annotation.body());
        temp.setSize(annotation.size());
        temp.setBodyClassName(annotation.bodyClassName());
        temp.setCloseOnEsc(annotation.closeOnEsc());
        temp.setShowCloseButton(annotation.showCloseButton());
        temp.setShowErrorMsg(annotation.showErrorMsg());
        temp.setShowLoading(annotation.showLoading());
        temp.setDisabled(annotation.disabled());
        // temp.setActions(annotation.actions());
        // temp.setData(annotation.data());
        return temp;
    }
}