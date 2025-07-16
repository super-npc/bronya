package bronya.admin.module.rule.core.util;

import com.alibaba.fastjson2.JSONObject;

import bronya.admin.module.rule.core.EasyRuleNode;
import bronya.admin.module.rule.core.EasyRuleNode.CheckRuleFinalGroupRes;
import bronya.shared.module.rule.ConditionGroup;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class RuleUtil {

    public <T> CheckRuleFinalGroupRes checkRuleGroup(Class<T> ruleClass, String json, T checkObj) {
        log.info("检查规则对象:{}",checkObj);
        EasyRuleNode<T> assetRule = new EasyRuleNode<>(ruleClass);
        ConditionGroup assetStopProfitRule = JSONObject.parseObject(json, ConditionGroup.class);
        return assetRule.checkRuleGroup(assetStopProfitRule, checkObj);
    }

}
