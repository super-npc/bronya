package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisHtml extends AmisComponents {

    public AmisHtml(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** html代码 */
    private String html;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Html {
        /** html代码 */
        String html() default "";
    }

    public static AmisHtml change(Boolean required,Html annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisHtml temp = new AmisHtml(required,type, name, label,disabled,width,remark);
        temp.setHtml(annotation.html());
        return temp;
    }
}