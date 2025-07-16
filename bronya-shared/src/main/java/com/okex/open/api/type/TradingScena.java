package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/** 交易场景,现货,期货,期权,合约 */
@Getter
@AllArgsConstructor
public enum TradingScena {
    spot(1, "现货"), margin(2, "杠杆"), futures(3, "交割合约"), swap(4, "永续合约"), option(5, "期权合约");

    private final Integer key;

    // @JsonValue
    private final String cn;

    public static TradingScena getByName(String name) {
        return Arrays.asList(TradingScena.values()).stream().filter(s -> s.name().equals(name)).findFirst()
            .orElse(null);
    }
}
