package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OcSide {
    open(1, "开仓"), close(2, "平仓");

    private final Integer key;
    private final String cn;

    public static OcSide convert(String value) {
        return Arrays.stream(OcSide.values()).filter(a -> a.name().equals(value)).findFirst().orElse(null);
    }
    // public static OcSide convertByCn(String cn) {
    // return Arrays.stream(OcSide.values()).filter(a ->
    // a.getCn().equals(cn)).findFirst().orElse(null);
    // }
}
