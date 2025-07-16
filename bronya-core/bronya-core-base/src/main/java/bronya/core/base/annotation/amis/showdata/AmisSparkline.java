package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import org.dromara.hutool.core.text.StrUtil;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisSparkline extends AmisComponents {

    public AmisSparkline(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 高度 */
    private String height;
    /** 数据为空时显示的内容 */
    private String placeholder;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Sparkline {
        /** 宽度 */
        String width() default "";

        /** 高度 */
        String height() default "";

        /** 数据为空时显示的内容 */
        String placeholder() default "无数据";
    }

    public static AmisSparkline change(Boolean required,Sparkline annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisSparkline temp = new AmisSparkline(required,type, name, label,disabled,width,remark);
        if (StrUtil.isNotBlank(annotation.width())) {
            temp.setWidth(annotation.width());
        }
        if (StrUtil.isNotBlank(annotation.height())) {
            temp.setHeight(annotation.height());
        }
        temp.setPlaceholder(annotation.placeholder());
        return temp;
    }
}