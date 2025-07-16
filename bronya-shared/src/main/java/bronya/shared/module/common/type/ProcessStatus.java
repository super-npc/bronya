package bronya.shared.module.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessStatus implements AmisEnum {
    CREATED("已创建", Color.适中的板岩暗蓝灰色, StatusType.PROCESS),

    PENDING("队列中", Color.橙色, StatusType.PROCESS),

    RUNNING("运行中", Color.天蓝色, StatusType.PROCESS),

    PAUSED("已暂停", Color.深兰花紫, StatusType.PROCESS),

    SUCCESS("完成", Color.纯绿, StatusType.RESULT),

    FAIL("失败", Color.深红色, StatusType.RESULT),

    CANCELED("已取消", Color.橙红色, StatusType.RESULT);

    private final String desc;
    private final Color color;
    private final StatusType type; // 区分流程状态和结果状态

    public enum StatusType {
        PROCESS, RESULT
    }
}