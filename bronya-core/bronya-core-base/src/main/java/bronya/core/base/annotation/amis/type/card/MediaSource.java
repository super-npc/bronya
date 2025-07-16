package bronya.core.base.annotation.amis.type.card;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MediaSource implements AmisEnum {
    MINI_APP("小程序", Color.火药蓝), AdminManager("后台上传", Color.靛青), OTHER("其他", Color.灰色);

    private final String desc;
    private final Color color;
}
