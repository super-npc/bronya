package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@FieldNameConstants
public enum BarType implements AmisEnum {
    m1(1, "1m", Color.纯蓝), m3(3, "3m", Color.深鲜肉), m5(5, "5m", Color.晒黑), m15(15, "15m", Color.橄榄土褐色),
    m30(30, "30m", Color.深海洋绿), h1(60, "1H", Color.深兰花紫), h2(120, "2H", Color.深绿宝石), h4(240, "4H", Color.绿玉碧绿色),
    h6(360, "6H", Color.绿玉碧绿色), h12(720, "12H", Color.绿玉碧绿色), d1(1440, "1D", Color.绿玉碧绿色),
    w1(10080, "1W", Color.绿玉碧绿色),;

    private final Integer minute;
    private final String desc;
    private final Color color;

    /** 通过value获取对应的枚举对象 */
    public static BarType getEnumByName(String value) {
        return Arrays.stream(BarType.values()).filter(a -> a.name().equals(value)).findFirst()
            .orElseThrow(() -> new RuntimeException("无法找到枚举"));
    }
}
