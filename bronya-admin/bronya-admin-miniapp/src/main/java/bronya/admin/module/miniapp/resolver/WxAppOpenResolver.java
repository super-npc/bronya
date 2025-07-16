package bronya.admin.module.miniapp.resolver;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import bronya.shared.module.miniapp.AppOpen;
import bronya.shared.module.miniapp.annotation.LoginAppOpen;
import cn.hutool.core.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WxAppOpenResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginAppOpen.class) != null;
    }

    @Override
    public AppOpen resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer container,
                                   @NotNull NativeWebRequest request, WebDataBinderFactory factory) {

        String appId = request.getHeader("appId");
        String openId = request.getHeader("openId");
        Assert.notBlank(appId,"无法获取到appId");
        Assert.notBlank(openId,"无法获取到openId");
        return new AppOpen(appId,openId);
    }
}
