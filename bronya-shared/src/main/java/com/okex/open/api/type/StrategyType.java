package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StrategyType implements AmisEnum {
    NONE("无信号",Color.晒黑),GRID_TREND("网格", Color.深天蓝), TREND_LONG("看多", Color.纯绿), TREND_SHORT("看空", Color.纯红);

    private final String desc;
    private final Color color;
}
