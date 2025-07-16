package bronya.core.base.util;

import org.dromara.mpe.autotable.annotation.Column;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequiredUtil {
    public Boolean isRequired(Column column){
        if(column == null){
            return null;
        }
        return column.notNull() ? true : null;
    }
}
