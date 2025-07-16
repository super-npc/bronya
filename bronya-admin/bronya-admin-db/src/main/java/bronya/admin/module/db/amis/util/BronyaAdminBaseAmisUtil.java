package bronya.admin.module.db.amis.util;

import java.util.Map;
import java.util.Set;

import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.text.StrPool;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.split.SplitUtil;

import com.google.common.collect.Maps;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BronyaAdminBaseAmisUtil {

    public Object map2obj(Map<String, Object> map) {
        Map<String, Object> mapTarget = Maps.newHashMap();
        map.forEach((field, value) -> {
            String key = SplitUtil.split(field, StrPool.DOT).getLast();
            mapTarget.put(key, value);
        });
        return mapTarget;
    }

    public Map<String, Object> obj2map(Class<?> mainClass, Object record) {
        Map<String, Object> mapSource = BeanUtil.beanToMap(record);
        Map<String, Object> mapTarget = Maps.newHashMap();
        Set<String> fieldKeys = mapSource.keySet();
        for (String fieldKey : fieldKeys) {
            Object remove = mapSource.get(fieldKey);
            if (fieldKey.equals("id")) {
                mapTarget.put(fieldKey, remove);
            }
            mapTarget.put(STR."\{mainClass.getSimpleName()}.\{StrUtil.toCamelCase(fieldKey)}", remove);
        }
        return mapTarget;
    }
}
