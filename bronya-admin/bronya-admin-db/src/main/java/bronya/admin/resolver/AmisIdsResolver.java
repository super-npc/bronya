package bronya.admin.resolver;

import bronya.admin.annotation.AmisIds;
import bronya.admin.module.db.amis.dto.AmisIdsDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.split.SplitUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmisIdsResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AmisIds.class);
    }

    @SneakyThrows
    @Override
    public AmisIdsDto resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) {
        String idStr = request.getHeader("id");
        List<Long> ids = SplitUtil.split(idStr, ",").stream().map(Long::parseLong).toList();
        return new AmisIdsDto(ids);
    }
}
