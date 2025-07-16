package bronya.admin.module.db.antishake;

import java.util.concurrent.TimeUnit;

import com.alibaba.cola.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.dromara.hutool.core.lang.Assert;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Aspect // 标记为切面类
@Component // 让Spring管理这个Bean
public class AntiShakeAspect {
    // 使用Guava Cache实现防抖，设置缓存过期时间为1秒
    private final Cache<String, Object> debounceCache =
        CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS).build();

    @Around("@annotation(antiShake)") // 拦截所有标记了@AntiShake的方法
    public Object aroundAdvice(ProceedingJoinPoint joinPoint, AntiShake antiShake) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getTarget().getClass().getName();

        // 获取请求的IP地址
        HttpServletRequest request =
            ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();

        // 生成唯一标识（类名 + 方法名 + IP地址）
        String key = STR."\{className}:\{methodName}:\{ip}";
        Assert.isTrue(debounceCache.getIfPresent(key) == null, () -> new BizException("操作频繁,请稍后"));

        // 如果不存在，将标识存入缓存，并继续执行原方法
        debounceCache.put(key, new Object());
        return joinPoint.proceed();
    }
}