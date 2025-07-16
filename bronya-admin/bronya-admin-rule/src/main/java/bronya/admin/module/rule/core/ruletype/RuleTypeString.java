package bronya.admin.module.rule.core.ruletype;


import org.dromara.hutool.core.convert.ConvertUtil;
import org.dromara.hutool.core.text.StrUtil;

import com.alibaba.cola.exception.BizException;

import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import jodd.util.Wildcard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleTypeString implements IRuleTypeClass {
    private final Left left;
    private final OpType op;
    private final String rightVal;
    private final Object leftInputVal;

    @Override
    public boolean checkValueCondition() {
        String leftValue = ConvertUtil.toStr(leftInputVal);

        return switch (op) {
            case equal -> leftValue.contentEquals(rightVal);
            case not_equal -> !leftValue.contentEquals(rightVal);
            case is_empty -> StrUtil.isBlank(leftValue);
            case is_not_empty -> StrUtil.isNotBlank(leftValue);
            case like -> Wildcard.match(rightVal,STR."%\{leftValue}%");
            case not_like -> !Wildcard.match(rightVal,STR."%\{leftValue}%");
            case starts_with -> StrUtil.startWith(rightVal,leftValue);
            case ends_with -> StrUtil.endWith(rightVal,leftValue);
            default -> throw new BizException(STR."rule不支持该op类型,文本 \{op}");
        };
    }

    @Override
    public String print(String printTemplate) {
        return StrUtil.format(printTemplate, rightVal,leftInputVal);
    }
}
