package bronya.core.base.constant;

import java.util.List;

import bronya.core.base.annotation.amis.inputdata.AmisConditionBuilder.ConditionColumn;

public interface IConditionBuilderTarget<T> {
    List<ConditionColumn> getConditionColumns(T openSearchCategory);
}
