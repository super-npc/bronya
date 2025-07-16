package com.okex.open.api.type;

import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Mode {
    trendTracking(1, "趋势跟踪", Color.兰花的紫色), trendWave(2, "波动", Color.午夜的蓝色), hold(3, "持有", Color.卡其布),
    shortSelling(5, "沽空", Color.天蓝色);

    private final Integer key;
    private final String cn;
    private final Color color;

    public static Mode convert(String value) {
        return Arrays.stream(Mode.values()).filter(a -> a.name().equals(value)).findFirst().orElse(null);
    }
}
