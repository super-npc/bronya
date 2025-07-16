package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.constant.BulinkeModelConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputFile extends AmisComponents {

    public AmisInputFile(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 上传文件接口 */
    private String receiver;
    /** 默认只支持纯文本，要支持其他类型，请配置此属性为文件后缀.xxx */
    private String accept;
    // /** 用于控制 input[type=file] 标签的 capture 属性，在移动端可控制输入来源 */
    // private String capture;
    // /** 将文件以base64的形式，赋值给当前组件 */
    // private Boolean asBase64;
    // /** 将文件以二进制的形式，赋值给当前组件 */
    // private Boolean asBlob;
    /** 默认没有限制，当设置后，文件大小大于此值将不允许上传。单位为B */
    private Integer maxSize;
    /** 默认没有限制，当设置后，一次只允许上传指定数量文件。 */
    private Integer maxLength;
    /** 是否多选。 */
    private Boolean multiple;
    /** 是否为拖拽上传 */
    private Boolean drag;
    // /** 提取值 */
    // private Boolean extractValue;
    /** 否选择完就自动开始上传 */
    private Boolean autoUpload;
    /** 隐藏上传按钮 */
    private Boolean hideUploadButton;
    // /** 如果你不想自己存储，则可以忽略此属性。 */
    // private String fileField;
    // /** 接口返回哪个字段用来标识文件名 */
    // private String nameField;
    // /** 文件的值用那个字段来标识。 */
    // private String valueField;
    // /** 文件下载地址的字段名。 */
    // private String urlField;
    /** 上传按钮的文字 */
    private String btnLabel;
    /** amis 所在服务器，限制了文件上传大小不得超出 10M，所以 amis 在用户选择大文件的时候，自动会改成分块上传模式。 **/
    private Boolean useChunk = false;
    // /** startChunkApi */
    // private Object startChunkApi;
    // /** chunkApi */
    // private Object chunkApi;
    // /** finishChunkApi */
    // private Object finishChunkApi;
    /** 文档内容 */
    private String documentation;
    /** 文档链接 */
    private String documentLink;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputFile {
        /** 上传文件接口 */
        String receiver() default BulinkeModelConstant.UPLOAD_FILE_PATH;

        /** 默认只支持纯文本，要支持其他类型，请配置此属性为文件后缀.xxx */
        String accept() default "text/plain";

        // /** 用于控制 input[type=file] 标签的 capture 属性，在移动端可控制输入来源 */
        // String capture() default ;
        // /** 将文件以base64的形式，赋值给当前组件 */
        // boolean asBase64() default ;
        // /** 将文件以二进制的形式，赋值给当前组件 */
        // boolean asBlob() default ;
        /** 默认没有限制，当设置后，文件大小大于此值将不允许上传。单位为B */
        int maxSize() default 0;

        /** 默认没有限制，当设置后，一次只允许上传指定数量文件。 */
        int maxLength() default 0;

        /** 是否多选。 */
        boolean multiple() default false;

        /** 是否为拖拽上传 */
        boolean drag() default false;

        // /** 提取值 */
        // boolean extractValue() default ;
        /** 否选择完就自动开始上传 */
        boolean autoUpload() default true;

        /** 隐藏上传按钮 */
        boolean hideUploadButton() default false;

        // /** 如果你不想自己存储，则可以忽略此属性。 */
        // String fileField() default ;
        // /** 接口返回哪个字段用来标识文件名 */
        // String nameField() default ;
        // /** 文件的值用那个字段来标识。 */
        // String valueField() default ;
        // /** 文件下载地址的字段名。 */
        // String urlField() default ;
        /** 上传按钮的文字 */
        String btnLabel() default "文件上传";

        // /** startChunkApi */
        // Object startChunkApi() default "";
        // /** chunkApi */
        // Object chunkApi() default "";
        // /** finishChunkApi */
        // Object finishChunkApi() default "";
        /** 文档内容 */
        String documentation() default "";

        /** 文档链接 */
        String documentLink() default "";
    }

    public static AmisInputFile change(Boolean required,InputFile annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputFile temp = new AmisInputFile(required,type, name, label,disabled,width,remark);
        temp.setReceiver(annotation.receiver());
        temp.setAccept(annotation.accept());
        // temp.setCapture(annotation.capture());
        // temp.setAsBase64(annotation.asBase64());
        // temp.setAsBlob(annotation.asBlob());
        temp.setMaxSize(annotation.maxSize());
        temp.setMaxLength(annotation.maxLength());
        temp.setMultiple(annotation.multiple());
        temp.setDrag(annotation.drag());
        // temp.setExtractValue(annotation.extractValue());
        temp.setAutoUpload(annotation.autoUpload());
        temp.setHideUploadButton(annotation.hideUploadButton());
        // temp.setFileField(annotation.fileField());
        // temp.setNameField(annotation.nameField());
        // temp.setValueField(annotation.valueField());
        // temp.setUrlField(annotation.urlField());
        temp.setBtnLabel(annotation.btnLabel());
        // temp.setStartChunkApi(annotation.startChunkApi());
        // temp.setChunkApi(annotation.chunkApi());
        // temp.setFinishChunkApi(annotation.finishChunkApi());
        temp.setDocumentation(annotation.documentation());
        temp.setDocumentLink(annotation.documentLink());
        return temp;
    }
}