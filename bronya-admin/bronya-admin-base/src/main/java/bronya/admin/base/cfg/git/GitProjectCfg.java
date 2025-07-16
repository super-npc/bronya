package bronya.admin.base.cfg.git;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Configuration
public class GitProjectCfg {

    @Value("classpath:git-project.properties")
    private Resource resource;

    private Properties properties;

    @PostConstruct
    public void init() throws IOException {
        properties = new Properties();
        try (InputStream is = resource.getInputStream()) {
            properties.load(is);
        }
    }

    public String getGitVersion() {
        return properties.getProperty("git.commit.id.full", "未获取到版本");
    }
}