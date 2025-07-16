package bronya.admin.module.rabbit.module.rpc.util;

import java.lang.reflect.Method;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RabbitRpcUtil {
    private final String pre = "_rpc_";

    public String getRpcQueueName(Class<?> beanClass, Method method) {
        return STR."\{pre}\{beanClass.getSimpleName()}_\{method.getName()}_\{method.getParameterCount()}";
    }
}
