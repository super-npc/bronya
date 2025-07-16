package bronya.admin.module.rule.core;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.util.EnumUtil;
import org.dromara.hutool.core.util.RandomUtil;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

import com.alibaba.cola.exception.BizException;
import com.google.common.collect.Lists;

import bronya.admin.module.rule.core.ruletype.*;
import bronya.core.base.annotation.amis.AmisConditionField;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.rule.ConditionGroup;
import bronya.shared.module.rule.ConditionGroup.Conjunction;
import bronya.shared.module.rule.ConditionGroup.Left;
import bronya.shared.module.rule.ConditionGroup.OpType;
import bronya.shared.module.util.Md;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EasyRuleNode<T> {
    private final Class<T> clazz;

    public CheckRuleFinalGroupRes checkRuleGroup(ConditionGroup root, T checkObj) {
        Assert.isTrue(root.isCfg(), () -> new BizException("未配置规则"));
        try {
            // 1. 递归收集所有节点的校验结果
            Map<ConditionGroup, FinalDecisionRes> groupResultMap = new java.util.IdentityHashMap<>();
            Map<ConditionGroup, CheckRuleGroupRes> leafResultMap = new java.util.IdentityHashMap<>();
            FinalDecisionRes res = this.checkRuleGroupRecursiveCollect(root, checkObj, groupResultMap, leafResultMap);
            boolean finalDecision = res.isFinalDecision();

            List<RuleTreeRes> ruleTreeRes = this.getRuleFiles(root);
            String treeRes = this.buildRuleTreeString(ruleTreeRes, finalDecision);
            return new CheckRuleFinalGroupRes(finalDecision, res.getSuccess(), res.getFail(), res.getInfos(), treeRes);
        } catch (Exception e) {
            log.error("校验规则引擎异常", e);
            throw e;
        }
    }

    private List<RuleTreeRes> getRuleFiles(ConditionGroup root) {
        List<RuleTreeRes> result = new java.util.ArrayList<>();
        if (root == null) {
            return result;
        }
        result.add(toRuleFile(root));
        return result;
    }

    private RuleTreeRes toRuleFile(ConditionGroup group) {
        RuleTreeRes file = new RuleTreeRes();
        // 组节点
        if (group.getChildren() != null && !group.getChildren().isEmpty()) {
            file.setFinalDecision(group.getGroupFinalDecision());
            file.setPrint(
                STR.
                "[组] 操作符: [\{group.getConjunction().getDesc()}] 该组决策: [\{Md.emojiBool(group.getGroupFinalDecision())}]");
                    List<RuleTreeRes> children = new java.util.ArrayList<>();
            for (ConditionGroup child : group.getChildren()) {
                children.add(toRuleFile(child));
            }
            file.setChildren(children);
        } else {
            file.setPrint(STR."[条件] \{group.getResult()}");
                file.setChildren(null);
        }
        return file;
    }

    @Data
    public static class RuleTreeRes {
        private Boolean finalDecision;
        private String print;
        private List<RuleTreeRes> children;
    }

    /**
     * 递归校验ConditionGroup，收集所有节点的校验结果到Map
     */
    private FinalDecisionRes checkRuleGroupRecursiveCollect(ConditionGroup group, T checkObj,
        Map<ConditionGroup, FinalDecisionRes> groupResultMap, Map<ConditionGroup, CheckRuleGroupRes> leafResultMap) {
        List<CheckRuleGroupRes> checkResults = Lists.newArrayList();
        List<FinalDecisionRes> childResults = Lists.newArrayList();
        Rules rules = new Rules();
        boolean isGroup = CollUtil.isNotEmpty(group.getChildren());
        if (isGroup) {
            for (ConditionGroup child : group.getChildren()) {
                if (CollUtil.isNotEmpty(child.getChildren())) {
                    FinalDecisionRes childRes =
                        checkRuleGroupRecursiveCollect(child, checkObj, groupResultMap, leafResultMap);
                    childResults.add(childRes);
                } else {
                    // 叶子节点，单个条件
                    rules.register(this.checkConditionRule(checkResults, child, checkObj));
                    if (!checkResults.isEmpty()) {
                        leafResultMap.put(child, checkResults.getLast());
                    }
                }
            }
        } else {
            // group本身就是叶子节点
            rules.register(this.checkConditionRule(checkResults, group, checkObj));
            if (!checkResults.isEmpty()) {
                leafResultMap.put(group, checkResults.getLast());
            }
        }
        FinalDecisionRes thisLevelRes =
            this.singleRuleGroup(group, group.getConjunction(), rules, checkResults, checkObj);
        groupResultMap.put(group, thisLevelRes);
        // 汇总所有子层结果
        List<Boolean> allBool = Lists.newArrayList();
        List<CheckRuleGroupRes> allInfos = Lists.newArrayList();
        List<CheckRuleGroupRes> allSuccess = Lists.newArrayList();
        List<CheckRuleGroupRes> allFail = Lists.newArrayList();
        allBool.add(thisLevelRes.isFinalDecision());
        allInfos.addAll(thisLevelRes.getInfos());
        allSuccess.addAll(thisLevelRes.getSuccess());
        allFail.addAll(thisLevelRes.getFail());
        for (FinalDecisionRes childRes : childResults) {
            allBool.add(childRes.isFinalDecision());
            allInfos.addAll(childRes.getInfos());
            allSuccess.addAll(childRes.getSuccess());
            allFail.addAll(childRes.getFail());
        }
        boolean finalDecision =
            switch (Optional.ofNullable(group.getConjunction()).orElse(ConditionGroup.Conjunction.and)) {
                case and -> allBool.stream().allMatch(Boolean::booleanValue);
                case or -> allBool.stream().anyMatch(Boolean::booleanValue);
            };
        return new FinalDecisionRes(group.getConjunction(), finalDecision, allInfos, allSuccess, allFail);
    }

    /**
     * 单独每个规则组
     */
    private FinalDecisionRes singleRuleGroup(ConditionGroup group, Conjunction conjunction, Rules rules,
        List<CheckRuleGroupRes> checkResults, T checkObj) {
        RulesEngineParameters parameters = new RulesEngineParameters()
            // 注意:skipOnFirstAppliedRule意思是，只要匹配到第一条规则就跳过后面规则匹配
            .skipOnFirstAppliedRule(false)
            // 告诉引擎一个规则不被触发，就跳过后面的规则。 // 这个有效果
            .skipOnFirstNonTriggeredRule(false)
            // 告诉引擎在规则失败时跳过后面的规则。
            .skipOnFirstFailedRule(false);
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine(parameters);
        // rulesEngine.registerRuleListener(new ThisRuleListener());

        Facts facts = new Facts();
        BeanUtil.beanToMap(checkObj).forEach((k, v) -> {
            if (v != null)
                facts.put(k, v);
        });
        rulesEngine.fire(rules, facts);
        // 对结果最终的决策
        List<Boolean> checkBoolRes = checkResults.stream().map(CheckRuleGroupRes::isResult).toList();
        // 如果没有child 是没有conjunction,其实就是一条规则并且的关系
        boolean finalDecision = switch (Optional.ofNullable(conjunction).orElse(Conjunction.and)) {
            case and -> checkBoolRes.stream().allMatch(Boolean::booleanValue);
            case or -> checkBoolRes.stream().anyMatch(Boolean::booleanValue);
        };
        group.setGroupFinalDecision(finalDecision);
        // 找出成功,失败的集合并返回list
        List<CheckRuleGroupRes> success = checkResults.stream().filter(CheckRuleGroupRes::isResult).toList();
        List<CheckRuleGroupRes> fail = checkResults.stream().filter(res -> !res.isResult()).toList();
        // log.info(STR."操作符:\{conjunction},成功数:\{success.size()},失败数:\{fail.size()},决策:\{finalDecision}");
        return new FinalDecisionRes(conjunction, finalDecision, checkResults, success, fail);
    }

    private Rule checkConditionRule(List<CheckRuleGroupRes> checkRuleGroupResList, ConditionGroup condition,
        T checkObj) {
        // 根据反射拿到泛型的class字段组装等式条件
        Left left = condition.getLeft();
        OpType op = condition.getOp();
        String right = condition.getRight();
        String fieldName = left.getField();
        Field field = FieldUtil.getField(clazz, fieldName);
        Class<?> typeClass = field.getType();
        AmisConditionField annotation = AnnotationUtil.getAnnotation(field, AmisConditionField.class);
        // rule 根据name作为维度的,相同维度只能有一条规则,所以注意同一个字段name的命名
        return new RuleBuilder().when(facts -> this.getRuleRunType(condition, checkRuleGroupResList, facts, fieldName,
            typeClass, field, left, op, right, checkObj)).then(facts -> {
                // Object o = facts.get(fieldName);
                // System.out.println("o = " + o);
            }).name(annotation.label() + RandomUtil.randomString(5)).description(null).build();
    }

    private boolean getRuleRunType(ConditionGroup condition, List<CheckRuleGroupRes> checkRuleGroupResList, Facts facts,
        String fieldName, Class<?> fileldTypeClass, Field field, Left left, OpType op, String right, T checkObj) {
        Object leftInputValue = facts.get(fieldName);
        IRuleTypeClass iRuleTypeClass = null;
        if (AdminBaseConstant.numberClass.contains(fileldTypeClass)) {
            iRuleTypeClass = new RuleTypeNumber(left, op, right, leftInputValue);
        } else if (fileldTypeClass == String.class) {
            iRuleTypeClass = new RuleTypeString(left, op, right, leftInputValue);
        } else if (fileldTypeClass == Boolean.class) {
            iRuleTypeClass = new RuleTypeBool(left, op, right, leftInputValue);
        } else if (Lists.newArrayList(Date.class, LocalDateTime.class, LocalDate.class).contains(fileldTypeClass)) {
            iRuleTypeClass = new RuleTypeDateTime(fileldTypeClass, left, op, right, leftInputValue);
        } else if (fileldTypeClass == LocalTime.class) {
            iRuleTypeClass = new RuleTypeTime(left, op, right, leftInputValue);
        } else if (EnumUtil.isEnum(field.getType())) {
            Class<? extends Enum> type = (Class<? extends Enum>)field.getType();
            iRuleTypeClass = new RuleTypeAmisEnum(type, left, op, right, leftInputValue);
        } else {
            throw new RuntimeException(STR."没有找到匹配的类型\{fileldTypeClass}");
        }
        boolean res = false;
        try {
            res = iRuleTypeClass.checkValueCondition();
            Field ruleField = FieldUtil.getField(checkObj.getClass(), fieldName);
            AmisConditionField annotation = AnnotationUtil.getAnnotation(ruleField, AmisConditionField.class);
            String print =
                iRuleTypeClass.print(STR." [\{annotation.label()} \{op.getDesc()} {}] 输入:{} \{Md.emojiBool(res)}");
                    log.info(print);
            condition.setResult(print);
            left.setLabel(annotation.label());
            checkRuleGroupResList.add(new CheckRuleGroupRes(res, fieldName, left, op, right, leftInputValue, print));
        } catch (Exception e) {
            throw new BizException(STR."rule执行未知异常 \{e.getMessage()}", e);
        }
        return res;
    }

    @Data
    @AllArgsConstructor
    public static class CheckRuleFinalGroupRes {
        /**
         * 是否跟预期一致，true表示校验通过，false则有打破规则
         */
        private boolean expectOk;
        private List<CheckRuleGroupRes> successRes;
        private List<CheckRuleGroupRes> failRes;
        /**
         * 包含成功/失败的所有检查
         */
        private List<CheckRuleGroupRes> allRes;
        /**
         * 树形状结构
         */
        private String treeRes;
    }

    @Data
    @AllArgsConstructor
    public static class FinalDecisionRes {
        private Conjunction conjunction;
        private boolean finalDecision;
        private List<CheckRuleGroupRes> infos;
        private List<CheckRuleGroupRes> success;
        private List<CheckRuleGroupRes> fail;
    }

    @Data
    @AllArgsConstructor
    public static class CheckRuleGroupRes {
        private boolean result;
        private String fieldName;
        private Left left;
        private OpType op;
        private String right;
        private Object leftInputValue;
        private String print;
    }

    @Data
    public static class Result {
        private boolean success;
        private String failureReason;
    }

    /**
     * 将RuleFile列表转换为目录结构字符串
     */
    private String buildRuleTreeString(List<RuleTreeRes> ruleTreeRes, boolean finalDecision) {
        StringBuilder sb = new StringBuilder();
        sb.append(STR."所有组最终决策: \{Md.emojiBool(finalDecision)}\n");
        for (int i = 0; i < ruleTreeRes.size(); i++) {
            RuleTreeRes file = ruleTreeRes.get(i);
            boolean isLast = (i == ruleTreeRes.size() - 1);
            buildRuleFileString(file, 0, "", isLast, sb);
        }
        return sb.toString();
    }

    /**
     * 递归构建单个RuleFile的目录结构字符串
     */
    private void buildRuleFileString(RuleTreeRes file, int level, String prefix, boolean isLast, StringBuilder sb) {
        String nodePrefix = prefix + (level == 0 ? "" : (isLast ? "└─" : "├─"));
        sb.append(nodePrefix).append(file.getPrint()).append("\n");

        if (file.getChildren() != null && !file.getChildren().isEmpty()) {
            int size = file.getChildren().size();
            for (int i = 0; i < size; i++) {
                RuleTreeRes child = file.getChildren().get(i);
                boolean childIsLast = (i == size - 1);
                buildRuleFileString(child, level + 1, prefix + (level == 0 ? "" : (isLast ? "   " : "│  ")),
                    childIsLast, sb);
            }
        }
    }
}
