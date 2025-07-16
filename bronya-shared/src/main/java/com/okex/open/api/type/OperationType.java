package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType implements AmisEnum {
    STOP_PROFIT("止盈", Color.纯绿), STOP_LOSS("止损", Color.纯红);

    // @JsonValue
    private final String desc;
    private final Color color;
}