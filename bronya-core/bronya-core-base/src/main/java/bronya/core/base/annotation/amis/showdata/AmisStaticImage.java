package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.constant.BulinkeModelConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisStaticImage extends AmisComponents {

    public AmisStaticImage(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    private String url;
    private Boolean enlargeAble;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface StaticImage {

//        /** 路径 ps: 这个是自定义的注解名称,通过 */
        String customUrl() default BulinkeModelConstant.UPLOAD_FILE_PATH;

        /**
         * 支持放大
         */
        boolean enlargeAble() default true;
    }

    public static AmisStaticImage change(Boolean required,StaticImage annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisStaticImage temp = new AmisStaticImage(required,type, name, label,disabled,width,remark);
        temp.setEnlargeAble(annotation.enlargeAble());
        // temp.setType(annotation.type());
        // temp.setValue(annotation.value());
        return temp;
    }
}