package bronya.admin.module.db.env.proxy;

import java.util.Map;

import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.env.domain.SysEnvObjField;
import bronya.admin.module.db.env.domain.SysEnvObjField.EnvPropertyExt;
import bronya.admin.module.db.env.domain.SysEnvObjField.EnvStatus;
import bronya.admin.module.db.env.repository.SysEnvObjFieldRepository;
import bronya.admin.module.db.env.service.SysEnvPropertyBizService;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysEnvObjFieldProxy extends DataProxy<SysEnvObjField> {
    private final SysEnvObjFieldRepository sysEnvObjFieldRepository;
    private final SysEnvPropertyBizService sysEnvPropertyBizService;

    @Override
    public void table(Map<String, Object> map) {
        SysEnvObjField sysEnvObjField = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SysEnvObjField.class);
        EnvPropertyExt envPropertyExt = new EnvPropertyExt();


        String key = sysEnvPropertyBizService.getKey(sysEnvObjField);
        String val = SpringUtil.getProperty(key);
        if (val == null) {
            envPropertyExt.setStatus(EnvStatus.NON_EXIST);
        } else {
            if (val.equals(sysEnvObjField.getDataValue())) {
                envPropertyExt.setStatus(EnvStatus.SYNC);
            } else {
                envPropertyExt.setStatus(EnvStatus.DIFF);
            }
        }
        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SysEnvObjField.class, envPropertyExt));
    }

    @Override
    public void afterUpdate(SysEnvObjField sysEnvObjField) {
        super.afterUpdate(sysEnvObjField);
        sysEnvPropertyBizService.update(sysEnvObjField);
    }
}
