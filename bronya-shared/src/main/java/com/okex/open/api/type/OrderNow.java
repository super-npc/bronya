package com.okex.open.api.type;

import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderNow {
    // ---- 限价买入订单
    _1opened("1开仓", Color.草坪绿), _2zhiYingSuning("2止盈止损中", Color.深绿色),

    _3sunEnd("3止损结单", Color.洋红), _3yingEnd("3盈利结单", Color.橙色),

    _4cancel("取消订单", Color.春天的绿色), _5filled("订单成交", Color.春天的绿色),

    _6error("异常", Color.海军蓝),

    _7reduceOnly("单纯减仓", Color.印度红), _8reduceOnlyEnd("单纯减仓结单", Color.卡其布);

    private final String cn;
    private final Color colour;

    public static OrderNow convert(String name) {
        return Arrays.asList(OrderNow.values()).stream().filter(s -> s.name().equals(name)).findFirst().orElse(null);
    }
}
