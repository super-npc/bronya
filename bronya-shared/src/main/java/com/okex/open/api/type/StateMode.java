package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StateMode {
    // 未成交订单获取
    live("等待成交"), partially_filled("部分成交"),

    // 获取订单状态
    filled("完全成交"),

    canceled("撤单成功"),
    // ------ 止盈止损
    effective("已生效"), order_failed("委托失败");

    private final String cn;

    public static StateMode convert(String name) {
        return Arrays.asList(StateMode.values()).stream().filter(s -> s.name().equals(name)).findFirst().orElse(null);
    }
}
