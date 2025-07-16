package bronya.core.base.annotation.amis;

import lombok.AllArgsConstructor;
import lombok.Getter;

public @interface AmisFieldView {
    /**
     * 重复指定标题,则覆盖父类title
     */
    String title() default "";

    ViewType type() default ViewType.AUTO;

    String className() default "";

    String visibleOn() default "";

    boolean disabled() default false;


    enum FixedType {
        left, right, none
    }

    @Getter
    @AllArgsConstructor
    enum ViewType {
        AUTO(AmisComponentsRegister.staticText, AmisComponentsRegister.inputText),

        隐藏(AmisComponentsRegister.hidden, AmisComponentsRegister.hidden),

        文本(AmisComponentsRegister.staticText, AmisComponentsRegister.inputText),

        验证码(AmisComponentsRegister.staticText, AmisComponentsRegister.inputVerificationCode),

        文本域(AmisComponentsRegister.textarea, AmisComponentsRegister.textarea),

        富文本(AmisComponentsRegister.hidden, AmisComponentsRegister.inputRichText),

        数字(AmisComponentsRegister.staticText, AmisComponentsRegister.inputNumber),

        日期date(AmisComponentsRegister.staticDate, AmisComponentsRegister.inputDate),

        日期dateTime(AmisComponentsRegister.staticDatetime, AmisComponentsRegister.inputDateTime),

        日期month(AmisComponentsRegister.staticDate, AmisComponentsRegister.inputMonth),

        日期monthRange(AmisComponentsRegister.staticText, AmisComponentsRegister.inputMonthRange),

        单选(AmisComponentsRegister.staticMapping, AmisComponentsRegister.radios),

        下拉框(AmisComponentsRegister.staticText, AmisComponentsRegister.select),



        开关(AmisComponentsRegister.switchBool, AmisComponentsRegister.switchBool),

        按钮点选(AmisComponentsRegister.staticText, AmisComponentsRegister.buttonGroupSelect),

        复选框(AmisComponentsRegister.staticText, AmisComponentsRegister.checkboxes),

        上传图片(AmisComponentsRegister.staticImage, AmisComponentsRegister.inputImage),

        上传文件(AmisComponentsRegister.staticText, AmisComponentsRegister.inputFile),

        评分(AmisComponentsRegister.inputRating, AmisComponentsRegister.inputRating),

        穿梭器(AmisComponentsRegister.hidden, AmisComponentsRegister.transferPicker),

        jsonSchema(AmisComponentsRegister.json, AmisComponentsRegister.jsonSchema),

        滑块(AmisComponentsRegister.progress, AmisComponentsRegister.inputRange),

        实时日志(AmisComponentsRegister.hidden, AmisComponentsRegister.log),

        链接(AmisComponentsRegister.link, AmisComponentsRegister.link),

        分割线(AmisComponentsRegister.divider, AmisComponentsRegister.divider),

        颜色选择器(AmisComponentsRegister.color, AmisComponentsRegister.inputColor),

        代码编辑器(AmisComponentsRegister.editor, AmisComponentsRegister.editor),
        代码编辑器Diff(AmisComponentsRegister.editorDiff, AmisComponentsRegister.editorDiff),

        组合条件(AmisComponentsRegister.conditionBuilder, AmisComponentsRegister.conditionBuilder),

        markdown(AmisComponentsRegister.markdown, AmisComponentsRegister.editor),

        时间轴(AmisComponentsRegister.timeline, AmisComponentsRegister.timeline),

        视频(AmisComponentsRegister.video, AmisComponentsRegister.video),

        走势图(AmisComponentsRegister.sparkline, AmisComponentsRegister.hidden),

        // 数据展示

        // selectByRemote, TransferByRemote, markdown, 自适配, 单选, 数字, 文本域, 富文本, 音频, 隐藏, 树,
        // 文件, 图片, 轮播图, 日期, 进度, 开关, 密码, 标签, 色盘, jpa枚举, 列表, 链接,
        // /**
        // * 代码编辑器
        // */
        // editor;
        ;

        private final AmisComponentsRegister tableDetail;
        private final AmisComponentsRegister addEdit;
    }

}