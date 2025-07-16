package bronya.admin.module.rule.core.ruletype;

public interface IRuleTypeClass {
    boolean checkValueCondition();

    String print(String printTemplate);
}
