package bronya.admin.module.db.env.service;

import java.lang.reflect.Field;
import java.util.*;

import org.apache.commons.compress.utils.Lists;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.core.env.*;
import org.springframework.stereotype.Service;

import com.alibaba.cola.exception.SysException;

import bronya.admin.module.db.env.domain.SysEnvObj;
import bronya.admin.module.db.env.domain.SysEnvObjField;
import bronya.admin.module.db.env.repository.SysEnvObjFieldRepository;
import bronya.admin.module.db.env.repository.SysEnvObjRepository;
import bronya.core.base.annotation.amis.AmisField;
import bronya.shared.module.common.constant.AdminBaseConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysEnvPropertyBizService {
    private final RefreshScope refreshScope;
    private final ConfigurableEnvironment environment;
    private final SysEnvObjRepository envObjRepository;
    private final SysEnvObjFieldRepository fieldRepository;

    public void loadingEnvAndRegister() {
        log.info("加载环境变量");
        Set<Class<?>> classesAmisEnvBean = AdminBaseConstant.CLASSES_AMIS_ENV;
        for (Class<?> envClass : classesAmisEnvBean) {
            {
                // 检查环境变量合法性
                ConfigurationProperties configurationProperties = envClass.getAnnotation(ConfigurationProperties.class);
                Assert.notNull(configurationProperties, "系统变量对象未使用注解ConfigurationProperties:{}",
                    envClass.getSimpleName());
                String prefix = configurationProperties.prefix();
                String camelCase = StrUtil.toUnderlineCase(envClass.getSimpleName());
                String envName = StrUtil.replace(camelCase, "_", "-");
                Assert.isTrue(StrUtil.equals(prefix, envName), "环境变量前缀与类名不一致:{}.{}", envClass.getSimpleName(), envName);
            }
            SysEnvObj envObj = envObjRepository.find(envClass);
            Field[] fields = FieldUtil.getFields(envClass);
            List<SysEnvObjField> sysEnvObjFields = Lists.newArrayList();
            for (Field field : fields) {
                AmisField amisField = field.getAnnotation(AmisField.class);
                if (amisField == null) {
                    continue;
                }
                SysEnvObjField objField = fieldRepository.find(envObj, field);
                sysEnvObjFields.add(objField);
            }
            this.registerEnvValue(envObj, sysEnvObjFields);
        }
        log.info("加载环境变量完成");
    }

    public void registerEnvValue(SysEnvObj envObj, List<SysEnvObjField> objFields) {
        MutablePropertySources propertySources = environment.getPropertySources();
        Properties properties = new Properties();
        for (SysEnvObjField objField : objFields) {
            properties.setProperty(this.getKey(envObj, objField), objField.getDataValue());
        }
        propertySources.addFirst(new PropertiesPropertySource(envObj.getObjName(), properties));
        refreshScope.refresh(envObj.getObjName()); // 必须要这个bean的名称 驼峰首字母小写
        log.info("注册环境变量完成 {}.{}", envObj.getObjName(), properties);
    }

    public void update(SysEnvObjField objField) {
        SysEnvObj envObj = envObjRepository.getById(objField.getEnvObjId());
        String key = this.getKey(envObj, objField);
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        PropertySource<?> propertySource = mutablePropertySources.get(envObj.getObjName());
        if (propertySource instanceof MapPropertySource mapPropertySource) {
            Map<String, Object> source = mapPropertySource.getSource();
            if (source.get(key) != null) {
                // 覆盖新的值
                source.put(key, objField.getDataValue());
                environment.getPropertySources().replace(propertySource.getName(),
                    new OriginTrackedMapPropertySource(propertySource.getName(), source));
                // 刷新bean
                this.refresh(envObj, objField);
            }
        }
    }

    private void refresh(SysEnvObj envObj, SysEnvObjField objField) {
        refreshScope.refresh(envObj.getObjName()); // 必须要这个bean的名称 驼峰首字母小写
        log.info("刷新配置,重新加载配置bean {}.{} : {}", envObj.getObjName(), objField.getDataKey(), objField.getDataValue());
    }

    public String getKey(SysEnvObjField objField) {
        SysEnvObj envObj = envObjRepository.getById(objField.getEnvObjId());
        String envPrefix = this.getEnvPrefix(envObj);
        return STR."\{envPrefix}.\{objField.getDataKey()}";
    }

    public String getKey(SysEnvObj envObj, SysEnvObjField objField) {
        String envPrefix = this.getEnvPrefix(envObj);
        return STR."\{envPrefix}.\{objField.getDataKey()}";
    }

    public String getEnvPrefix(SysEnvObj envObj) {
        Optional<Class<?>> findEnvClassOpt = AdminBaseConstant.CLASSES_AMIS_ENV.stream()
            .filter(temp -> StrUtil.lowerFirst(temp.getSimpleName()).equals(envObj.getObjName())).findFirst();
        if (findEnvClassOpt.isEmpty()) {
            return null;
        }
        Class<?> envBeanClass = findEnvClassOpt.get();
        ConfigurationProperties configurationProperties = envBeanClass.getAnnotation(ConfigurationProperties.class);
        Assert.notNull(configurationProperties, "系统变量对象未使用注解ConfigurationProperties:{}", envBeanClass.getSimpleName());
        return configurationProperties.prefix();
    }

}
