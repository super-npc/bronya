package bronya.admin.resolver;

import bronya.admin.module.db.amis.util.AmisTableUtil;
import org.dromara.hutool.core.lang.Assert;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import bronya.admin.annotation.AmisBean;
import bronya.shared.module.platform.dto.AmisBeanDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmisBeanResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AmisBean.class);
    }

    @SneakyThrows
    @Override
    public AmisBeanDto resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) {
        String bean = request.getHeader("bean");
        Assert.notNull(bean, "缺少参数header.bean");
        return AmisTableUtil.find(bean);
    }
}
