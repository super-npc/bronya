package bronya.core.base.annotation.amis.type.card;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardMediaPosition implements AmisEnum {
    left("左", Color.靛青), right("右", Color.灰色), top("上", Color.灯笼海棠), bottom("下", Color.深海洋绿);

    private final String desc;
    private final Color color;
}
