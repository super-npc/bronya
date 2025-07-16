package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisLink extends AmisComponents {

    public AmisLink(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    // /** 如果在 Table、Card 和 List 中，为"link"；在 Form 中用作静态展示，为"static-link" */
    // private String type;
    /** 标签内文本 */
    private String body;
    /** 链接地址 */
    private String href;
    /** 是否在新标签页打开 */
    private Boolean blank;
    /** a 标签的 target，优先于 blank 属性 */
    private String htmlTarget;
    /** a 标签的 title */
    private String title;
    /** 禁用超链接 */
    private Boolean disabled;
    /** 超链接图标，以加强显示 */
    private String icon;
    /** 右侧图标 */
    private String rightIcon;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Link {
        /** 标签内文本 */
        String body() default "";

        /** 链接地址 */
        String href() default "";

        /** 是否在新标签页打开 */
        boolean blank() default true;

        /** a 标签的 target，优先于 blank 属性 */
        String htmlTarget() default "";

        /** a 标签的 title */
        String title() default "";

        /** 禁用超链接 */
        boolean disabled() default false;

        /** 超链接图标，以加强显示 */
        String icon() default "";

        /** 右侧图标 */
        String rightIcon() default "";
    }

    public static AmisLink change(Boolean required,Link annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisLink temp = new AmisLink(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        temp.setBody(annotation.body());
        temp.setHref(annotation.href());
        temp.setBlank(annotation.blank());
        temp.setHtmlTarget(annotation.htmlTarget());
        temp.setTitle(annotation.title());
        temp.setDisabled(annotation.disabled());
        temp.setIcon(annotation.icon());
        temp.setRightIcon(annotation.rightIcon());
        return temp;
    }
}