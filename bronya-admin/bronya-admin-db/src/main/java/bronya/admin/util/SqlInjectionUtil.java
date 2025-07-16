package bronya.admin.util;

import java.util.Map;

import org.dromara.hutool.core.bean.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlInjectionUtil {
    private static final Logger log = LoggerFactory.getLogger("SQL_INJECTION_FILE_NAME");

    public void checkInjection(String val) {
        if (SqlInjectionUtils.check(val)) {
            return;
        }
        log.error("有注入风险:{}", val);
//        throw new BizException("系统异常,有注入风险,请稍后重试");
    }

    public void checkInjection(Object val) {
        checkInjection(BeanUtil.beanToMap(val));
    }
    public void checkInjection(Map<String, Object> map) {
        map.forEach((k, v) -> {
//            checkInjection(k);
            if (v instanceof String value) {
                checkInjection(value);
            }
        });
    }
}
