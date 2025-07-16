package bronya.core.base.annotation.amis.inputdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.constant.BulinkeModelConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisInputImage extends AmisComponents {

    public AmisInputImage(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 上传文件接口 */
    private String receiver = BulinkeModelConstant.UPLOAD_FILE_PATH;
    /** 支持的图片类型格式，请配置此属性为图片后缀，例如.jpg,.png */
    private String accept;
    /** 用于控制 input[type=file] 标签的 capture 属性，在移动端可控制输入来源 */
    private String capture;
    /** 默认没有限制，当设置后，文件大小大于此值将不允许上传。单位为B */
    private Integer maxSize;
    /** 默认没有限制，当设置后，一次只允许上传指定数量文件。 */
    private Integer maxLength;
    /** 是否多选。 */
    private Boolean multiple;
    /** 拼接值 */
    private Boolean joinValues;
    /** 提取值 */
    private Boolean extractValue;
    /** 拼接符 */
    private String delimiter;
    /** 否选择完就自动开始上传 */
    private Boolean autoUpload;
    /** 隐藏上传按钮 */
    private Boolean hideUploadButton;
    /** 如果你不想自己存储，则可以忽略此属性。 */
    private String fileField;
    /** 用来设置是否支持裁剪。 */
    private Boolean crop;
    // /** 裁剪比例。浮点型，默认 1 即 1:1，如果要设置 16:9 请设置 1.7777777777777777 即 16 / 9。。 */
    // private Integer crop.aspectRatio;
    // /** 裁剪时是否可旋转 */
    // private Boolean crop.rotatable;
    // /** 裁剪时是否可缩放 */
    // private Boolean crop.scalable;
    // /** 裁剪时的查看模式，0 是无限制 */
    // private Integer crop.viewMode;
    /** 裁剪文件格式 */
    private String cropFormat;
    /** 裁剪文件格式的质量，用于 jpeg/webp，取值在 0 和 1 之间 */
    private Integer cropQuality;
    /** 限制图片大小，超出不让上传。 */
    private Object limit;
    /** 默认占位图地址 */
    private String frameImage;
    /** 是否开启固定尺寸,若开启，需同时设置 fixedSizeClassName */
    private Boolean fixedSize;
    /** 开启固定尺寸时，根据此值控制展示尺寸。例如h-30,即图片框高为 h-30,AMIS 将自动缩放比率设置默认图所占位置的宽度，最终上传图片根据此尺寸对应缩放。 */
    private String fixedSizeClassName;
    /** 表单反显时是否执行 autoFill */
    private Boolean initAutoFill;
    /** 上传按钮文案。支持 tpl、schema 形式配置。 */
    private Object uploadBtnText;
    /** 图片上传后是否进入裁剪模式 */
    private Boolean dropCrop;
    /** 图片选择器初始化后是否立即进入裁剪模式 */
    private Boolean initCrop;
    /** 开启后支持拖拽排序改变图片值顺序 */
    private Boolean draggable;
    /** 拖拽提示文案 */
    private String draggableTip;


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface InputImage {
        // /** 上传文件接口 */
        // AmisApi receiver() default "";
        /** 支持的图片类型格式，请配置此属性为图片后缀，例如.jpg,.png */
        String accept() default ".jpeg,.jpg,.png,.gif";

        /** 用于控制 input[type=file] 标签的 capture 属性，在移动端可控制输入来源 */
        // String capture() default "undefined";
        //
        // /** 默认没有限制，当设置后，文件大小大于此值将不允许上传。单位为B */
        // int maxSize() default 0;
        //
        // /** 默认没有限制，当设置后，一次只允许上传指定数量文件。 */
        // int maxLength() default 0;
        //
        // /** 是否多选。 */
        // boolean multiple() default false;
        //
        // /** 拼接值 */
        // boolean joinValues() default true;
        //
        // /** 提取值 */
        // boolean extractValue() default true;
        //
        // /** 拼接符 */
        // String delimiter() default ",";

        // /** 否选择完就自动开始上传 */
        // boolean autoUpload() default true;

        /** 隐藏上传按钮 */
        boolean hideUploadButton() default false;

        /** 如果你不想自己存储，则可以忽略此属性。 */
        String fileField() default "file";

        /** 用来设置是否支持裁剪。 */
        boolean crop() default true;

        // /** 裁剪比例。浮点型，默认 1 即 1:1，如果要设置 16:9 请设置 1.7777777777777777 即 16 / 9。。 */
        // int crop.aspectRatio() default 0;
        // /** 裁剪时是否可旋转 */
        // boolean crop.rotatable() default ;
        // /** 裁剪时是否可缩放 */
        // boolean crop.scalable() default ;
        // /** 裁剪时的查看模式，0 是无限制 */
        // int crop.viewMode() default ;
        /** 裁剪文件格式 */
        String cropFormat() default "image/png";

        /** 裁剪文件格式的质量，用于 jpeg/webp，取值在 0 和 1 之间 */
        int cropQuality() default 1;

        // /** 限制图片大小，超出不让上传。 */
        // Object limit() default "";
        /** 默认占位图地址 */
        String frameImage() default "";

        // /** 是否开启固定尺寸,若开启，需同时设置 fixedSizeClassName */
        // boolean fixedSize() default false;
        //
        // /** 开启固定尺寸时，根据此值控制展示尺寸。例如h-30,即图片框高为 h-30,AMIS 将自动缩放比率设置默认图所占位置的宽度，最终上传图片根据此尺寸对应缩放。 */
        // String fixedSizeClassName() default "";
        //
        // /** 表单反显时是否执行 autoFill */
        // boolean initAutoFill() default false;
        //
        // // /** 上传按钮文案。支持 tpl、schema 形式配置。 */
        // // Object uploadBtnText() default "";
        // /** 图片上传后是否进入裁剪模式 */
        // boolean dropCrop() default true;
        //
        // /** 图片选择器初始化后是否立即进入裁剪模式 */
        // boolean initCrop() default false;
        //
        // /** 开启后支持拖拽排序改变图片值顺序 */
        // boolean draggable() default true;
        //
        // /** 拖拽提示文案 */
        // String draggableTip() default "拖拽排序";
    }

    public static AmisInputImage change(Boolean required,InputImage annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisInputImage temp = new AmisInputImage(required,type, name, label,disabled,width,remark);
        // temp.setReceiver(annotation.receiver());
        temp.setAccept(annotation.accept());
        // temp.setCapture(annotation.capture());
        // temp.setMaxSize(annotation.maxSize());
        // temp.setMaxLength(annotation.maxLength());
        // temp.setMultiple(annotation.multiple());
        // temp.setJoinValues(annotation.joinValues());
        // temp.setExtractValue(annotation.extractValue());
        // temp.setDelimiter(annotation.delimiter());
        // temp.setAutoUpload(annotation.autoUpload());
        temp.setHideUploadButton(annotation.hideUploadButton());
        temp.setFileField(annotation.fileField());
        temp.setCrop(annotation.crop());
        // temp.setCrop.aspectRatio(annotation.crop.aspectRatio());
        // temp.setCrop.rotatable(annotation.crop.rotatable());
        // temp.setCrop.scalable(annotation.crop.scalable());
        // temp.setCrop.viewMode(annotation.crop.viewMode());
        temp.setCropFormat(annotation.cropFormat());
        temp.setCropQuality(annotation.cropQuality());
        // temp.setLimit(annotation.limit());
        temp.setFrameImage(annotation.frameImage());
        // temp.setFixedSize(annotation.fixedSize());
        // temp.setFixedSizeClassName(annotation.fixedSizeClassName());
        // temp.setInitAutoFill(annotation.initAutoFill());
        // temp.setUploadBtnText(annotation.uploadBtnText());
        // temp.setDropCrop(annotation.dropCrop());
        // temp.setInitCrop(annotation.initCrop());
        // temp.setDraggable(annotation.draggable());
        // temp.setDraggableTip(annotation.draggableTip());
        return temp;
    }
}