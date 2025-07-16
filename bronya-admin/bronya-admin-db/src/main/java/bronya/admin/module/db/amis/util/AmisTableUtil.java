package bronya.admin.module.db.amis.util;

import org.dromara.hutool.core.lang.Assert;

import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.platform.dto.AmisBeanDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AmisTableUtil {

    public AmisBeanDto find(String beanSimpleName){
        Class<?> clazz = AdminBaseConstant.AMIS_TABLES.get(beanSimpleName);
        Assert.notNull(clazz, "未注册:{}", beanSimpleName);
        return new AmisBeanDto(clazz,clazz.getSimpleName());
    }
}
