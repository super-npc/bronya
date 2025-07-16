package bronya.shared.module.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.dromara.hutool.core.lang.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@UtilityClass
public class MyClassFieldUtil {

    @SneakyThrows
    public Class<?> get字段类型Class(Field field) {
        return Class.forName(field.getGenericType().getTypeName());
    }

    /**
     * 获取List字段泛型, 例如: List<Dog>, 则返回Dog.class
     *
     * @param field 必须为List/Set
     * @return 集合的泛型
     */
    public Class<?> getList字段泛型(Field field) {
        // 判断fc是否和List相同或者其父类
        Assert.isTrue(field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(Set.class),
                STR."一对多字段字段非List/Set类型,类:\{field.getDeclaringClass().getName()}字段:\{field.getName()}");
        // 如果是List类型，得到其Generic的类型
        Type fc = field.getGenericType();
        // Assert.isTrue(fc instanceof ParameterizedType, "字段无法获取泛型:${field.getName()}");
        if (fc instanceof ParameterizedType pt) {
            // 得到泛型里的class类型对象。
            return (Class<?>)pt.getActualTypeArguments()[0];
        }
        return null;
    }
}
