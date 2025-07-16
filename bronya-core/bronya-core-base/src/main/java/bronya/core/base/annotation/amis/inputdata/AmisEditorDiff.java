package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisEditorDiff extends AmisComponents {

    public AmisEditorDiff(Boolean required, String type, String name, String label, Boolean disabled,String width,String remark) {
        super(required, type, name, label, disabled,width,remark);
    }

    /** 编辑器高亮的语言 */
    private EditorLanguage language;
    /** 编辑器高度，取值可以是 md、lg、xl、xxl */
    private String diffValue;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface EditorDiff {
        /** 编辑器高亮的语言 */
        EditorLanguage language() default EditorLanguage.javascript;

        /**
         * 左值名称, 会覆盖name属性
         */
        String sourceValueName() default "";

        /**
         * 右值
         */
        String diffValue() default "";
    }

    public static AmisEditorDiff change(Boolean required, EditorDiff annotation, String type, String name, String label,
        Boolean disabled,String width,String remark) {
        AmisEditorDiff temp = new AmisEditorDiff(required, type, name, label, disabled,width,remark);
        temp.setLanguage(annotation.language());
        temp.setName(annotation.sourceValueName());
        temp.setDiffValue(annotation.diffValue());
        return temp;
    }
}