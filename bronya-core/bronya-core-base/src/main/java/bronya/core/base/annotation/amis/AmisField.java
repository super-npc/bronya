package bronya.core.base.annotation.amis;

import java.beans.Transient;
import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisFieldView.FixedType;
import bronya.core.base.annotation.amis.inputdata.*;
import bronya.core.base.annotation.amis.showdata.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AmisField {
    String comment() default "";

    AmisInputText.InputText inputText() default @AmisInputText.InputText();

    AmisInputRichText.InputRichText inputRichText() default @AmisInputRichText.InputRichText();

    AmisHtml.Html html() default @AmisHtml.Html();

    AmisInputDate.InputDate inputDate() default @AmisInputDate.InputDate();

    AmisInputDateTime.InputDateTime inputDateTime() default @AmisInputDateTime.InputDateTime();

    AmisInputMonth.InputMonth inputMonth() default @AmisInputMonth.InputMonth();

    AmisInputMonthRange.InputMonthRange inputMonthRange() default @AmisInputMonthRange.InputMonthRange();

    AmisInputNumber.InputNumber inputNumber() default @AmisInputNumber.InputNumber();

    AmisRadios.Radios radios() default @AmisRadios.Radios();

    AmisSelect.Select select() default @AmisSelect.Select();

    AmisPicker.Picker picker() default @AmisPicker.Picker();

    AmisSwitch.Switch switchBool() default @AmisSwitch.Switch();

    AmisTextarea.Textarea textarea() default @AmisTextarea.Textarea();

    AmisButtonGroupSelect.ButtonGroupSelect buttonGroupSelect() default @AmisButtonGroupSelect.ButtonGroupSelect();

    AmisDate.Date date() default @AmisDate.Date();

    AmisInputColor.InputColor inputColor() default @AmisInputColor.InputColor();

    AmisColor.Color color() default @AmisColor.Color();

    AmisStaticText.StaticText staticText() default @AmisStaticText.StaticText();

    AmisStaticDate.StaticDate staticDate() default @AmisStaticDate.StaticDate();

    AmisStaticDatetime.StaticDatetime staticDatetime() default @AmisStaticDatetime.StaticDatetime();

    AmisStaticImage.StaticImage staticImage() default @AmisStaticImage.StaticImage();

    AmisStaticMapping.StaticMapping staticMapping() default @AmisStaticMapping.StaticMapping();

    AmisTag.Tag staticTag() default @AmisTag.Tag();

    AmisJsonSchema.JsonSchema jsonSchema() default @AmisJsonSchema.JsonSchema();

    AmisHidden.Hidden hidden() default @AmisHidden.Hidden();

    AmisProgress.Progress progress() default @AmisProgress.Progress();

    AmisLog.Log log() default @AmisLog.Log();

    AmisLink.Link link() default @AmisLink.Link();

    AmisEditor.Editor editor() default @AmisEditor.Editor();

    AmisEditorDiff.EditorDiff editorDiff() default @AmisEditorDiff.EditorDiff();

    AmisCheckboxes.Checkboxes checkboxes() default @AmisCheckboxes.Checkboxes();

    AmisInputImage.InputImage inputImage() default @AmisInputImage.InputImage();

    AmisInputFile.InputFile inputFile() default @AmisInputFile.InputFile();

    AmisInputVerificationCode.InputVerificationCode inputVerificationCode() default @AmisInputVerificationCode.InputVerificationCode();

    AmisInputRating.InputRating inputRating() default @AmisInputRating.InputRating();

    AmisInputRange.InputRange inputRange() default @AmisInputRange.InputRange();

    AmisTransferPicker.TransferPicker transferPicker() default @AmisTransferPicker.TransferPicker();

    AmisDivider.Divider divider() default @AmisDivider.Divider();

    AmisConditionBuilder.ConditionBuilder conditionBuilder() default @AmisConditionBuilder.ConditionBuilder();

    AmisMarkdown.Markdown markdown() default @AmisMarkdown.Markdown();

    AmisTimeline.Timeline timeline() default @AmisTimeline.Timeline();

    AmisVideo.Video video() default @AmisVideo.Video();

    AmisSparkline.Sparkline sparkline() default @AmisSparkline.Sparkline();

    AmisJson.Json json() default @AmisJson.Json();

    AmisFieldView table() default @AmisFieldView();

    AmisFieldView edit() default @AmisFieldView();

    AmisFieldView add() default @AmisFieldView();

    AmisFieldView detail() default @AmisFieldView();

    /**
     * 表格内容渲染使用static,也有一些特殊需求,例如textarea不需要静态
     */
    boolean tableStatic() default true;

    String remark() default "";

    /**
     * 系统环境变量值
     */
    String envValue() default "";

    /**
     * amis页面默认查询
     */
    String pageDefaultParam() default "";

    SecureType secure() default SecureType.none;

    boolean search() default false;

    boolean quickEdit() default false;

    FixedType fixed() default FixedType.none;

    /**
     * 显示列
     */
    boolean toggled() default true;

    boolean batchUpdate() default false;

    boolean batchDel() default false;

    /**
     * 敏感数据,例如:密码 需要脱敏
     */
    Sensitive sensitive() default @Sensitive();

    @Transient
    // 显示顺序，默认按照字段排列顺序排序
    int sort() default 0;

    int width() default -1;

    /**
     * 只有数据库对象才支持,拓展对象不支持排序
     */
    boolean sortable() default false;

    boolean copyable() default false;

    @interface Sensitive {
        SensitiveType type() default SensitiveType.none;
    }

    enum SensitiveType {
        none, yes
    }

    enum SecureType {
        none, md5, aes
    }
}
