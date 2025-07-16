package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.card.CardMediaPosition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
public class AmisCard extends AmisComponents {

    public AmisCard(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /**
     * 外层 Dom 的类名
     */
    private String className;
    /**
     * 外部链接
     */
    private String href;
    /**
     * Card 头部内容设置
     */
    private CardHeader header;

    /**
     * 内容容器，主要用来放置非表单项组件
     */
    private Object body;
    /**
     * 内容区域类名
     */
    private String bodyClassName;
    /**
     * 配置按钮集合
     */
    private Object actions;
    /**
     * 按钮集合每行个数
     */
    private Integer actionsCount;
    // /** 点击卡片的行为 */
    private Object itemAction;
    /**
     * Card 多媒体部内容设置
     */
    private CardMedia media;
    /**
     * 次要说明
     */
    private Object secondary;
    /**
     * 工具栏按钮
     */
    private Object toolbar;
    /**
     * 是否显示拖拽图标
     */
    private Boolean dragging;
    /**
     * 卡片是否可选
     */
    private Boolean selectable;
    /**
     * 卡片选择按钮是否禁用
     */
    private Boolean checkable;
    /**
     * 卡片选择按钮是否选中
     */
    private Boolean selected;
    /**
     * 卡片选择按钮是否隐藏
     */
    private Boolean hideCheckToggler;
    /**
     * 卡片是否为多选
     */
    private Boolean multiple;
    /**
     * 卡片内容区的表单项 label 是否使用 Card 内部的样式
     */
    private Boolean useCardLabel;

    @Data
    @FieldNameConstants
    public static class CardMedia {
        /**
         * 多媒体类型
         */
        private String type;
        /**
         * 图片/视频链接
         */
        private String url;
        /**
         * 多媒体位置
         */
        private CardMediaPosition position;
        /**
         * 多媒体类名
         */
        private String className;
        /**
         * 视频是否为直播
         */
        private String isLive;
        /**
         * 视频是否自动播放
         */
        private String autoPlay;
        /**
         * 视频封面
         */
        private String poster;
    }

    @Data
    @FieldNameConstants
    public static class CardHeader {
        /**
         * 头部类名
         */
        private String className;
        /**
         * 标题
         */
        private String title;
        /**
         * 标题类名
         */
        private String titleClassName;
        /**
         * 副标题
         */
        private String subTitle;
        /**
         * 副标题类名
         */
        private String subTitleClassName;
        /**
         * 副标题占位
         */
        private String subTitlePlaceholder;
        /**
         * 描述
         */
        private String description;
        /**
         * 描述类名
         */
        private String descriptionClassName;
        /**
         * 描述占位
         */
        private String descriptionPlaceholder;
        /**
         * 图片
         */
        private Object avatar;
        /**
         * 图片包括层类名
         */
        private String avatarClassName;
        /**
         * 图片类名
         */
        private String imageClassName;
        /**
         * 如果不配置图片，则会在图片处显示该文本
         */
        private Object avatarText;
        /**
         * 设置文本背景色，它会根据数据分配一个颜色
         */
        private Object avatarTextBackground;
        /**
         * 图片文本类名
         */
        private String avatarTextClassName;
        /**
         * 是否显示激活样式
         */
        private Boolean highlight;
        /**
         * 激活样式类名
         */
        private String highlightClassName;
        /**
         * 点击卡片跳转的链接地址
         */
        private String href;
        /**
         * 是否新窗口打开
         */
        private Boolean blank;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Card {
        /**
         * 外层 Dom 的类名
         */
        String className() default "";

        /**
         * 外部链接
         */
        String href() default "";
        /** Card 头部内容设置 */
        // Object header() default "";
        // /** 头部类名 */
        // String className() default "";
        // /** 标题 */
        // Object title() default "";
        // /** 标题类名 */
        // String titleClassName() default "";
        // /** 副标题 */
        // Object subTitle() default "";
        // /** 副标题类名 */
        // String subTitleClassName() default "";
        // /** 副标题占位 */
        // String subTitlePlaceholder() default "";
        // /** 描述 */
        // Object description() default "";
        // /** 描述类名 */
        // String descriptionClassName() default "";
        // /** 描述占位 */
        // String descriptionPlaceholder() default "";
        // /** 图片 */
        // Object avatar() default "";
        // /** 图片包括层类名 */
        // String avatarClassName() default ;
        // /** 图片类名 */
        // String imageClassName() default "";
        // /** 如果不配置图片，则会在图片处显示该文本 */
        // Object avatarText() default "";
        // /** 设置文本背景色，它会根据数据分配一个颜色 */
        // Object avatarTextBackground() default "";
        // /** 图片文本类名 */
        // String avatarTextClassName() default "";
        // /** 是否显示激活样式 */
        // boolean highlight() default ;
        // /** 激活样式类名 */
        // String highlightClassName() default "";
        // /** 点击卡片跳转的链接地址 */
        // Object href() default "";
        // /** 是否新窗口打开 */
        // boolean blank() default ;
        // /** 内容容器，主要用来放置非表单项组件 */
        // Object body() default "";

        /**
         * 内容区域类名
         */
        String bodyClassName() default "";
        // /** 配置按钮集合 */
        // Object actions() default "";

        /**
         * 按钮集合每行个数
         */
        int actionsCount() default 4;
        /** 点击卡片的行为 */
        // Object itemAction() default "";
        /** Card 多媒体部内容设置 */
        // Object media() default "";
        // /** 多媒体类型 */
        // Object type() default "";
        // /** 图片/视频链接 */
        // String url() default "";
        // /** 多媒体位置 */
        // Object position() default ;
        // /** 多媒体类名 */
        // String className() default ;
        // /** 视频是否为直播 */
        // boolean isLive() default ;
        // /** 视频是否自动播放 */
        // boolean autoPlay() default ;
        // /** 视频封面 */
        // String poster() default ;
        // /** 次要说明 */
        // Object secondary() default "";
        // /** 工具栏按钮 */
        // Object toolbar() default "";

        /**
         * 是否显示拖拽图标
         */
        boolean dragging() default false;

        /**
         * 卡片是否可选
         */
        boolean selectable() default false;

        /**
         * 卡片选择按钮是否禁用
         */
        boolean checkable() default false;

        /**
         * 卡片选择按钮是否选中
         */
        boolean selected() default false;

        /**
         * 卡片选择按钮是否隐藏
         */
        boolean hideCheckToggler() default false;

        /**
         * 卡片是否为多选
         */
        boolean multiple() default false;

        /**
         * 卡片内容区的表单项 label 是否使用 Card 内部的样式
         */
        boolean useCardLabel() default true;
    }

    public static AmisCard change(Boolean required,Card annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisCard temp = new AmisCard(required,type, name, label,disabled,width,remark);
        temp.setClassName(annotation.className());
        temp.setHref(annotation.href());
        // temp.setHeader(annotation.header());
        // temp.setBody(annotation.body());
        temp.setBodyClassName(annotation.bodyClassName());
        // temp.setActions(annotation.actions());
        temp.setActionsCount(annotation.actionsCount());
        // temp.setItemAction(annotation.itemAction());
        // temp.setMedia(annotation.media());
        // temp.setSecondary(annotation.secondary());
        // temp.setToolbar(annotation.toolbar());
        temp.setDragging(annotation.dragging());
        temp.setSelectable(annotation.selectable());
        temp.setCheckable(annotation.checkable());
        temp.setSelected(annotation.selected());
        temp.setHideCheckToggler(annotation.hideCheckToggler());
        temp.setMultiple(annotation.multiple());
        temp.setUseCardLabel(annotation.useCardLabel());
        return temp;
    }
}