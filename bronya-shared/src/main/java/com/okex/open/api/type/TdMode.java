package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TdMode implements AmisEnum {
    isolated("逐仓", Color.绿宝石), cross("全仓", Color.橄榄), cash("非保证金", Color.卡其布);

    private final String desc;
    private final Color color;
}
