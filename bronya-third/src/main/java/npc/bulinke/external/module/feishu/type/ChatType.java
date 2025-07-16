package npc.bulinke.external.module.feishu.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatType implements AmisEnum {
    DING_DING("钉钉", Color.紫罗兰), FEI_SHU("飞书", Color.军校蓝);

    private final String desc;
    private final Color color;
}
