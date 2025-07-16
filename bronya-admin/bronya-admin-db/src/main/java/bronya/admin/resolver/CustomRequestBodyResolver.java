//package bronya.admin.resolver;
//
//import java.util.Set;
//
//import org.dromara.hutool.core.collection.CollUtil;
//import org.springframework.core.MethodParameter;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.support.WebDataBinderFactory;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import bronya.admin.base.exception.ParamException;
//import jakarta.validation.ConstraintViolation;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class CustomRequestBodyResolver implements HandlerMethodArgumentResolver {
//    private final LocalValidatorFactoryBean validator;
//
//    @Override
//    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(RequestBody.class);
//    }
//
//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        Object arg = binderFactory.createBinder(webRequest, null, parameter.getParameterName()).getTarget();
////         if (parameter.hasParameterAnnotation(Valid.class)) { // 检查参数是否带有@Valid注解
//        validate(arg);
//        return arg;
//    }
//
//    private void validate(Object arg) {
//        if (arg == null) {
//            return;
//        }
//        Set<ConstraintViolation<Object>> violations = validator.validate(arg);
//        // 检查校验结果
//        if (!violations.isEmpty()) {
//            ConstraintViolation<Object> violation = CollUtil.getFirst(violations);
//            throw new ParamException(violation.getMessage());
//        }
//    }
//}