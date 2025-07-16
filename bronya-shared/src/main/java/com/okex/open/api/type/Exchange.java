package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author chenwenxi
 */
@Getter
@AllArgsConstructor
public enum Exchange {
    zb(1, "中币"), ouyi(2, "ok"), futu(3, "富途牛牛");

    private final int value;

    // @JsonValue
    private final String cn;

    /** 通过value获取对应的枚举对象 */
    public static Exchange getEnum(int value) {
        return Arrays.stream(Exchange.values()).filter(exchange -> exchange.getValue() == value).findFirst()
            .orElse(null);
    }

    public static Exchange getEnum(String name) {
        return Arrays.stream(Exchange.values()).filter(exchange -> exchange.name().equals(name)).findFirst()
            .orElse(null);
    }
}
