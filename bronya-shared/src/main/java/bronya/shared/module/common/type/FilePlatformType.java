package bronya.shared.module.common.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilePlatformType implements AmisEnum {
    LOCAL("本地", Color.绿宝石), MINI_APP_CLOUD("小程序云储存", Color.深红色);

    private final String desc;
    private final Color color;
}