package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PosSide implements AmisEnum {
    long_(1, "long", "多", Color.灰色), short_(2, "short", "空", Color.深绿色);

    private final Integer key;
    private final String val;

    private final String desc;
    private final Color color;

    public static PosSide convert(String value) {
        return Arrays.stream(PosSide.values()).filter(a -> a.name().equals(value)).findFirst().orElse(null);
    }

    public static PosSide convertShortLong(String posSideZh) {
        return Arrays.stream(PosSide.values()).filter(a -> a.getVal().equals(posSideZh)).findFirst().orElse(null);
    }
    // public static PosSide convertByCn(String cn) {
    // return Arrays.stream(PosSide.values()).filter(a ->
    // a.getCn().equals(cn)).findFirst().orElse(null);
    // }
}
