package bronya.core.base.annotation.amis.type.video;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoType implements AmisEnum {
    flv("video/x-flv", Color.淡蓝色), hls("application/x-mpegURL", Color.深绿色);

    private final String desc;
    private final Color color;
}
