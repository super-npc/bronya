package bronya.core.base.util;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.mpe.autotable.annotation.Table;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import bronya.core.base.constant.IThreadPoolEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class MyProjectClassScannerUtil {

    /**
     * 找出所有线程池类
     */
    public Set<Class<?>> scanThreadPools(Set<String> basePackages) {
        Set<Class<?>> classesThreadPools = Sets.newHashSet();
        for (String basePackage : basePackages) {
            Set<Class<?>> classesThreadPool = ClassUtil.scanPackage(basePackage,
                    clazz -> IThreadPoolEnum.class.isAssignableFrom(clazz) && !clazz.equals(IThreadPoolEnum.class));
            classesThreadPools.addAll(classesThreadPool);
        }
        return classesThreadPools;
    }

    /**
     * 扫描指定包路径下所有带有 @Table 注解的类
     *
     * @return <basePackage, className, class>
     */
    public HashBasedTable<String, String, Class<?>> scanTable(Set<String> basePackages) {
        HashBasedTable<String, String, Class<?>> table = HashBasedTable.create();
        Map<String, Class<?>> classNameTemp = Maps.newHashMap(); // 校验是否有重复的className
        for (String basePackage : basePackages) {
            Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Table.class);
            List<String> tableNames = classes.stream().map(Class::getSimpleName).toList();
            log.info("扫描表类,路径:{},数量:{},表:{}", basePackage, classes.size(), tableNames);
            for (Class<?> aClass : classes) {
                if (classNameTemp.containsKey(aClass.getSimpleName())) {
                    // 名称一致,但不同的class 需要告警,例如 a.C 跟 b.C
                    Class<?> sameNameClass = classNameTemp.get(aClass.getSimpleName());
                    // 同名,不同包名
                    Assert.isTrue(sameNameClass.getPackageName().equals(aClass.getPackageName()), "有重复的表类:{} | {}", aClass.getName(), sameNameClass.getName());
                }
                table.put(basePackage, aClass.getSimpleName(), aClass);
                classNameTemp.put(aClass.getSimpleName(), aClass);
            }
        }
        return table;
    }

}
