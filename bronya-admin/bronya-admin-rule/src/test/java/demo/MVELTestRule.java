package demo;

import java.util.HashMap;
import java.util.Map;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;

import lombok.Data;

public class MVELTestRule {

    public static void main(String[] args) {

        // 规则引擎
        // RulesEngine rulesEngine = new DefaultRulesEngine();
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRuleListener(new MyRuleListener());

        // 规则
        MVELRule ageRule =
            new MVELRule().name("my rule").description("test demo rule").priority(1).when("user.age > 18").then(
                "map.put('code',200);map.put('msg','success');myResult.setCode('200');myResult.setMsg('success');");

        Rules rules = new Rules();
        rules.register(ageRule);

        Facts facts = new Facts();

        User user = new User();
        user.setAge(19);
        facts.put("user", user);
        Map<String, String> map = new HashMap();
        MyResult myResult = new MyResult();
        facts.put("map", map);
        facts.put("myResult", myResult);

        rulesEngine.fire(rules, facts);
        System.out.println(map);
        System.out.println(myResult);

    }

    @Data
    public static class MyResult {
        private String code;
        private String msg;
    }

    @Data
    public static class User {
        private Integer age;
    }
}
