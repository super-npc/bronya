package bronya.admin.module.db.amis.service.field.converter;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;

import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import bronya.admin.module.db.amis.service.field.converter.impl.AmisFieldDateConvert;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmisFiledConvertSwitch {

    public Map<String, Object> request(Class<?> mainClass, Object obj) {
        Map<String, Object> bean = BeanUtil.beanToMap(obj);
        // 对每个字段类型进行匹配转换
        Field[] fields = FieldUtil.getFields(mainClass);
        Map<String, Object> newBean = Maps.newHashMap();
        for (Field field : fields) {
            String name = field.getName();
            Object value = bean.get(name);
            if (value instanceof String str) {
                value = CharSequenceUtil.trim(str);
            }
            if (field.getType() == Date.class) {
                IAmisFiledConvert convert = new AmisFieldDateConvert();
                newBean.put(name, convert.request(value));
                continue;
            }
            newBean.put(name, value);
        }
        return newBean;
    }
}
