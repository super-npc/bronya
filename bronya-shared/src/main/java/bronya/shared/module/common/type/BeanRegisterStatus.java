package bronya.shared.module.common.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

/**
 * 类注册枚举
 */
@Getter
@AllArgsConstructor
@FieldNameConstants
public enum BeanRegisterStatus implements AmisEnum {
    REGISTERED("已注册", Color.森林绿), DOWN("类不存在", Color.紫罗兰);

    private final String desc;
    private final Color color;
}