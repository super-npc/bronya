package bronya.admin.module.rule.core.ruletype;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.dromara.hutool.core.date.DateTime;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.json.JSONUtil;

import com.alibaba.cola.exception.BizException;

import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleTypeDateTime implements IRuleTypeClass {
    private final Class<?> fileldTypeClass;
    private final Left left;
    private final OpType op;
    private final String rightVal;
    private final Object leftInputVal;

    @Override
    public boolean checkValueCondition() {
        if (op == OpType.is_empty) {
            return Objects.isNull(leftInputVal);
        } else if (op == OpType.is_not_empty) {
            return !Objects.isNull(leftInputVal);
        }
        // 来到这里,leftInputVal已经非空状态,如果还是为空说明输入的实体没有设置该属性,应该抛出该异常
        if (leftInputVal == null)
            return false;
        return switch (op) {
            case equal ->
                DateUtil.parse(StrUtil.toString(leftInputVal)).getTime() == DateUtil.parse(rightVal).getTime();
            case not_equal ->
                DateUtil.parse(StrUtil.toString(leftInputVal)).getTime() != DateUtil.parse(rightVal).getTime();
            case less -> DateUtil.parse(StrUtil.toString(leftInputVal)).isBefore(DateUtil.parse(rightVal));
            case less_or_equal ->
                DateUtil.parse(StrUtil.toString(leftInputVal)).isBeforeOrEquals(DateUtil.parse(rightVal));
            case greater -> DateUtil.parse(StrUtil.toString(leftInputVal)).isAfter(DateUtil.parse(rightVal));
            case greater_or_equal ->
                DateUtil.parse(StrUtil.toString(leftInputVal)).isAfterOrEquals(DateUtil.parse(rightVal));
            case between -> {
                DateTime leftValue = DateUtil.parse(StrUtil.toString(leftInputVal));
                List<DateTime> dates =
                    JSONUtil.parseArray(rightVal).stream().map(date -> DateUtil.parse(StrUtil.toString(date))).toList();
                yield leftValue.isAfterOrEquals(dates.getFirst()) && leftValue.isBeforeOrEquals(dates.getLast());
            }
            case not_between -> {
                DateTime leftValue = DateUtil.parse(StrUtil.toString(leftInputVal));
                List<DateTime> dates =
                    JSONUtil.parseArray(rightVal).stream().map(date -> DateUtil.parse(StrUtil.toString(date))).toList();
                yield leftValue.before(dates.getFirst()) || leftValue.isAfter(dates.getLast());
            }
            default -> throw new BizException(STR."rule不支持该op类型,日期时间 \{op}");
        };
    }

    @Override
    public String print(String printTemplate) {
        if (op == OpType.is_empty || op == OpType.is_not_empty || leftInputVal == null) {
            switch (op) {
                case between, not_between -> {
                    List<DateTime> dates = JSONUtil.parseArray(rightVal).stream()
                        .map(date -> DateUtil.parse(StrUtil.toString(date))).toList();
                    return StrUtil.format(printTemplate, dates,leftInputVal);
                }
            }
            DateTime rightValue = DateUtil.parse(rightVal);
            return StrUtil.format(printTemplate, rightValue,leftInputVal);
        }
        DateTime leftValue = DateUtil.parse(StrUtil.toString(leftInputVal));
        switch (op) {
            case between, not_between -> {
                List<DateTime> dates =
                    JSONUtil.parseArray(rightVal).stream().map(date -> DateUtil.parse(StrUtil.toString(date))).toList();
                if (LocalDate.class == fileldTypeClass) {
                    List<LocalDate> localDates =
                        dates.stream().map(date -> date.toLocalDateTime().toLocalDate()).toList();
                    return StrUtil.format(printTemplate, localDates,leftValue.toLocalDateTime().toLocalDate());
                }
                return StrUtil.format(printTemplate, dates,leftValue);
            }
        }
        DateTime rightValue = DateUtil.parse(rightVal);
        if (LocalDate.class == fileldTypeClass) {
            return StrUtil.format(printTemplate,
                rightValue.toLocalDateTime().toLocalDate(),leftValue.toLocalDateTime().toLocalDate());
        }
        return StrUtil.format(printTemplate, rightValue,leftValue);
    }

}
