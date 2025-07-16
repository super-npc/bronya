package bronya.admin.resolver;

import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.user.SysUserVo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import bronya.admin.annotation.LoginDto;
import bronya.admin.module.db.amis.dto.AmisLoginUser;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserResolver implements HandlerMethodArgumentResolver {
    private final IPlatform platformService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginDto.class);
    }

    @SneakyThrows
    @Override
    public AmisLoginUser resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) {
//        String token = request.getHeader("token");
        Optional<SysUserVo> loginInfo = platformService.getLoginInfo();
        return new AmisLoginUser(loginInfo);
    }
}
