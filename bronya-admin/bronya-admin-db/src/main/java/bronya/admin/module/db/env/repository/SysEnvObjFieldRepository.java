package bronya.admin.module.db.env.repository;

import java.lang.reflect.Field;

import org.dromara.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import bronya.admin.module.db.env.domain.SysEnvObj;
import bronya.admin.module.db.env.domain.SysEnvObjField;
import bronya.admin.module.db.env.mapper.SysEnvObjFieldMapper;
import bronya.core.base.annotation.amis.AmisField;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SysEnvObjFieldRepository extends CrudRepository<SysEnvObjFieldMapper, SysEnvObjField> {

    public SysEnvObjField find(SysEnvObj envObj, Field field) {
        AmisField amisField = field.getAnnotation(AmisField.class);
        String fieldName = field.getName();
        Assert.notNull(amisField, "字段非系统变量:{}.{}", envObj.getClass().getSimpleName(), fieldName);

        LambdaQueryWrapper<SysEnvObjField> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysEnvObjField::getEnvObjId, envObj.getId());
        wrapper.eq(SysEnvObjField::getDataKey, fieldName);
        SysEnvObjField objField = this.getOne(wrapper);
        if (objField == null) {
            objField = new SysEnvObjField();
            objField.setEnvObjId(envObj.getId());
            objField.setDataKey(fieldName);
            objField.setDataValue(amisField.envValue());
            objField.setDescription(amisField.comment());
            this.save(objField);
        }
        Assert.notNull(objField.getId());
        return objField;
    }
}
