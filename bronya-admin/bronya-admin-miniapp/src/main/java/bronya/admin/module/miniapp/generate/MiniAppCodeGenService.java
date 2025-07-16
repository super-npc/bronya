package bronya.admin.module.miniapp.generate;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.dromara.hutool.core.annotation.AnnotationUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrPool;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import bronya.admin.module.miniapp.generate.dto.MiniAppControllerTsDto;
import bronya.admin.module.miniapp.generate.dto.MiniAppControllerTsDto.MiniAppTsMethodDto;
import bronya.admin.module.miniapp.util.JavaToTypeScriptUtil;
import bronya.admin.module.miniapp.util.JavaToTypeScriptUtil.InterfaceAndEnumStaticMethod;
import bronya.admin.module.miniapp.util.ScanMiniAppClient;
import bronya.shared.module.miniapp.MiniAppTypeScriptController;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ReflectUtil;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MiniAppCodeGenService {
    private final Class<?> rootClass;

    public void genTypeScriptCode() {
        // 获取所有controller以及每个控制器的方法
        Set<MiniAppControllerTsDto> miniAppControllerTsList = this.listController(rootClass);
        for (MiniAppControllerTsDto miniAppControllerTsDto : miniAppControllerTsList) {
            // 生成每个controller的ts文件
            this.genTsCode(miniAppControllerTsDto);
        }
    }

    private void genTsCode(MiniAppControllerTsDto miniAppController) {
        String basePath = miniAppController.getBasePath();
        // 1. 组装每个方法
        Set<MiniAppTsMethodDto> tsMethods = miniAppController.getTsMethods();
        Set<Class<?>> reqRespClass = Sets.newHashSet();
        Set<String> tsMethodContents = Sets.newHashSet(tsMethods.stream().map(tsMethod -> {
            String returnClass = tsMethod.getReturnType().getSimpleName();
            reqRespClass.add(tsMethod.getReturnType());
            String methodPath = tsMethod.getMethodPath();
            String methodType = tsMethod.getMethod().name().toLowerCase();
            // 收集请求参数/返回参数,生成ts.interface
            // 兼容请求无入参的情况
            String requestClass = Optional.of(tsMethod).map(MiniAppTsMethodDto::getRequestBodyParameter)
                    .map(Parameter::getType).map(Class::getSimpleName).orElse("");
            if (StrUtil.isNotBlank(requestClass)) {
                reqRespClass.add(tsMethod.getRequestBodyParameter().getType());
                return STR."""
                static \{tsMethod.getApiName()} = <\{returnClass}>(data: \{requestClass}) =>
                    httpRequest.\{methodType}<\{returnClass}>(
                      baseUrl + '\{basePath}\{methodPath}', data
                    )
                """;
            }
            // 无参数的情况
            return STR."""
                static \{tsMethod.getApiName()} = <\{returnClass}>() =>
                    httpRequest.\{methodType}<\{returnClass}>(
                      baseUrl + '\{basePath}\{methodPath}'
                    )
                """;
        }).collect(Collectors.toSet()));

        Set<String> tsCodeList = Sets.newHashSet();
        for (Class<?> respClass : reqRespClass) {
            InterfaceAndEnumStaticMethod interfaceAndEnumStaticMethod = JavaToTypeScriptUtil.genTypeScriptInterfaces(respClass);
            tsCodeList.addAll(interfaceAndEnumStaticMethod.getInterfaceCodes());
            tsMethodContents.addAll(interfaceAndEnumStaticMethod.getEnumStaticMethods());
        }
        // 生成收集的请求/返参 实体对象typeScript
        String respRespTypeScript = CollUtil.join(tsCodeList, StrPool.CR);
        // 2. 组装总体ts
        Class<?> miniAppControllerClass = miniAppController.getMiniAppController();
        String tsFileTemplate = STR."""
            import { httpRequest } from '../../utils/request'
            const baseUrl = require('../base').allBaseUrl.GDEnvs.host

            export default class \{miniAppControllerClass.getSimpleName()} {
              \{CollUtil.join(tsMethodContents, StrPool.CR)}
            }
            \{respRespTypeScript}
            """;
        // 写入同级package下对应的controller
        String beanName = miniAppControllerClass.getSimpleName();
        String controllerTsFile =
                STR."\{ResourceUtil.getResource(".", miniAppControllerClass).getPath()}\{beanName}.ts";
        FileUtil.writeUtf8String(tsFileTemplate, controllerTsFile);
    }

    private Set<MiniAppControllerTsDto> listController(Class<?> rootClass) {
        Set<Class<?>> miniAppControllers = ScanMiniAppClient.getMiniAppControllers(rootClass);
        // 处理每个controller
        return miniAppControllers.stream().map(miniAppController -> {
            RequestMapping controllerRequestMapping =
                    AnnotationUtil.getAnnotation(miniAppController, RequestMapping.class);
            String basePath = controllerRequestMapping.value()[0];
            MiniAppControllerTsDto miniAppControllerTsDto = new MiniAppControllerTsDto();
            miniAppControllerTsDto.setMiniAppController(miniAppController);
            miniAppControllerTsDto.setBasePath(basePath);
            miniAppControllerTsDto.setTsMethods(this.listMethod(miniAppController));
            return miniAppControllerTsDto;
        }).collect(Collectors.toSet());
    }

    private Set<MiniAppTsMethodDto> listMethod(Class<?> miniAppController) {
        List<Method> publicMethods = ReflectUtil.getPublicMethods(miniAppController,
                method -> AnnotationUtil.getAnnotation(method, MiniAppTypeScriptController.class) != null);
        log.info("小程序控制器:{},需要生成的接口数量:{}", miniAppController.getSimpleName(), publicMethods.size());
        return publicMethods.stream().map(publicMethod -> {
            // 一般只有一个requestBody封装体
            Parameter requestBodyParameter =
                    Lists.newArrayList(publicMethod.getParameters()).stream().filter(parameter -> {
                        return AnnotationUtil.getAnnotation(parameter, RequestBody.class) != null;
                    }).findFirst().orElse(null);
            Class<?> returnType = publicMethod.getReturnType();
            MiniAppTsMethodDto miniAppTsMethodDto = new MiniAppTsMethodDto();
            Tuple2<org.dromara.hutool.http.meta.Method, String> methodPath = this.getMethodPath(publicMethod);
            miniAppTsMethodDto.setApiName(publicMethod.getName());
            miniAppTsMethodDto.setMethod(methodPath._1());
            miniAppTsMethodDto.setMethodPath(methodPath._2());
            miniAppTsMethodDto.setRequestBodyParameter(requestBodyParameter);
            miniAppTsMethodDto.setReturnType(returnType);
            return miniAppTsMethodDto;
        }).collect(Collectors.toSet());
    }

    private Tuple2<org.dromara.hutool.http.meta.Method, String> getMethodPath(Method publicMethod) {
        PostMapping postMapping = AnnotationUtil.getAnnotation(publicMethod, PostMapping.class);
        if (postMapping != null) {
            return Tuple.of(org.dromara.hutool.http.meta.Method.POST, postMapping.value()[0]);
        }
        PutMapping putMapping = AnnotationUtil.getAnnotation(publicMethod, PutMapping.class);
        if (putMapping != null) {
            return Tuple.of(org.dromara.hutool.http.meta.Method.PUT, putMapping.value()[0]);
        }
        DeleteMapping deleteMapping = AnnotationUtil.getAnnotation(publicMethod, DeleteMapping.class);
        if (deleteMapping != null) {
            return Tuple.of(org.dromara.hutool.http.meta.Method.DELETE, deleteMapping.value()[0]);
        }
        GetMapping getMapping = AnnotationUtil.getAnnotation(publicMethod, GetMapping.class);
        if (getMapping != null) {
            return Tuple.of(org.dromara.hutool.http.meta.Method.GET, getMapping.value()[0]);
        }
        throw new RuntimeException(STR."小程序方法没有找到path:" + publicMethod.getName());
    }
}
