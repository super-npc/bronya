package bronya.admin.module.openfeign.interceptor;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

import com.alibaba.cola.exception.BizException;
import com.alibaba.cola.exception.SysException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {
    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 200) {
            return null;
        } else if (response.status() == 503) {
            return new SysException(STR."远程服务器不可用:\{response.request().requestTemplate().feignTarget().name()}");
        } else {
                String json = IOUtils.toString(response.body().asReader(StandardCharsets.UTF_8));
            String msg =
                Optional.ofNullable(JSONUtil.parse(json).getByPath("msg")).map(Object::toString).orElse("内部服务器错误");
            return new BizException(msg);
        }
    }
}