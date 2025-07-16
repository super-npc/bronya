package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 激活状态
 */
@Getter
@AllArgsConstructor
public enum ActivationStatus implements AmisEnum {
    ENABLE("生效", Color.军校蓝), DISABLE("失效", Color.深洋红色);

    private final String desc;
    private final Color color;
}