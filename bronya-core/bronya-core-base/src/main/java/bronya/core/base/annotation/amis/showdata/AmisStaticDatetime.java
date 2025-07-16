package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisStaticDatetime extends AmisComponents {

    public AmisStaticDatetime(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    private String value;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface StaticDatetime {

        // /** 显示的日期数值 */
        // String value() default "";
    }

    public static AmisStaticDatetime change(Boolean required,StaticDatetime annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisStaticDatetime temp = new AmisStaticDatetime(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        // temp.setValue(annotation.value());
        return temp;
    }
}