package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.log.LogOperation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisLog extends AmisComponents {

    public AmisLog(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 展示区域高度 */
    private Integer height;
    /** 外层 CSS 类名 */
    private String className;
    /** 是否自动滚动 */
    private Boolean autoScroll;
    /** 是否禁用 ansi 颜色支持 */
    private Boolean disableColor;
    /** 加载中的文字 */
    private String placeholder;
    /** 返回内容的字符编码 */
    private String encoding;
    /** 接口 */
    private String source;
    /** fetch 的 credentials 设置 */
    private String credentials;
    /** 设置每行高度，将会开启虚拟渲染 */
    private Integer rowHeight;
    /** 最大显示行数 */
    private Integer maxLength;
    /** 可选日志操作：['stop','restart',clear','showLineNumber','filter'] */
    private LogOperation[] operation;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Log {
        /** 展示区域高度 */
        int height() default 500;

        /** 外层 CSS 类名 */
        String className() default "";

        /** 是否自动滚动 */
        boolean autoScroll() default true;

        /** 是否禁用 ansi 颜色支持 */
        boolean disableColor() default false;

        /** 加载中的文字 */
        String placeholder() default "请稍等..";

        /** 返回内容的字符编码 */
        String encoding() default "utf-8";

        /** 接口 */
        String source() default "";

        // /** fetch 的 credentials 设置 */
        // String credentials() default "include";
        // /** 设置每行高度，将会开启虚拟渲染 */
        // int rowHeight() default 0;
        // /** 最大显示行数 */
        // int maxLength() default 0;
        /** 可选日志操作：['stop','restart',clear','showLineNumber','filter'] */
        LogOperation[] operation() default {LogOperation.restart, LogOperation.stop, LogOperation.clear,
            LogOperation.showLineNumber, LogOperation.filter};
    }

    public static AmisLog change(Boolean required,Log annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisLog temp = new AmisLog(required,type, name, label,disabled,width,remark);
        temp.setHeight(annotation.height());
        temp.setClassName(annotation.className());
        temp.setAutoScroll(annotation.autoScroll());
        temp.setDisableColor(annotation.disableColor());
        temp.setPlaceholder(annotation.placeholder());
        temp.setEncoding(annotation.encoding());
        temp.setSource(annotation.source());
        // temp.setCredentials(annotation.credentials());
        // temp.setRowHeight(annotation.rowHeight());
        // temp.setMaxLength(annotation.maxLength());
        temp.setOperation(annotation.operation());
        return temp;
    }
}