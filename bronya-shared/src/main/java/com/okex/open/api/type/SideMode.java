package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SideMode implements AmisEnum {
    buy("买入", Color.淡珊瑚色), sell("卖出", Color.绿宝石);

    private final String desc;
    private final Color color;

    public static SideMode convert(String name) {
        return Arrays.asList(SideMode.values()).stream().filter(s -> s.name().equals(name)).findFirst().orElse(null);
    }
}
