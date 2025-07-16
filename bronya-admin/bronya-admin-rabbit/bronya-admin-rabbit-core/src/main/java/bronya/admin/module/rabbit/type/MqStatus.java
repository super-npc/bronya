package bronya.admin.module.rabbit.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MqStatus implements AmisEnum {
    CREATE("已创建", Color.兰花的紫色), BROKER_ACK_SUCCESS("投递成功", Color.午夜的蓝色), BROKER_ACK_FAILED("投递失败", Color.橙色),
    SPENT_SUCCESS("已消费", Color.适中的碧绿色), SPENT_FAILED("已消费失败", Color.热情的粉红);

    private final String desc;
    private final Color color;
}