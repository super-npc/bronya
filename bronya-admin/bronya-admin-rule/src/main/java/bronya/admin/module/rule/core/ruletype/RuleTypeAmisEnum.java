package bronya.admin.module.rule.core.ruletype;


import java.util.LinkedHashMap;
import java.util.Objects;

import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.reflect.method.MethodUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.util.EnumUtil;
import org.dromara.hutool.json.JSONUtil;

import com.alibaba.cola.exception.BizException;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleTypeAmisEnum implements IRuleTypeClass {
    private final Class<? extends Enum> fileType;
    private final Left left;
    private final OpType op;
    private final String rightVal;
    private final Object leftInputVal;

    @Override
    public boolean checkValueCondition() {
        if (leftInputVal == null)
            return false;
        return switch (op) {
            case select_equals -> rightVal.contentEquals(StrUtil.toString(leftInputVal));
            case select_not_equals -> !rightVal.contentEquals(StrUtil.toString(leftInputVal));
            case select_any_in -> JSONUtil.parseArray(rightVal).contains(leftInputVal.toString());
            case select_not_any_in -> !JSONUtil.parseArray(rightVal).contains(leftInputVal.toString());
            default -> throw new BizException(STR."rule不支持该op类型,选项 \{op}");
        };
    }

    @Override
    public String print(String printTemplate) {
        String leftInputDesc = null;
        if (leftInputVal != null) {
            leftInputDesc = MethodUtil.invoke(leftInputVal, "getDesc");
        }
        String rightValStr = null;
        LinkedHashMap enumMap = EnumUtil.getEnumMap(fileType);
        if (JSONUtil.isTypeJSONArray(rightVal)) {
            rightValStr = CollUtil.join(JSONUtil.parseArray(rightVal).stream().map(temp -> {
                if (enumMap.get(rightVal) instanceof AmisEnum a) {
                    return a.getDesc();
                }
                return null;
            }).filter(Objects::nonNull).toList(), ",");
        } else {
            if (enumMap.get(rightVal) instanceof AmisEnum a) {
                rightValStr = a.getDesc();
            }
        }
        return StrUtil.format(printTemplate, rightValStr,leftInputDesc);
    }

}
