package bronya.shared.module.rule;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;

/**
 * 规则组
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConditionGroup {
    private Conjunction conjunction;
    private List<ConditionGroup> children;
    private Left left;
    private OpType op;
    private String right;

    /**
     * 是否有配置
     */
    public boolean isCfg(){
        return CollUtil.isNotEmpty(children);
    }
    /**
     * 存放结果
     */
    private Boolean groupFinalDecision; // 当前组的最终结果,如果是and,则groupFinalDecision为true,否则为false
    private String result; // 字符串结果

    @Getter
    @AllArgsConstructor
    public enum Conjunction {
        and("且"), or("或");

        private final String desc;
    }

    @NoArgsConstructor
    @Data
    public static class Left {
        private String type;
        private String field;
        private String label;
    }

    @Getter
    @AllArgsConstructor
    public enum OpType {
        // 文本
        equal("等于"),

        not_equal("不等于"),

        is_empty("为空"),

        is_not_empty("不为空"),

        like("模糊匹配"),

        not_like("不匹配"),

        starts_with("匹配开头"),

        ends_with("匹配结尾"),

        // 数字,日期
        less("小于"),

        less_or_equal("小于或等于"),

        greater("大于"),

        greater_or_equal("大于或等于"),

        between("属于范围"),

        not_between("不属于范围"),

        // 下拉选择
        select_equals("等于"),

        select_not_equals("不等于"),

        select_any_in("包含"),

        select_not_any_in("不包含");

        private final String desc;
    }
}