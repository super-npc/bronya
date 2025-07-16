package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;
import java.util.Map;

import org.dromara.hutool.core.map.MapUtil;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisStaticMapping extends AmisComponents {

    public AmisStaticMapping(Boolean required, String type, String name, String label, Boolean disabled, String width,String remark) {
        super(required, type, name, label, disabled, width,remark);
    }

    private String value;
    private Map<String, AmisTag> map;

    {
        map = MapUtil.newHashMap();
        AmisTag amisTag = new AmisTag(true, "tag", "", "-", false, null,null);
        map.put("*", amisTag);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface StaticMapping {

        // /** 显示的日期数值 */
        // String value() default "";
    }

    public static AmisStaticMapping change(Boolean required, StaticMapping annotation, String type, String name,
        String label, Boolean disabled, String width) {
        AmisStaticMapping temp = new AmisStaticMapping(required, type, name, label, disabled, width,null);
        // temp.setType(annotation.type());
        // temp.setValue(annotation.value());
        return temp;
    }
}