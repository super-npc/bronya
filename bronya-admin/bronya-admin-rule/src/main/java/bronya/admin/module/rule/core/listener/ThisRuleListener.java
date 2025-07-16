package bronya.admin.module.rule.core.listener;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ThisRuleListener implements RuleListener {

    /**
     * 在规则操作成功执行之后触发
     *
     * @param rule t当前的规则
     * @param facts 执行规则操作时的已知事实
     */
    @Override
    public void onSuccess(Rule rule, Facts facts) {
        System.out.println("------onSuccess-----" + rule.getName());
    }

    /**
     * 在规则操作执行失败时触发
     *
     * @param rule 当前的规则
     * @param facts 执行规则操作时的已知事实
     * @param e 执行规则操作时发生的异常
     */
    @Override
    public void onFailure(Rule rule, Facts facts, Exception e) {
        System.out.println("------onFailure-----" + rule.getName());
    }

    /**
     * 在评估规则之前触发。
     *
     * @param rule 正在被评估的规则
     * @param facts 评估规则之前的已知事实
     * @return 如果规则应该评估，则返回true，否则返回false
     */
    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        System.out.println("------beforeEvaluate-----" + rule.getName());
        return true;
    }

    /**
     * 在评估规则之后触发,醉了,评估结果true会触发onSuccess,但是false不会触发onFailure
     *
     * @param rule 评估之后的规则
     * @param facts 评估规则之后的已知事实
     * @param b 评估结果
     */
    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean b) {
        String string = facts.toString();
        System.out.println("------afterEvaluate-----" + rule.getName() + " 结果:" + b);
    }

    /**
     * 在规则操作执行之前触发。
     *
     * @param rule 当前的规则
     * @param facts 执行规则操作时的已知事实
     */
    @Override
    public void beforeExecute(Rule rule, Facts facts) {
        System.out.println("------beforeExecute-----" + rule.getName());
    }
}