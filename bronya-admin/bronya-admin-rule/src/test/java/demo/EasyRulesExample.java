package demo;

import java.util.List;

import org.dromara.hutool.json.JSONUtil;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EasyRulesExample {

    public static void main(String[] args) {
        String json = """
            {
            "id": "542ea87205af",
            "conjunction": "and",
            "children": [
            {
            "id": "04e72fa3594e",
            "left": {
            "type": "field",
            "field": "name"
            },
            "op": "equal",
            "right": "张三"
            },
            {
            "id": "72d3d11e9073",
            "conjunction": "or",
            "children": [
            {
            "id": "e95fc05cafeb",
            "left": {
            "type": "field",
            "field": "name"
            },
            "op": "not_equal",
            "right": "王五"
            },
            {
            "id": "aacfe5ed49bf",
            "left": {
            "type": "field",
            "field": "count"
            },
            "op": "greater",
            "right": 1
            }
            ]
            }
            ]
            }
            """;
        ConditionGroup root = JSONUtil.parseObj(json).toBean(ConditionGroup.class);

        RulesEngineParameters parameters = new RulesEngineParameters()
            // 注意:skipOnFirstAppliedRule意思是，只要匹配到第一条规则就跳过后面规则匹配
            .skipOnFirstAppliedRule(false)
            // 告诉引擎一个规则不被触发，就跳过后面的规则。
            .skipOnFirstNonTriggeredRule(false)
            // 告诉引擎在规则失败时跳过后面的规则。
            .skipOnFirstFailedRule(true);
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);
        rulesEngine.registerRuleListener(new MyRuleListener());

        Rules rules = new Rules();

        Result result = new Result();
        parseConditionGroupToRules(root, rules, result);

        Facts facts = new Facts();
        facts.put("name", "张三");
        facts.put("count", 11);
        facts.put("foods", List.of("BISCUIT", "CHOCOLATE"));
        // facts.put("date", "2024-08-15 20:00:00");
        // facts.put("localDate", "2024-08-15");
        // facts.put("localTime", "");
        facts.put("result", result);

        rulesEngine.fire(rules, facts);

        if (!result.isSuccess()) {
            System.out.println("Validation failed: " + result.getFailureReason());
        } else {
            System.out.println("Validation passed.");
        }
    }

    private static void parseConditionGroupToRules(ConditionGroup conditionGroup, Rules rules, Result result) {
        for (ConditionGroup.Children child : conditionGroup.getChildren()) {
            if (child.getChildren() != null && !child.getChildren().isEmpty()) {
                parseConditionGroupToRules(new ConditionGroup(child.getConjunction(), child.getChildren()), rules, result);
            } else {
                String condition = child.getOp().buildCondition(child.getLeft().getField(), child.getRight());
                // 成功规则
                Rule rule = new MVELRule().name(condition).when(condition).then(STR."""
                        result.setSuccess(true);
                        """);
                rules.register(rule);
            }
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ConditionGroup {
        private String conjunction;
        private List<Children> children;

        @NoArgsConstructor
        @Data
        public static class Children {
            private Left left;
            private OpType op;
            private String right;
            private String conjunction;
            private List<Children> children;

            @NoArgsConstructor
            @Data
            public static class Left {
                private String type;
                private String field;
            }
        }

        @Getter
        @AllArgsConstructor
        public enum OpType {
            // 文本
            equal("", "等于"),

            not_equal("!=", "不等于"),

            is_empty("", "为空"),

            is_not_empty("", "不为空"),

            like("", "模糊匹配"),

            not_like("", "不匹配"),

            starts_with("", "匹配开头"),

            ends_with("", "匹配结尾"), // 数字,日期
            less("<", "小于"),

            less_or_equal("<=", "小于或等于"),

            greater(">", "大于"),

            greater_or_equal(">=", "大于或等于"),

            between("", "属于范围"),

            not_between("", "不属于范围"), // 下拉选择
            select_equals("==", "等于"),

            select_not_equals("!=", "不等于"),

            select_any_in("", "包含"),

            select_not_any_in("", "不包含");

            private final String opVal;
            private final String desc;

            public String buildCondition(String field, String rightValue) {
                return switch (this) {
                    case equal -> STR."\{field} == '\{rightValue}'";
                    case between -> {
                        String[] values = rightValue.split(",");
                        yield STR."\{field} >= \{values[0]} && \{field} <= \{values[1]}";
                    }
                    case select_any_in -> STR."\{field} in (\{rightValue})";
                    case like -> STR."\{field} =~ /.*\{rightValue}.*/";
                    case not_like -> STR."\{field} !~ /.*\{rightValue}.*/";
                    case starts_with -> STR."\{field}.startsWith('\{rightValue}')";
                    case ends_with -> STR."\{field}.endsWith('\{rightValue}')";
                    case is_empty -> STR."\{field} == null || \{field}.equals('')";
                    case is_not_empty -> STR."\{field} != null && !\{field}.equals('')";
                    default -> STR."\{field} \{opVal} \{rightValue}";
                };
            }

        }
    }

    @Data
    public static class Result {
        private boolean success;
        private String failureReason;
    }
}
