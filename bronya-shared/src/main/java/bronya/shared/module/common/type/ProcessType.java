package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessType implements AmisEnum {
    WAITING("未开始", Color.适中的板岩暗蓝灰色), RUNNING("运行中", Color.适中的绿宝石), FINISH("完成", Color.矢车菊的蓝色);

    private final String desc;
    private final Color color;
}