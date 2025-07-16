package com.okex.open.api.type;

import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenwenxi
 */
@Getter
@AllArgsConstructor
public enum KTime implements AmisEnum {
    min1(1, "1min", Color.橙色), min3(3, "3min", Color.秋麒麟), min5(5, "5min", Color.午夜的蓝色), min15(15, "15min", Color.沙棕色),
    min30(30, "30min", Color.适中的绿宝石), hour1(60, "1hour", Color.纯蓝)
    // min1(60,1,"1min","1分钟"),
    // min3(180,3,"3min","3分钟"),
    // min5(300,5,"5min","5分钟"),
    // min15(900,15,"15min","15分钟"),
    // min30(1800,30,"30min","30分钟"),
    // hour1(3600,60,"1hour","1小时")
    // hour1(3600,"1hour","1小时"),
    // hour2(7200,"2hour","2小时"),
    // hour4(14400,"4hour","4小时"),
    // hour6(21600,"6hour","6小时"),
    // hour12(43200,"12hour","12小时"),
    //
    // day1(86400,"1day","1天"),
    // week1(604800,"1week","1周");
    ;

    /**
     * 60 -> 1min 180 -> 3min 300 -> 5min 900 -> 15min 1800 -> 30min 3600 -> 1hour 7200 -> 2hour 14400 -> 4hour 21600 ->
     * 6hour 43200 -> 12hour 86400 -> 1day 604800 -> 1week
     */
    private final int minute;
    private final String desc;
    private final Color color;

    public long getSecond() {
        return this.getMinute() * 60;
    }
}
