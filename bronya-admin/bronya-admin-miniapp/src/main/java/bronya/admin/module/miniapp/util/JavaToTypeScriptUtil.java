package bronya.admin.module.miniapp.util;


import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrPool;
import org.dromara.mpe.annotation.Exclude;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import bronya.admin.base.util.CustomFieldFinder;
import bronya.shared.module.common.type.AmisEnum;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JavaToTypeScriptUtil {

    private static final Map<Class<?>, String> primitiveTypeMappings = new HashMap<>();

    public InterfaceAndEnumStaticMethod genTypeScriptInterfaces(Class<?> javaClass) {
        // 存储所有生成的接口
        Map<String, StringBuilder> interfaces = new LinkedHashMap<>();

        // 生成TypeScript interface
        generateTypeScriptInterface(javaClass, interfaces);

        Set<Class<?>> findClasses = CustomFieldFinder.findCustomClasses(javaClass);
        Set<Class<?>> customEnums = Sets.newHashSet();
        // 递归出所有javaClass的实体类,需要生成interfaces
        for (Class<?> innerClass : findClasses) {
            generateTypeScriptInterface(innerClass, interfaces);
            // 从每个类提取出枚举字段
            List<? extends Class<?>> enumClass =
                    Arrays.stream(ReflectUtil.getFields(innerClass, field -> EnumUtil.isEnum(field.getType())))
                            .map(Field::getType).toList();
            CollUtil.addAll(customEnums, enumClass);
        }

        // 打印所有ts,实体类,枚举
        Set<String> interfaceCodes = Sets.newHashSet();
        interfaces.forEach((name, body) -> {
            String interfaceTemplate = STR."""
                export interface \{name} {
                \{body}
                }
                """;
            interfaceCodes.add(interfaceTemplate);
        });
        // 枚举转ts枚举
        Set<String> enumStaticMethods = Sets.newHashSet();
        customEnums.forEach(customEnum -> {
            if (AmisEnum.class.isAssignableFrom(customEnum)) {
                Object[] enumConstants = customEnum.getEnumConstants();
                Set<String> fieldEnum = Sets.newHashSet();
                Set<String> mapKeyVal = Sets.newHashSet();
                for (Object enumConstant : enumConstants) {
                    if (enumConstant instanceof AmisEnum amisEnum) {
                        String enumName = ReflectUtil.invoke(enumConstant, "name").toString();
                        String desc = amisEnum.getDesc();
                        String rgb = amisEnum.getColor().getRgb();
                        fieldEnum.add(STR."""
                                /** \{desc} */
                                \{enumName},
                            """);
                        mapKeyVal.add(STR."""
                            "\{enumName}": {desc:"\{desc}",color:"\{rgb}"},
                            """);
                        // 实现枚举对应的静态方法
                        enumStaticMethods.add(STR."""
                                  static get\{customEnum.getSimpleName()}Details = (enumStrKey: string): typeof \{customEnum.getSimpleName()}Details[keyof typeof \{customEnum.getSimpleName()}Details] => {
                                    return \{customEnum.getSimpleName()}Details[enumStrKey as keyof typeof \{customEnum.getSimpleName()}Details];
                                  }
                                """);
                    }
                }
                String enumTemplate = STR."""
                     export enum \{customEnum.getSimpleName()}{
                         \{CollUtil.join(fieldEnum, StrPool.CR)}
                     }
                     export const \{customEnum.getSimpleName()}Details = {
                       \{CollUtil.join(mapKeyVal, StrPool.CR)}
                     };
                     """;
                interfaceCodes.add(enumTemplate);
            }
        });
        return new InterfaceAndEnumStaticMethod(interfaceCodes,enumStaticMethods);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InterfaceAndEnumStaticMethod {
        // 所有ts,实体类,枚举
        private Set<String> interfaceCodes;
        // 枚举对应获取的静态方法
        private Set<String> enumStaticMethods;
    }

    private void generateTypeScriptInterface(Class<?> javaClass, Map<String, StringBuilder> interfaces) {
        if (EnumUtil.isEnum(javaClass)) {
            // 枚举不生成ts.interface
            return;
        }
        String className = getTypeName(javaClass);
        StringBuilder body = interfaces.computeIfAbsent(className, k -> new StringBuilder());

        List<Field> fields = Lists.newArrayList(
                ReflectUtil.getFields(javaClass, field -> AnnotationUtil.getAnnotation(field, Exclude.class) == null));
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String tsType = getTsType(field.getGenericType(), field.getType(), interfaces);
            body.append("  ").append(field.getName()).append("?: ").append(tsType);
            if (fields.getLast() == field) {
                body.append(";");
            } else {
                body.append(";\n");
            }
        }
    }

    private String getTypeName(Class<?> javaClass) {
        if (javaClass.getEnclosingClass() != null && !javaClass.isAnonymousClass()) {
            return javaClass.getSimpleName();
        } else {
            return javaClass.getSimpleName();
        }
    }

    private String getTsType(Type genericType, Type fieldType, Map<String, StringBuilder> interfaces) {
        if (genericType instanceof Class<?> clazz) {
            if (primitiveTypeMappings.containsKey(clazz)) {
                return primitiveTypeMappings.get(clazz);
            } else if (clazz.isArray()) {
                return STR."\{getTsType(clazz.getComponentType(), clazz, interfaces)}[]";
            } else {
                return getTypeName(clazz);
            }
        } else if (genericType instanceof ParameterizedType paramType) {
            Type rawType = paramType.getRawType();
            if (rawType.equals(List.class)) {
                Type[] typeArguments = paramType.getActualTypeArguments();
                if (typeArguments.length > 0) {
                    return STR."\{getTsType(typeArguments[0], typeArguments[0], interfaces)}[]";
                }
            }
        } else if (genericType instanceof GenericArrayType genericType1) {
            Type componentType = genericType1.getGenericComponentType();
            return STR."\{getTsType(componentType, componentType, interfaces)}[]";
        } else if (genericType instanceof TypeVariable<?> typeVariable) {
            return typeVariable.getName();
        }
        return "any";
    }

    static {
        primitiveTypeMappings.put(int.class, "number");
        primitiveTypeMappings.put(boolean.class, "boolean");
        primitiveTypeMappings.put(double.class, "number");
        primitiveTypeMappings.put(float.class, "number");
        primitiveTypeMappings.put(long.class, "number");
        primitiveTypeMappings.put(short.class, "number");
        primitiveTypeMappings.put(byte.class, "number");
        primitiveTypeMappings.put(char.class, "string");
        primitiveTypeMappings.put(BigDecimal.class, "number");
        primitiveTypeMappings.put(Integer.class, "number");
        primitiveTypeMappings.put(Boolean.class, "boolean");
        primitiveTypeMappings.put(Double.class, "number");
        primitiveTypeMappings.put(Float.class, "number");
        primitiveTypeMappings.put(Long.class, "number");
        primitiveTypeMappings.put(Short.class, "number");
        primitiveTypeMappings.put(Byte.class, "number");
        primitiveTypeMappings.put(Character.class, "string");
        primitiveTypeMappings.put(String.class, "string");
        primitiveTypeMappings.put(List.class, "any[]"); // Default to any[] for lists
    }
}