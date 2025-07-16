package bronya.admin.base.filter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
//@Component
@RequiredArgsConstructor
public class ControllerAspect {


    @Pointcut("execution(* *..*controller..*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object logHeaders(ProceedingJoinPoint joinPoint) throws Throwable {
        // ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // String trace = attributes.getRequest().getHeader(AdminBaseConstant.TRACE_ID);

        return joinPoint.proceed();
    }

    @Before("controllerPointcut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            // try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            // 不能每次都构建buildDefaultValidatorFactory,有锁,导致大并发请求进不来

            // 执行校验

            // }
        }
    }

    @AfterReturning("controllerPointcut()")
    public void afterReturning(JoinPoint joinPoint) {

    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "ex")
    public void afterException(JoinPoint joinPoint, Throwable ex) throws Throwable {
        throw ex;
    }
}