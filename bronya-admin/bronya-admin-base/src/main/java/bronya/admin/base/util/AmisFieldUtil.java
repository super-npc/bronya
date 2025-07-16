package bronya.admin.base.util;

import java.lang.reflect.Field;

import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.mpe.autotable.annotation.ColumnId;

import com.alibaba.cola.exception.SysException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AmisFieldUtil {

    public String findPrimaryKey(Class<?> amisBeanClass) {
        for (Field field : FieldUtil.getFields(amisBeanClass)) {
            if (field.getAnnotation(ColumnId.class) != null) {
                return field.getName();
            }
        }
        throw new SysException("未找到主键:{}", amisBeanClass.getName());
    }
}
