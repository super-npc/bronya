package bronya.admin.module.rabbit.module.fanout.util;

import org.dromara.hutool.core.text.StrUtil;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FanoutUtil {
    /**
     * 以发送的实体类作为交换机名称
     */
    public String getExchangeName(Class<?> aClass) {
        return StrUtil.lowerFirst(aClass.getSimpleName());
    }
}
