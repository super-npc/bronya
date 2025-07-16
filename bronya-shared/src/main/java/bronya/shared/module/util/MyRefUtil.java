package bronya.shared.module.util;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.reflect.ClassUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Slf4j
@UtilityClass
public class MyRefUtil {
    public Set<Class<?>> findAnnotation(String rootPackage, Class<? extends Annotation> annotationClass) {
        Set<Class<?>> annotationClasses = ClassUtil.scanPackageByAnnotation(rootPackage, annotationClass);
        log.info(STR."扫描注解 - 包路径:\{rootPackage},查找:\{annotationClass.getSimpleName()},找到数量:\{annotationClasses.size()}");
        return annotationClasses;
    }

    /**
     * 获取泛型class
     */
    public String getGenericClass(Object bean) {
        return ((ParameterizedType)bean.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0].getTypeName();
    }

    /**
     * 获取指定类及其超类中所有泛型参数的列表。 该方法用于遍历类的继承树，直到找到第一个带有泛型参数的超类为止。 如果类或其超类没有泛型参数，则返回空列表。
     *
     * @param aClass 需要查询的类
     * @return 返回一个包含所有泛型参数的列表。如果不存在泛型参数，则返回空列表。
     */
    // 通过一个 while 循环，遍历类继承树，直到找到一个 ParameterizedType，然后获取其泛型类型参数。获取该继承类的所有泛型对象
    public List<Type> getAllGenericClassByExtendClass(Class<?> aClass) {
        // 初始化一个列表，用于存储所有泛型参数
        List<Type> typeArgumentReturn = Lists.newArrayList();
        // 当当前类的超类不为空时，继续遍历
        while (aClass.getSuperclass() != null) {
            // 获取当前类的超类的泛型类型
            Type superClass = aClass.getGenericSuperclass();
            // 如果超类是参数化类型，则处理其泛型参数
            if (superClass instanceof ParameterizedType parameterizedType) {
                // 获取超类的所有泛型参数
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                // 将泛型参数添加到结果列表中
                typeArgumentReturn.addAll(Lists.newArrayList(typeArguments));
                // 找到泛型参数后，停止遍历
                break;
            }
            // 如果当前超类不是参数化类型，则继续向上遍历超类
            aClass = aClass.getSuperclass();
        }
        // 返回所有泛型参数的列表
        return typeArgumentReturn;
    }

}
