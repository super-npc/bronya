package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.json.JsonTheme;
import bronya.core.base.annotation.amis.type.log.LogOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisJson extends AmisComponents {

    public AmisJson(Boolean required, String type, String name, String label, Boolean disabled, String width,
        String remark) {
        super(required, type, name, label, disabled, width, remark);
    }

    /**
     * 外层 CSS 类名
     */
    private String className;
    /**
     * 接口
     */
    private String source;
    /**
     * 占位文本
     */
    private String placeholder;
    /**
     * 默认展开的层级
     */
    private Integer levelExpand;

    private JsonTheme jsonTheme;
    /**
     * 是否可修改
     */
    private Boolean mutable;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Json {

        /**
         * 外层 CSS 类名
         */
        String className() default "";

        /**
         * 加载中的文字
         */
        String placeholder() default "请稍等..";

        /**
         * 接口
         */
        String source() default "";

        JsonTheme jsonTheme() default JsonTheme.twilight;

        /**
         * 是否显示数据类型
         */
        boolean displayDataTypes() default false;

        /**
         * 是否可修改
         */
        boolean mutable() default false;

        /**
         * 默认展开的层级
         */
        int levelExpand() default 1;

        // /** fetch 的 credentials 设置 */
        // String credentials() default "include";
        // /** 设置每行高度，将会开启虚拟渲染 */
        // int rowHeight() default 0;
        // /** 最大显示行数 */
        // int maxLength() default 0;

        /**
         * 可选日志操作：['stop','restart',clear','showLineNumber','filter']
         */
        LogOperation[] operation() default {LogOperation.restart, LogOperation.stop, LogOperation.clear,
            LogOperation.showLineNumber, LogOperation.filter};
    }

    public static AmisJson change(Boolean required, Json annotation, String type, String name, String label,
        Boolean disabled, String width, String remark) {
        AmisJson temp = new AmisJson(required, type, name, label, disabled, width, remark);
        temp.setPlaceholder(annotation.placeholder());
        temp.setJsonTheme(annotation.jsonTheme());
        temp.setMutable(annotation.mutable());
        temp.setLevelExpand(annotation.levelExpand());
        temp.setClassName(annotation.className());
        temp.setPlaceholder(annotation.placeholder());
        temp.setSource(annotation.source());
        return temp;
    }
}