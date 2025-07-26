package bronya.admin.module.db.env.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.annotation.AmisEnv;
import bronya.admin.module.db.env.domain.SysEnvObj;
import bronya.admin.module.db.env.mapper.SysEnvObjMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SysEnvObjRepository extends CrudRepository<SysEnvObjMapper, SysEnvObj> {

    public SysEnvObj find(Class<?> envClass){
        String objName = StrUtil.toCamelCase(envClass.getSimpleName(), '-');
        LambdaQueryWrapper<SysEnvObj> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysEnvObj::getObjName,objName);
        SysEnvObj envObj = this.getOne(wrapper);
        if(envObj == null){
            AmisEnv amisEnv = envClass.getAnnotation(AmisEnv.class);
            envObj = new SysEnvObj();
            envObj.setObjName(objName);
            envObj.setDescription(amisEnv.description());
            this.save(envObj);
        }
        Assert.notNull(envObj.getId());
        return envObj;
    }
}
