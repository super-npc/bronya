package bronya.starter.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import bronya.admin.annotation.AmisScan;
import bronya.admin.base.handler.GlobExceptionHandler;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AmisScan(basePackage = {"bronya"})
@EnableWebMvc
//@EnableAutoTable(basePackages = {"bronya"})
@AutoConfiguration
@ComponentScan({"bronya","olive", "org.dromara.hutool.extra.spring", "com.alibaba.cola"})
@MapperScan({"bronya","olive",})
@RestControllerAdvice(basePackageClasses = {GlobExceptionHandler.class})
public class BronyaStarterDbConfiguration {
    @PostConstruct
    public void init() {
        // 禁用,否则testcontainers无法在容器中使用
        System.setProperty("TESTCONTAINERS_RYUK_DISABLED", "true");
        log.info("BronyaSpringBoot3StarterAutoConfiguration 启动完成");
    }
}