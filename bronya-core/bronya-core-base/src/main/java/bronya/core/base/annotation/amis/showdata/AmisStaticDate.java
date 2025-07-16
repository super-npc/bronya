package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisStaticDate extends AmisComponents {

    public AmisStaticDate(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    private String value;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface StaticDate {

        // /** 显示的日期数值 */
        // String value() default "";
    }

    public static AmisStaticDate change(Boolean required,StaticDate annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisStaticDate temp = new AmisStaticDate(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        // temp.setValue(annotation.value());
        return temp;
    }
}