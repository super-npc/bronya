package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author chenwenxi
 */
@Getter
@AllArgsConstructor
public enum PositionType implements AmisEnum {
    openLong(1, "开多",Color.深绿色), openShort(2, "开空",Color.猩红), closeLong(3, "平多",Color.查特酒绿), closeShort(4, "平空",Color.深洋红色);

    private final Integer key;
    private final String desc;
    private final Color color;

    public static PositionType convert(String name) {
        return Arrays.asList(PositionType.values()).stream().filter(s -> s.name().equals(name)).findFirst()
            .orElse(null);
    }

    public static PositionType convert(Integer key) {
        return Arrays.asList(PositionType.values()).stream().filter(s -> s.getKey().equals(key)).findFirst()
            .orElse(null);
    }
}
