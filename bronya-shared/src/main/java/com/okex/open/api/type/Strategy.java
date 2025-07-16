package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Strategy implements AmisEnum {
    trend("趋势", Color.淡珊瑚色), grid("高抛低吸", Color.灰秋麒麟);

    private final String desc;
    private final Color color;
}
