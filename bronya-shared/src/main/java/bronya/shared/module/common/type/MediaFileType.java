package bronya.shared.module.common.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum MediaFileType implements AmisEnum {
    IMAGE("图片", Color.火药蓝), VIDEO("视频", Color.马鞍棕色), AUDIO("音频", Color.靛青), OTHER("其他", Color.灰色);

    private final String desc;
    private final Color color;
}
