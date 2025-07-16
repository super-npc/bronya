package bronya.admin.base.util;

import bronya.shared.module.util.MyClassFieldUtil;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.mpe.annotation.Exclude;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class CustomFieldFinder {

    public Set<Class<?>> findCustomClasses(Class<?> javaClass) {
        Set<Class<?>> customClasses = new HashSet<>();
        findCustomClassesRecursive(javaClass, customClasses);
        return customClasses;
    }

    private void findCustomClassesRecursive(Class<?> javaClass, Set<Class<?>> customClasses) {
        if (javaClass == null || javaClass.isPrimitive() || javaClass.isArray() || ClassUtil.isJdkClass(javaClass)) {
            return;
        }

        Field[] fields = javaClass.getDeclaredFields();
        for (Field field : fields) {
            Exclude transientAnnotation = AnnotationUtil.getAnnotation(field, Exclude.class);
            if (transientAnnotation != null) {
                // 数据库不持久化不生成ts字段
                continue;
            }
            if (!Modifier.isStatic(field.getModifiers())) { // 排除静态字段
                Class<?> fieldType = field.getType();
                if (Lists.newArrayList(List.class, Set.class).contains(fieldType)) {
                    Class<?> list字段泛型 = MyClassFieldUtil.getList字段泛型(field);
                    if (!ClassUtil.isJdkClass(list字段泛型)) {
                        customClasses.add(list字段泛型);
                    }
                } else if (!ClassUtil.isJdkClass(fieldType)) {
                    customClasses.add(fieldType);
                }
                // 如果字段是集合类型，递归查找泛型参数
                if (fieldType.isArray() || List.class.isAssignableFrom(fieldType)) {
                    Type genericType = field.getGenericType();
                    if (genericType instanceof ParameterizedType paramType) {
                        Type[] argTypes = paramType.getActualTypeArguments();
                        for (Type argType : argTypes) {
                            if (argType instanceof Class) {
                                findCustomClassesRecursive((Class<?>)argType, customClasses);
                            }
                        }
                    }
                }
                findCustomClassesRecursive(fieldType, customClasses); // 递归查找字段类型
            }
        }

        // 检查父类
        Class<?> superclass = javaClass.getSuperclass();
        if (superclass != null) {
            findCustomClassesRecursive(superclass, customClasses);
        }

        // 检查接口
        Class<?>[] interfaces = javaClass.getInterfaces();
        for (Class<?> iface : interfaces) {
            findCustomClassesRecursive(iface, customClasses);
        }
    }

//    public static void main(String[] args) {
//        Set<Class<?>> customClasses = findCustomClasses(TestRequest.class);
//        for (Class<?> clazz : customClasses) {
//            System.out.println(clazz.getName());
//        }
//    }
}