package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;
import java.util.List;

import org.dromara.hutool.http.meta.Method;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.AmisApi;
import bronya.core.base.annotation.amis.type.timeline.TimeDirection;
import bronya.core.base.annotation.amis.type.timeline.TimelineMode;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AmisTimeline extends AmisComponents {

    public AmisTimeline(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /**
     * 配置节点数据
     */
    private List<AmisTimelineItem> items;
    /**
     * 数据源，可通过数据映射获取当前数据域变量、或者配置 API 对象
     */
    private AmisApi source;
    /**
     * 指定文字相对于时间轴的位置，仅 direction=vertical 时支持
     */
    private TimelineMode mode;
    /**
     * 时间轴方向
     */
    private TimeDirection direction;
    /**
     * 根据时间倒序显示
     */
    private Boolean reverse;

    @Data
    @RequiredArgsConstructor
    public static class AmisTimelineItem {
        /**
         * 节点时间
         */
        private final String time;
        /**
         * 节点标题
         */
        private final String title;
        /**
         * 节点详细描述（折叠）
         */
        private String detail;
        /**
         * 详细内容折叠时按钮文案
         */
        private String detailCollapsedText;
        /**
         * 详细内容展开时按钮文案
         */
        private String detailExpandedText;
        /**
         * 时间轴节点颜色
         */
        private String color;
        /**
         * icon 名，支持 fontawesome v4 或使用 url（优先级高于 color）
         */
        private String icon;
        /**
         * 节点图标的 CSS 类名（优先级高于统一配置的 iconClassName ，（3.4.0 版本支持））
         */
        private String iconClassName;
        /**
         * 节点时间的 CSS 类名（优先级高于统一配置的 timeClassName，（3.4.0 版本支持））
         */
        private String timeClassName;
        /**
         * 节点标题的 CSS 类名（优先级高于统一配置的 titleClassName，（3.4.0 版本支持））
         */
        private String titleClassName;
        /**
         * 节点详情的 CSS 类名（优先级高于统一配置的 detailClassName，（3.4.0 版本支持））
         */
        private String detailClassName;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Timeline {
        /**
         * 指定文字相对于时间轴的位置，仅 direction=vertical 时支持
         */
        TimelineMode mode() default TimelineMode.left;

        /**
         * 时间轴方向
         */
        TimeDirection direction() default TimeDirection.vertical;

        /**
         * 根据时间倒序显示
         */
        boolean reverse() default false;

        UrlConf urlConf() default @UrlConf();

        @interface UrlConf {
            String getUrl() default "";
        }
    }

    public static AmisTimeline change(Boolean required,Timeline annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisTimeline temp = new AmisTimeline(required,type, name, label,disabled,width,remark);
        temp.setMode(annotation.mode());
        temp.setDirection(annotation.direction());
        temp.setReverse(annotation.reverse());
        temp.setSource(new AmisApi(Method.GET, annotation.urlConf().getUrl()));
        return temp;
    }
}