package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author chenwenxi
 */
@Getter
@AllArgsConstructor
public enum State {
    canceled(-1, "撤单成功"), faile(-2, "失败"), ok(2, "完全成交"),

    waiting(0, "等待成交"), tradePart(1, "部分成交"), ordering(3, "下单中"), canceling(4, "撤单中")
    // , processing(6, "未完成（等待成交+部分成交）"), //这两个实际中用不到
    // okAndCanceled(7, "已完成（撤单成功+完全成交）")
    ;

    private final Integer key;
    private final String cn;

    public static State getByName(String name) {
        return Arrays.asList(State.values()).stream().filter(s -> s.name().equals(name)).findFirst().orElse(null);
    }
}
