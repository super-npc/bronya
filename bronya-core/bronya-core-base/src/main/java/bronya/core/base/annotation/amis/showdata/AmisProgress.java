package bronya.core.base.annotation.amis.showdata;

import java.lang.annotation.*;

import bronya.core.base.annotation.amis.AmisComponents;
import bronya.core.base.annotation.amis.type.Progress.ProgressGapPosition;
import bronya.core.base.annotation.amis.type.Progress.ProgressMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmisProgress extends AmisComponents {

    public AmisProgress(Boolean required,String type, String name, String label,Boolean disabled,String width,String remark) {
        super(required,type, name, label,disabled,width,remark);
    }

    /** 进度「条」的类型,可选line,circle,dashboard */
    private ProgressMode mode;
    /** 进度条线宽度 */
    private Integer strokeWidth;
    /**  */
    private Object placeholder;
    /** 是否展示进度文本 */
    private Boolean showLabel;
    /**  */
    private Integer gapDegree;
    /** 背景是否显示条纹 */
    private Boolean stripe;
    /** type为line可支持动画 */
    private Boolean animate;
    /** 是否显示阈值（刻度）数值 */
    private Boolean showThresholdText;
    /** 仪表盘进度条缺口位置，可选top bottom left right */
    private ProgressGapPosition gapPosition;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @Documented
    public @interface Progress {
        /** 进度「条」的类型,可选line,circle,dashboard */
        ProgressMode mode() default ProgressMode.line;

        // /** 进度条线宽度 */
        // int strokeWidth() default ;
        /**  */
        String placeholder() default "";

        /** 是否展示进度文本 */
        boolean showLabel() default true;

        /** 仪表盘缺角角度，可取值 0 ~ 295 */
        int gapDegree() default 75;

        /** 背景是否显示条纹 */
        boolean stripe() default false;

        /** type为line可支持动画 */
        boolean animate() default false;

        /** 是否显示阈值（刻度）数值 */
        boolean showThresholdText() default false;

        /** 仪表盘进度条缺口位置，可选top bottom left right */
        ProgressGapPosition gapPosition() default ProgressGapPosition.bottom;
    }

    public static AmisProgress change(Boolean required,Progress annotation, String type, String name, String label,Boolean disabled,String width,String remark) {
        AmisProgress temp = new AmisProgress(required,type, name, label,disabled,width,remark);
        temp.setMode(annotation.mode());
        // temp.setStrokeWidth(annotation.strokeWidth());
        temp.setPlaceholder(annotation.placeholder());
        temp.setShowLabel(annotation.showLabel());
        temp.setGapDegree(annotation.gapDegree());
        temp.setStripe(annotation.stripe());
        temp.setAnimate(annotation.animate());
        temp.setShowThresholdText(annotation.showThresholdText());
        temp.setGapPosition(annotation.gapPosition());
        return temp;
    }
}