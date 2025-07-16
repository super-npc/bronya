package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.core.base.annotation.amis.type.editor.EditorSize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisEditor extends AmisComponents {

    public AmisEditor(Boolean required, String type, String name, String label, Boolean disabled, String width,
        String remark) {
        super(required, type, name, label, disabled, width, remark);
    }

    /** 编辑器高亮的语言 */
    private EditorLanguage language;
    /** 编辑器高度，取值可以是 md、lg、xl、xxl */
    private EditorSize size;
    /** 是否显示全屏模式开关 */
    private Boolean allowFullscreen;
    /** monaco 编辑器的其它配置，比如是否显示行号等，请参考这里，不过无法设置 readOnly，只读模式需要使用 disabled: true */
    private Map<String, Object> options;
    /** 占位描述，没有值的时候展示 */
    private String placeholder;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Editor {
        /** 编辑器高亮的语言 */
        EditorLanguage language() default EditorLanguage.javascript;

        /** 编辑器高度，取值可以是 md、lg、xl、xxl */
        EditorSize size() default EditorSize.md;

        /** 是否显示全屏模式开关 */
        boolean allowFullscreen() default true;

        /** monaco 编辑器的其它配置，比如是否显示行号等，请参考这里，不过无法设置 readOnly，只读模式需要使用 disabled: true */
        EditorOption options() default @EditorOption();

        /** 占位描述，没有值的时候展示 */
        String placeholder() default "";

        /**
         * 是否为md渲染
         */
        boolean markdownRender() default false;
    }

    // option参数参考 https://microsoft.github.io/monaco-editor/docs.html#interfaces/editor.IEditorOptions.html#lineNumbers
    public @interface EditorOption {
        String lineNumbers() default "off";

        // 自动布局
        boolean automaticLayout() default true;
    }

    public static AmisEditor change(Boolean required, Editor annotation, String type, String name, String label,
        Boolean disabled, String width, String remark) {
        AmisEditor temp = new AmisEditor(required, type, name, label, disabled, width, remark);
        temp.setLanguage(annotation.language());
        temp.setSize(annotation.size());
        temp.setAllowFullscreen(annotation.allowFullscreen());
        temp.setPlaceholder(annotation.placeholder());

        EditorOption options = annotation.options();
        HashMap<String, Object> optionsMap = Maps.newHashMap();
        optionsMap.put("lineNumbers", options.lineNumbers());
        optionsMap.put("automaticLayout", options.automaticLayout());
        temp.setOptions(optionsMap);
        return temp;
    }
}