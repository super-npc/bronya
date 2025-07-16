package bronya.admin.module.db.amis.util;

import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.mpe.base.repository.BaseRepository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseRepositoryUtil {
    public  <T> BaseRepository getService(Class<T> clazz) {
        return SpringUtil.getBean(STR."\{StrUtil.lowerFirst(clazz.getSimpleName())}Repository");
    }
}
