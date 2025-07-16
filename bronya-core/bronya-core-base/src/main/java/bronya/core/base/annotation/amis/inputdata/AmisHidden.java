package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisHidden extends AmisComponents {
    private final String type = "hidden";
    private Integer value;

    public AmisHidden(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Hidden {

        // /** 显示的日期数值 */
        // String value() default "";
    }

    public static AmisHidden change(Boolean required,Hidden annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisHidden temp = new AmisHidden(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        // temp.setValue(annotation.value());
        return temp;
    }
}
