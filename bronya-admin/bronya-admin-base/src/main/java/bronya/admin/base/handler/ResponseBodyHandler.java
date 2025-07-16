package bronya.admin.base.handler;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.google.common.collect.Lists;

import bronya.admin.base.annotation.NotResponseBodyAdvice;
import bronya.shared.module.common.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType,
        @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        // response是ResultVO类型，或者 贴上了@NotControllerResponseAdvice注解 都不进行包装
        return !(returnType.getParameterType().isAssignableFrom(ResultVO.class)
            || returnType.hasMethodAnnotation(NotResponseBodyAdvice.class));
    }

    @NotNull
    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter returnType,
        @NotNull MediaType selectedContentType, @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
        @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        // 如果是 actuator 请求，直接返回
        if (isActuatorRequest(request)) {
            return body;
        }

        // if (Objects.requireNonNull(request.getHeaders().get("user-agent")).get(0).startsWith("Java")) {
        // 如果是 Feign 请求，直接返回
        // return body;
        // }
        // 提供一定的灵活度，如果body已经被包装了，就不进行包装
        if (body instanceof ResultVO<?> temp) {
            return temp;
        }
        return ResultVO.success(body);
    }

    private boolean isActuatorRequest(ServerHttpRequest request) {
        List<String> strings = Lists.newArrayList("/favicon.ico", "/actuator", "/koTime");
        if (request instanceof ServletServerHttpRequest httpRequest) {
            return strings.stream().anyMatch(temp -> httpRequest.getServletRequest().getRequestURI().startsWith(temp));
        }
        return false;
    }
}
