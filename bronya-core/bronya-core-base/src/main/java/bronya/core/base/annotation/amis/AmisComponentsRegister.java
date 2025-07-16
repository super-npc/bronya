package bronya.core.base.annotation.amis;


import bronya.core.base.annotation.amis.inputdata.*;
import bronya.core.base.annotation.amis.showdata.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据展示,建立关系映射,是需要将字段上的注解 annotationClass 属性值赋值到javaClass并将序列化为json输出
 * {@link AmisFieldJavaInfo#getComponents(java.lang.Class)}
 * <p>
 * {@link AmisFieldView.ViewType}
 * <p>
 * {@link AmisField}
 */
@Getter
@AllArgsConstructor
public enum AmisComponentsRegister {
    inputText("input-text", AmisInputText.class, AmisInputText.InputText.class),
    textarea("textarea", AmisTextarea.class, AmisTextarea.Textarea.class),
    inputRichText("input-rich-text", AmisInputRichText.class, AmisInputRichText.InputRichText.class),
    inputDate("input-date", AmisInputDate.class, AmisInputDate.InputDate.class),
    inputColor("input-color", AmisInputColor.class, AmisInputColor.InputColor.class),
    inputDateTime("input-datetime", AmisInputDateTime.class, AmisInputDateTime.InputDateTime.class),
    inputMonth("input-month", AmisInputMonth.class, AmisInputMonth.InputMonth.class),
    inputMonthRange("input-month-range", AmisInputMonthRange.class, AmisInputMonthRange.InputMonthRange.class),
    inputNumber("input-number", AmisInputNumber.class, AmisInputNumber.InputNumber.class),
    radios("radios", AmisRadios.class, AmisRadios.Radios.class),
    select("select", AmisSelect.class, AmisSelect.Select.class),
    picker("picker", AmisPicker.class, AmisPicker.Picker.class),
    switchBool("switch", AmisSwitch.class, AmisSwitch.Switch.class),
    inputImage("input-image", AmisInputImage.class, AmisInputImage.InputImage.class),
    inputFile("input-file", AmisInputFile.class, AmisInputFile.InputFile.class),
    inputVerificationCode("input-verification-code", AmisInputVerificationCode.class,
            AmisInputVerificationCode.InputVerificationCode.class),

    buttonGroupSelect("button-group-select", AmisButtonGroupSelect.class,
            AmisButtonGroupSelect.ButtonGroupSelect.class),
    /**
     * 条件组合
     */
    conditionBuilder("condition-builder", AmisConditionBuilder.class, AmisConditionBuilder.ConditionBuilder.class),
    /**
     * 复选框
     */
    checkboxes("checkboxes", AmisCheckboxes.class, AmisCheckboxes.Checkboxes.class),
    inputRating("input-rating", AmisInputRating.class, AmisInputRating.InputRating.class),
    inputRange("input-range", AmisInputRange.class, AmisInputRange.InputRange.class),
    transferPicker("transfer-picker", AmisTransferPicker.class, AmisTransferPicker.TransferPicker.class),
    jsonSchema("json-schema", AmisJsonSchema.class, AmisJsonSchema.JsonSchema.class),
    editor("editor", AmisEditor.class, AmisEditor.Editor.class),
    editorDiff("diff-editor", AmisEditorDiff.class, AmisEditorDiff.EditorDiff.class),

    // 数据展示,
    date("date", AmisDate.class, AmisDate.Date.class),
    json("json", AmisJson.class, AmisJson.Json.class),
    staticText("static", AmisStaticText.class, AmisStaticText.StaticText.class),
    staticDate("static-date", AmisStaticDate.class, AmisStaticDate.StaticDate.class),
    staticDatetime("static-datetime", AmisStaticDatetime.class, AmisStaticDatetime.StaticDatetime.class),
    staticImage("static-image", AmisStaticImage.class, AmisStaticImage.StaticImage.class),
    staticMapping("static-mapping", AmisStaticMapping.class, AmisStaticMapping.StaticMapping.class),
    staticTag("tag", AmisTag.class, AmisTag.Tag.class), hidden("hidden", AmisHidden.class, AmisHidden.Hidden.class),
    progress("progress", AmisProgress.class, AmisProgress.Progress.class), log("log", AmisLog.class, AmisLog.Log.class),
    link("link", AmisLink.class, AmisLink.Link.class), divider("divider", AmisDivider.class, AmisDivider.Divider.class),
    color("color", AmisColor.class, AmisColor.Color.class), html("html", AmisHtml.class, AmisHtml.Html.class),
    markdown("markdown", AmisMarkdown.class, AmisMarkdown.Markdown.class),
    timeline("timeline", AmisTimeline.class, AmisTimeline.Timeline.class),
    video("video", AmisVideo.class, AmisVideo.Video.class),
    sparkline("sparkline", AmisSparkline.class, AmisSparkline.Sparkline.class);

    private final String componentsName;
    private final Class<?> javaClass;
    private final Class<?> amisAnnotationClass;
}
