package bronya.starter.base;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import bronya.admin.base.handler.GlobExceptionHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @AmisScan(basePackage = {"bronya"})
@EnableWebMvc
// @EnableAutoTable(basePackages = {"bronya"}) // 需要在具体项目上设置,这里设置会有可能无法扫描到父项目的table类
@AutoConfiguration
@ComponentScan({"bronya","olive", "org.dromara.hutool.extra.spring", "com.alibaba.cola"})
@MapperScan({"bronya","olive",})
@RestControllerAdvice(basePackageClasses = {GlobExceptionHandler.class})
@RequiredArgsConstructor
public class BronyaStarterBaseConfiguration {
    // private final SpringYaml springYaml;

    @PostConstruct
    public void init() {
        // 禁用,否则testcontainers无法在容器中使用
        System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");
        // String appName = Optional.ofNullable(springYaml.getApplication()).map(SpringYaml.Application::getName)
        // .orElseThrow(() -> new SysException("未配置app名称"));
        // System.setProperty("project.name", appName); // 给logback-config.xml日志使用的 无效，需要在main方法设置
        log.info("BronyaSpringBoot3StarterAutoConfiguration 启动完成");
    }
}