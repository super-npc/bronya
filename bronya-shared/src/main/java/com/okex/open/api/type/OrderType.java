package com.okex.open.api.type;

import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author chenwenxi
 */
@Getter
@AllArgsConstructor
public enum OrderType {
    maker("普通委托", Color.洋红), onlyMarker("只做Maker", Color.栗色), fok("全部成交或立即取消", Color.深兰花紫), ioc("立即成交并取消剩余", Color.桃色),
    marketPrice("市价委托", Color.淡蓝色);

    // @JsonValue
    private final String cn;
    private final Color colour;

    public static OrderType getByName(String name) {
        return Arrays.asList(OrderType.values()).stream().filter(s -> s.name().equals(name)).findFirst().orElse(null);
    }
}
