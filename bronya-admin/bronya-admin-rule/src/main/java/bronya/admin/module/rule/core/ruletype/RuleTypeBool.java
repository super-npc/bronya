package bronya.admin.module.rule.core.ruletype;

import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.text.StrUtil;

import com.alibaba.cola.exception.BizException;

import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleTypeBool implements IRuleTypeClass {
    private final Left left;
    private final OpType op;
    private final String rightVal;
    private final Object leftInputVal;

    @Override
    public boolean checkValueCondition() {
        if (leftInputVal == null)
            return false;
        Boolean leftInputValue = ConvertUtil.toBoolean(leftInputVal, false);
        Boolean rightValue = ConvertUtil.toBoolean(rightVal, false);
        return switch (op) {
            case equal -> leftInputValue == rightValue;
            case not_equal -> leftInputValue != rightValue;
            default -> throw new BizException(STR."rule不支持该op类型,布尔 \{op}");
        };
    }

    @Override
    public String print(String printTemplate) {
        Boolean leftInputValue = ConvertUtil.toBoolean(leftInputVal, false);
        Boolean rightValue = ConvertUtil.toBoolean(rightVal, false);
        return StrUtil.format(printTemplate, rightValue,leftInputValue);
    }
}
