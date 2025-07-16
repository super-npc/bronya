package bronya.admin.resolver;

import bronya.admin.base.annotation.AmisSite;
import bronya.shared.module.platform.dto.AmisSiteDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.net.url.UrlDecoder;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class SiteResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AmisSite.class);
    }

    @SneakyThrows
    @Override
    public AmisSiteDto resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) {
        String site = request.getHeader("site");
        site = UrlDecoder.decode(site); // header不支持 含中文或其他非 ISO-8859-1 字符, 需要解码
        return new AmisSiteDto(site);
    }
}
