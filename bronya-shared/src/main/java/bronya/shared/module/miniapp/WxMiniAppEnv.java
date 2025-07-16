package bronya.shared.module.miniapp;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum WxMiniAppEnv implements AmisEnum {
    develop("开发", Color.卡其布),
    trial("体验", Color.天蓝色),
    release("生产", Color.午夜的蓝色);

    private final String desc;
    private final Color color;

    public static WxMiniAppEnv getByName(String name) {
        return Arrays.stream(WxMiniAppEnv.values()).filter(e -> e.name().equals(name)).findFirst().orElse(null);
    }
}