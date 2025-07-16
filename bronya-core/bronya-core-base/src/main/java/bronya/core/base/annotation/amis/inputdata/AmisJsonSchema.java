package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.JsonSchemaVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisJsonSchema extends AmisComponents {

    public AmisJsonSchema(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 指定 json-schema */
    private JsonSchemaVo.SchemaVo schema;

    private Class<?> target;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface JsonSchema {
        // /** 指定 json-schema */
        // String schema() default "";
        Class<?> target() default Object.class;
    }

    public static AmisJsonSchema change(Boolean required,JsonSchema annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisJsonSchema temp = new AmisJsonSchema(required,type, name, label,disabled,width,remark);
        temp.setTarget(annotation.target());
        // temp.setSchema(annotation.schema());
        return temp;
    }
}