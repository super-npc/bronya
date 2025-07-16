package bronya.admin.module.rule.core.ruletype;


import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.json.JSONUtil;

import com.alibaba.cola.exception.BizException;

import bronya.shared.module.rule.ConditionGroup;
import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleTypeTime implements IRuleTypeClass {
    private final Left left;
    private final OpType op;
    private final String rightVal;
    private final Object leftInputVal;

    @Override
    public boolean checkValueCondition() {
        if (op == ConditionGroup.OpType.is_empty) {
            return Objects.isNull(leftInputVal);
        } else if (op == ConditionGroup.OpType.is_not_empty) {
            return !Objects.isNull(leftInputVal);
        }
        // 来到这里,leftInputVal已经非空状态,如果还是为空说明输入的实体没有设置该属性,应该抛出该异常
        if (leftInputVal == null)
            return false;
        // 转换为LocalTime并只保留到分钟
        LocalTime leftValue = this.getLeftValue(leftInputVal);
        return switch (op) {
            case equal -> leftValue.equals(LocalTime.parse(rightVal));
            case not_equal -> !leftValue.equals(LocalTime.parse(rightVal));
            case less -> leftValue.isBefore(LocalTime.parse(rightVal));
            case less_or_equal ->
                leftValue.isBefore(LocalTime.parse(rightVal)) && leftValue.equals(LocalTime.parse(rightVal));
            case greater -> leftValue.isAfter(LocalTime.parse(rightVal));
            case greater_or_equal ->
                leftValue.isAfter(LocalTime.parse(rightVal)) && leftValue.equals(LocalTime.parse(rightVal));
            case between -> {
                List<LocalTime> dates = JSONUtil.parseArray(rightVal).stream()
                    .map(date -> LocalTime.parse(StrUtil.toString(date))).toList();
                yield leftValue.isAfter(dates.getFirst()) && leftValue.isBefore(dates.getLast());
            }
            case not_between -> {
                List<LocalTime> dates = JSONUtil.parseArray(rightVal).stream()
                    .map(date -> LocalTime.parse(StrUtil.toString(date))).toList();
                yield leftValue.isBefore(dates.getFirst()) || leftValue.isAfter(dates.getLast());
            }
            default -> throw new BizException(STR."rule不支持该op类型,时间 \{op}");
        };
    }

    @Override
    public String print(String printTemplate) {
        if (op == ConditionGroup.OpType.is_empty || op == ConditionGroup.OpType.is_not_empty || leftInputVal == null) {
            switch (op) {
                case between, not_between -> {
                    List<LocalTime> dates = JSONUtil.parseArray(rightVal).stream()
                        .map(date -> LocalTime.parse(StrUtil.toString(date))).toList();
                    return StrUtil.format(printTemplate, dates,leftInputVal);
                }
            }
            return StrUtil.format(printTemplate, rightVal,leftInputVal);
        }
        LocalTime leftValue = this.getLeftValue(leftInputVal);
        switch (op) {
            case between, not_between -> {
                List<LocalTime> dates = JSONUtil.parseArray(rightVal).stream()
                    .map(date -> LocalTime.parse(StrUtil.toString(date))).toList();
                return StrUtil.format(printTemplate, dates,leftValue);
            }
        }
        LocalTime rightValue = LocalTime.parse(rightVal);
        return StrUtil.format(printTemplate, rightValue,leftValue);
    }

    private LocalTime getLeftValue(Object leftInputVal) {
        return LocalTime
            .parse(StrUtil.sub(leftInputVal.toString(), 0, StrUtil.ordinalIndexOf(leftInputVal.toString(), ":", 2)));
    }

}
