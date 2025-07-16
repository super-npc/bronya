package bronya.admin.autoconfig;

import bronya.admin.annotation.AmisScan;
import bronya.admin.annotation.EnableMySchedule;
import bronya.admin.autoconfig.scan.env.AmisEnvAnnotationBeanNameGenerator;
import bronya.admin.autoconfig.scan.env.AmisEnvClassPathBeanDefinitionScanner;
import bronya.admin.base.constant.AmisBaseConstant;
import bronya.core.base.util.MyProjectClassScannerUtil;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.rabbit.MyRabbitApi;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.mpe.autotable.annotation.Table;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AmisScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(@NotNull ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
        @NotNull BeanDefinitionRegistry beanDefinitionRegistry) {
        // 获取所有注解的属性和值
        AnnotationAttributes amisScan =
            AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(AmisScan.class.getName()));
        AnnotationAttributes enableMySchedule =
            AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableMySchedule.class.getName()));
        if (enableMySchedule != null) { // 这里的idea检测有问题,有可能是null
            AmisBaseConstant.ENABLE_SCHEDULED = true;
        }
        // 获取到basePackage的值
        String[] basePackage = amisScan.getStringArray("basePackage");
        Set<String> basePackages =
            Lists.newArrayList(basePackage).stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet());
        this.doTables(basePackages);
        this.registerThreadPool(basePackages);
        this.findRabbitRpc(basePackages);

        // 自定义的包amis.env扫描器
        AmisEnvClassPathBeanDefinitionScanner scanHandle =
            new AmisEnvClassPathBeanDefinitionScanner(beanDefinitionRegistry, false);
        scanHandle.setResourceLoader(resourceLoader);
        scanHandle.setBeanNameGenerator(new AmisEnvAnnotationBeanNameGenerator());
        // 扫描指定路径下的接口
        scanHandle.doScan(basePackage);
    }

    private void findRabbitRpc(Set<String> basePackages){
        for (String basePackage : basePackages) {
            Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, MyRabbitApi.class);
            AdminBaseConstant.CLASSES_RABBIT_RPC.addAll(classes);
        }
    }

    // 注册线程池到系统中
    private void registerThreadPool(Set<String> basePackages) {
        Set<Class<?>> classesThreadPools = MyProjectClassScannerUtil.scanThreadPools(basePackages);
        // final SysThreadPoolService threadPoolService = SpringUtil.getBean(SysThreadPoolService.class);
        // 此刻服务还未注入到容器中,需要存起来,等服务启动后执行
        AdminBaseConstant.CLASSES_THREAD_POOLS.addAll(classesThreadPools);
    }

    private void doTables(Set<String> basePackages) {
        HashBasedTable<String, String, Class<?>> scan = MyProjectClassScannerUtil.scanTable(basePackages);
        Map<String, Map<String, Class<?>>> tableMapMap = scan.rowMap();
        tableMapMap.forEach((packageName, map) -> {
            List<String> tableClassNames = map.values().stream().map(Class::getSimpleName).toList();
            log.info("扫描table,路径:{},数量:{},表类:{}", packageName, map.size(), CollUtil.join(tableClassNames, ","));
            map.forEach((name, clasz) -> {
                if (AdminBaseConstant.AMIS_TABLES.containsKey(name)) {
                    // throw new RuntimeException(STR."存在重复的表名:\{name}");
                    log.warn(STR."存在重复的表名:\{name}");
                    return;
                }
                AdminBaseConstant.AMIS_TABLES.put(name, clasz);
            });
        });
    }
}