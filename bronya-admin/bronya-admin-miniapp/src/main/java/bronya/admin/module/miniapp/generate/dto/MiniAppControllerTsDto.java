package bronya.admin.module.miniapp.generate.dto;

import java.lang.reflect.Parameter;
import java.util.Set;

import org.dromara.hutool.http.meta.Method;

import lombok.Data;

@Data
public class MiniAppControllerTsDto {
    private Class<?> miniAppController;
    private String basePath;
    private Set<MiniAppTsMethodDto> tsMethods;

    @Data
    public static class MiniAppTsMethodDto {
        private String apiName;
        private Method method;
        private String methodPath;
        private Parameter requestBodyParameter;
        private Class<?> returnType;
    }
}
