package bronya.admin.module.db.amis.util;

import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseRepositoryUtil {
    public  <T> CrudRepository getService(Class<T> clazz) {
        return SpringUtil.getBean(STR."\{StrUtil.lowerFirst(clazz.getSimpleName())}Repository");
    }
}