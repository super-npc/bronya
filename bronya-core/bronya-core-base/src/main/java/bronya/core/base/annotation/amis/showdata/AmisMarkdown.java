package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisMarkdown extends AmisComponents {

    public AmisMarkdown(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /**  */
    private String name;
    /**  */
    private String value;
    /**  */
    private String className;
    /**  */
    private AmisApi src;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Markdown {
        /**  */
        String name() default "";

        /**  */
        String value() default "";

        /**  */
        String className() default "";
        // /** */
        // AmisApi src() default ;
    }

    public static AmisMarkdown change(Boolean required,Markdown annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisMarkdown temp = new AmisMarkdown(required,type, name, label,disabled,width,remark);
        temp.setName(annotation.name());
        temp.setValue(annotation.value());
        temp.setClassName(annotation.className());
        // temp.setSrc(annotation.src());
        return temp;
    }
}