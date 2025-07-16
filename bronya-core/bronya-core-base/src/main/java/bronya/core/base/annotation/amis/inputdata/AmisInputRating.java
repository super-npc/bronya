package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.Map;

import org.dromara.hutool.core.map.MapUtil;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.shared.module.common.type.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputRating extends AmisComponents {

    public AmisInputRating(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 是否使用半星选择 */
    private Boolean half;
    /** 总星数 */
    private Integer count;
    /** 只读 */
    private Boolean readOnly;
    /** 是否允许再次点击后清除 */
    private Boolean allowClear;
    /** 星星被选中的颜色。 若传入字符串，则只有一种颜色。若传入对象，可自定义分段，键名为分段的界限值，键值为对应的类名 */
    private Object colors;
    /** 未被选中的星星的颜色 */
    private String inactiveColor;
    /** 星星被选中时的提示文字。可自定义分段，键名为分段的界限值，键值为对应的类名 */
    private Map<Integer, String> texts;
    /** 文字的位置 */
    private Object textPosition;
    // /** 自定义字符 */
    // private String char;
    /** 自定义样式类名 */
    private String className;
    /** 自定义字符类名 */
    private String charClassName;
    /** 自定义文字类名 */
    private String textClassName;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputRating {
        /** 是否使用半星选择 */
        boolean half() default true;

        /** 总星数 */
        int count() default 5;

        /** 只读 */
        boolean readOnly() default false;

        /** 是否允许再次点击后清除 */
        boolean allowClear() default true;

        // /** 星星被选中的颜色。 若传入字符串，则只有一种颜色。若传入对象，可自定义分段，键名为分段的界限值，键值为对应的类名 */
        Color colors() default Color.皇军蓝;

        /** 未被选中的星星的颜色 */
        Color inactiveColor() default Color.浅灰色;

        // /** 星星被选中时的提示文字。可自定义分段，键名为分段的界限值，键值为对应的类名 */
        // Map<Integer,String> texts() default ;
        /** 文字的位置 */
        String textPosition() default "right";

        // /** 自定义字符 */
        // String char() default ;
        /** 自定义样式类名 */
        String className() default "";

        /** 自定义字符类名 */
        String charClassName() default "";

        /** 自定义文字类名 */
        String textClassName() default "";
    }

    public static AmisInputRating change(Boolean required,InputRating annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputRating temp = new AmisInputRating(required,type, name, label,disabled,width,remark);
        temp.setHalf(annotation.half());
        temp.setCount(annotation.count());
        temp.setReadOnly(annotation.readOnly());
        temp.setAllowClear(annotation.allowClear());
        temp.setColors(annotation.colors().getHex());
        temp.setInactiveColor(annotation.inactiveColor().getHex());
        temp.setTexts(MapUtil.builder(1, "很差").put(2, "较差").put(3, "一般").put(4, "较好").put(5, "很好").build());
        temp.setTextPosition(annotation.textPosition());
        // temp.setChar(annotation.char());
        temp.setClassName(annotation.className());
        temp.setCharClassName(annotation.charClassName());
        temp.setTextClassName(annotation.textClassName());
        return temp;
    }
}