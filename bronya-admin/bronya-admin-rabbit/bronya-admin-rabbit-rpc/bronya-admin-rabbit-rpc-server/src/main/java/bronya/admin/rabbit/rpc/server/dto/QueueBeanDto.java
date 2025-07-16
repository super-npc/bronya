package bronya.admin.rabbit.rpc.server.dto;

import java.lang.reflect.Method;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueBeanDto {
    private Class<?> beanClass;
    private Method method;
}