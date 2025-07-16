package bronya.core.base.annotation.amis.type;

import java.util.Map;

import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.http.meta.Method;

import lombok.experimental.Accessors;

@lombok.Data
@Accessors(chain = true)
public class AmisApi {
    private final Method method;
    private final String url;
    private Map<String, Object> headers;
    private Map<String, Object> data = MapUtil.newHashMap();
    private String responseType;

    /**
     * 可以配置表达式sendOn来实现：当符合某个条件的情况下，接口才触发请求
     */
    private String sendOn;

    public AmisApi(Method method, String url) {
        this.method = method;
        this.url = url;
        this.data.put("&","$$");
    }

    public AmisApi(Class<?> baseEntity, Method method, String url) {
        this.method = method;
        this.url = url;
        this.headers = MapUtil.newHashMap();
        this.headers.put("bean", baseEntity.getSimpleName());
        this.data.put("&","$$");
    }
}