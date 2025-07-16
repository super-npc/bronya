package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;
import java.util.List;
import java.util.Map;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.video.VideoType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisVideo extends AmisComponents {

    public AmisVideo(Boolean required, String type, String name, String label, Boolean disabled, String width,
        String remark) {
        super(required, type, name, label, disabled, width, remark);
    }

    /** 外层 Dom 的类名 */
    private String className;
    /** 视频地址 */
    private String src;
    /** 是否为直播，视频为直播时需要添加上，支持flv和hls格式 */
    private Boolean isLive;
    /** 指定直播视频格式 */
    private String videoType;
    /** 视频封面地址 */
    private String poster;
    /** 是否静音 */
    private Boolean muted;
    /** 是否循环播放 */
    private Boolean loop;
    /** 是否自动播放 */
    private Boolean autoPlay;
    /** 倍数，格式为[1.0, 1.5, 2.0] */
    private List<Double> rates;
    /** key 是时刻信息，value 可以可以为空，可有设置为图片地址，请看上方示例 */
    private Map<String, String> frames;
    /** 点击帧的时候默认是跳转到对应的时刻，如果想提前 3 秒钟，可以设置这个值为 3 */
    private Boolean jumpBufferDuration;
    /** 到了下一帧默认是接着播放，配置这个会自动停止 */
    private Boolean stopOnNextFrame;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Video {
        /** 外层 Dom 的类名 */
        String className() default "";

        // /** 视频地址 */
        String src() default "";

        /** 是否为直播，视频为直播时需要添加上，支持flv和hls格式 */
        boolean isLive() default true;

        /** 指定直播视频格式 */
        VideoType videoType() default VideoType.hls;

        /** 视频封面地址 */
        String poster() default "";

        /** 是否静音 */
        boolean muted() default false;

        /** 是否循环播放 */
        boolean loop() default false;

        /** 是否自动播放 */
        boolean autoPlay() default false;

        // /** 倍数，格式为[1.0, 1.5, 2.0] */
        // Double[] rates() default {};
        // /** key 是时刻信息，value 可以可以为空，可有设置为图片地址，请看上方示例 */
        // Object frames() default "";
        // /** 点击帧的时候默认是跳转到对应的时刻，如果想提前 3 秒钟，可以设置这个值为 3 */
        // boolean jumpBufferDuration() default false;
        /** 到了下一帧默认是接着播放，配置这个会自动停止 */
        boolean stopOnNextFrame() default false;
    }

    public static AmisVideo change(Boolean required, Video annotation, String type, String name, String label,
        Boolean disabled, String width, String remark) {
        AmisVideo temp = new AmisVideo(required, type, name, label, disabled, width, remark);
        temp.setClassName(annotation.className());
        temp.setSrc(annotation.src());
        temp.setIsLive(annotation.isLive());
        temp.setVideoType(annotation.videoType().getDesc());
        temp.setPoster(annotation.poster());
        temp.setMuted(annotation.muted());
        temp.setLoop(annotation.loop());
        temp.setAutoPlay(annotation.autoPlay());
        // temp.setRates(annotation.rates());
        // temp.setFrames(annotation.frames());
        // temp.setJumpBufferDuration(annotation.jumpBufferDuration());
        temp.setStopOnNextFrame(annotation.stopOnNextFrame());
        return temp;
    }
}
