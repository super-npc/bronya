package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisDate extends AmisComponents {

    public AmisDate(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    // /** 如果在 Table、Card 和 List 中，为"date"；在 Form 中用作静态展示，为"static-date" */
    // private String type;
    /** 外层 CSS 类名 */
    private String className;
    // /** 显示的日期数值 */
    // private String value;
    // /** 在其他组件中，时，用作变量映射 */
    // private String name;
    /** 占位内容 */
    private String placeholder;
    /** 展示格式, 更多格式类型请参考 文档 */
    private String displayFormat;
    /** 数据格式，默认为时间戳。更多格式类型请参考 文档 */
    private String valueFormat;
    /** 是否显示相对当前的时间描述，比如: 11 小时前、3 天前、1 年前等，fromNow 为 true 时，format 不生效。 */
    private Boolean fromNow;
    /** 更新频率， 默认为 1 分钟 */
    private Integer updateFrequency;
    /** 设置日期展示时区，可设置清单参考：https://gist.github.com/diogocapela/12c6617fc87607d11fd62d2a4f42b02a */
    private String displayTimeZone;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Date {
        // /** 如果在 Table、Card 和 List 中，为"date"；在 Form 中用作静态展示，为"static-date" */
        // String type() default "";
        /** 外层 CSS 类名 */
        String className() default "";

        /** 显示的日期数值 */
        // String value() default "";

        // /** 在其他组件中，时，用作变量映射 */
        // String name() default "";

        /** 占位内容 */
        String placeholder() default "";

        /** 展示格式, 更多格式类型请参考 文档 */
        String displayFormat() default "YYYY-MM-DD";

        /** 数据格式，默认为时间戳。更多格式类型请参考 文档 */
        String valueFormat() default "X";

        /** 是否显示相对当前的时间描述，比如: 11 小时前、3 天前、1 年前等，fromNow 为 true 时，format 不生效。 */
        boolean fromNow() default false;

        /** 更新频率， 默认为 1 分钟 */
        int updateFrequency() default 60000;

        /** 设置日期展示时区，可设置清单参考：https://gist.github.com/diogocapela/12c6617fc87607d11fd62d2a4f42b02a */
        String displayTimeZone() default "";
    }

    public static AmisDate change(Boolean required,Date annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisDate temp = new AmisDate(required,type, name, label,disabled,width,remark);
        // temp.setType(annotation.type());
        temp.setClassName(annotation.className());
        // temp.setValue(annotation.value());
        // temp.setName(annotation.name());
        temp.setPlaceholder(annotation.placeholder());
        temp.setDisplayFormat(annotation.displayFormat());
        temp.setValueFormat(annotation.valueFormat());
        temp.setFromNow(annotation.fromNow());
        temp.setUpdateFrequency(annotation.updateFrequency());
        temp.setDisplayTimeZone(annotation.displayTimeZone());
        return temp;
    }
}