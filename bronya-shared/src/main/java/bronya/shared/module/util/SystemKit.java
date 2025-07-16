package bronya.shared.module.util;

import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.extra.spring.SpringUtil;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SystemKit {

    public String getApplicationName() {
        return SpringUtil.getProperty("spring.application.name");
    }

//    public String getVersion() {
//        final ProjectYaml yaml = SpringUtil.getBean(ProjectYaml.class);
//        return yaml.getVersion();
//    }

    public String getProfilesActive() {
        String property = SpringUtil.getProperty("spring.profiles.active");
        Assert.notBlank(property, "需要指定spring.profiles.active");
        return property;
    }
}
