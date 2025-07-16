package bronya.admin.module.db.togglz.cfg;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EnvFeature implements Feature, AmisEnum {
    @EnabledByDefault
    @Label("开发环境") DEV("开发环境", Color.军校蓝),

    @Label("测试环境") TEST("测试环境", Color.军校蓝),

    @Label("预发布环境") UAT("预发布环境", Color.军校蓝),

    @Label("生产环境") PROD("生产环境", Color.军校蓝);

    private final String desc;
    private final Color color;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

    public static EnvFeature getEnv(String env) {
        return Arrays.stream(EnvFeature.values()).filter(temp -> temp.name().equals(env)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("env参数不合法"));
    }
}
