package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputRichText extends AmisComponents {

    public AmisInputRichText(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 是否保存为 ubb 格式 */
    private Boolean saveAsUbb;
    /** 默认的图片保存 API */
    private AmisApi receiver;
    // /** 默认的视频保存 API 仅支持 froala 编辑器 */
    // private Object videoReceiver;
    /** 上传文件时的字段名 */
    private String fileField;
    /** 框的大小，可设置为 md 或者 lg */
    private String size;
    // /** 需要参考 tinymce 或 froala 的文档 */
    // private Object options;
    // /** froala 专用，配置显示的按钮，tinymce 可以通过前面的 options 设置 toolbar 字符串 */
    // private Object buttons;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputRichText {
        /** 是否保存为 ubb 格式 */
        boolean saveAsUbb() default true;

        // /** 默认的图片保存 API */
        // Object receiver() default "";
        //
        // /** 默认的视频保存 API 仅支持 froala 编辑器 */
        // Object videoReceiver() default "";

        /** 上传文件时的字段名 */
        String fileField() default "";

        /** 框的大小，可设置为 md 或者 lg */
        String size() default "";

        // /** 需要参考 tinymce 或 froala 的文档 */
        // Object options() default "";

        // /** froala 专用，配置显示的按钮，tinymce 可以通过前面的 options 设置 toolbar 字符串 */
        // Object buttons() default "";
    }

    public static AmisInputRichText change(Boolean required,InputRichText annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputRichText temp = new AmisInputRichText(required,type, name, label,disabled,width,remark);
        temp.setSaveAsUbb(annotation.saveAsUbb());
        // temp.setReceiver(annotation.receiver());
        // temp.setVideoReceiver(annotation.videoReceiver());
        temp.setFileField(annotation.fileField());
        temp.setSize(annotation.size());
        // temp.setOptions(annotation.options());
        // temp.setButtons(annotation.buttons());
        return temp;
    }
}