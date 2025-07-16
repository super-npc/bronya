package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum InstType implements AmisEnum {
    SPOT("币币", Color.灯笼海棠), MARGIN("币币杠杆", Color.秘鲁), SWAP("永续合约", Color.道奇蓝), FUTURES("交割合约", Color.适中的紫罗兰红色);

    private final String desc;
    private final Color color;

    public static InstType find(String value) {
        return Arrays.stream(InstType.values()).filter(a -> a.name().equals(value)).findFirst().orElse(null);
    }
}
