package bronya.admin.module.rule.core.ruletype;


import java.math.BigDecimal;

import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;

import com.alibaba.cola.exception.BizException;

import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleTypeNumber implements IRuleTypeClass {
    private final Left left;
    private final OpType op;
    private final String rightVal;
    private final Object leftInputVal;

    @Override
    public boolean checkValueCondition() {
        if (op == OpType.is_empty) {
            return leftInputVal == null;
        } else if (op == OpType.is_not_empty) {
            return leftInputVal != null;
        }
        if (leftInputVal == null)
            return false;
        BigDecimal leftInputValue = NumberUtil.toBigDecimal(leftInputVal.toString());

        return switch (op) {
            case equal -> leftInputValue.compareTo(NumberUtil.toBigDecimal(rightVal)) == 0;
            case not_equal -> leftInputValue.compareTo(NumberUtil.toBigDecimal(rightVal)) != 0;
            case less -> leftInputValue.compareTo(NumberUtil.toBigDecimal(rightVal)) < 0;
            case less_or_equal -> leftInputValue.compareTo(NumberUtil.toBigDecimal(rightVal)) <= 0;
            case greater -> leftInputValue.compareTo(NumberUtil.toBigDecimal(rightVal)) > 0;
            case greater_or_equal -> leftInputValue.compareTo(NumberUtil.toBigDecimal(rightVal)) >= 0;
            case between -> {
                String subBetween = StrUtil.subBetween(rightVal, "[", "]");
                String[] values = subBetween.split(",");
                BigDecimal lowerBound = NumberUtil.toBigDecimal(values[0]);
                BigDecimal upperBound = NumberUtil.toBigDecimal(values[1]);
                yield leftInputValue.compareTo(lowerBound) >= 0 && leftInputValue.compareTo(upperBound) <= 0;
            }
            case not_between -> {
                String subBetween = StrUtil.subBetween(rightVal, "[", "]");
                String[] values = subBetween.split(",");
                BigDecimal lowerBound = NumberUtil.toBigDecimal(values[0]);
                BigDecimal upperBound = NumberUtil.toBigDecimal(values[1]);
                yield !(leftInputValue.compareTo(lowerBound) >= 0 && leftInputValue.compareTo(upperBound) <= 0);
            }
            default -> throw new BizException(STR."rule不支持该op类型,数字 \{op}");
        };
    }

    @Override
    public String print(String printTemplate) {
        if (op == OpType.is_empty || op == OpType.is_not_empty || leftInputVal == null) {
            return StrUtil.format(printTemplate, rightVal,leftInputVal);
        }
        BigDecimal leftInputValue = NumberUtil.toBigDecimal(leftInputVal.toString());
        return StrUtil.format(printTemplate, rightVal,leftInputValue);
    }

}
