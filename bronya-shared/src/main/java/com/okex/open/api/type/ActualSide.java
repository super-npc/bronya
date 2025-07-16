package com.okex.open.api.type;
import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActualSide implements AmisEnum {
    tp(1, "止盈", Color.纯绿), sl(2, "止损", Color.深红色);

    private final Integer key;

    private final String desc;
    private final Color color;
}
