package bronya.admin.module.db.env.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.env.domain.SysEnvObj;
import bronya.admin.module.db.env.domain.SysEnvObj.SysEnvObjExt;
import bronya.admin.module.db.env.repository.SysEnvObjRepository;
import bronya.core.base.dto.DataProxy;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysEnvObjProxy extends DataProxy<SysEnvObj> {
    private final SysEnvObjRepository sysEnvObjRepository;
    private final ConfigurableEnvironment environment;

    @Override
    public void table(Map<String, Object> map) {
        SysEnvObj sysEnvObj = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SysEnvObj.class);
        SysEnvObjExt sysEnvObjExt = new SysEnvObjExt();

        String pre = STR."\{StrUtil.replace(StrUtil.toUnderlineCase(sysEnvObj.getObjName()), "_", "-")}.";
        MutablePropertySources mutablePropertySources = environment.getPropertySources();
        PropertySource<?> propertySource = mutablePropertySources.get(sysEnvObj.getObjName());
        if (propertySource instanceof MapPropertySource mapPropertySource) {
            Map<String, Object> source = mapPropertySource.getSource();
            Map<String, Object> sourceNew = Maps.newHashMap();
            source.forEach((k, v) -> {
                sourceNew.put(StrUtil.removeAll(k, pre), v);
            });
            sysEnvObjExt.setEnv(JSONUtil.toJsonStr(sourceNew));
        }
        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SysEnvObj.class, sysEnvObjExt));
    }
}
