package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 状态机事件 {
    转成交(1, "成交"),

    开仓完成埋止盈止损(2, "开仓完成埋止盈止损"),

    止盈止损转结单(3, "止盈止损转结单"),

    // 只减仓结单(5,"止盈转结单"),

    取消订单(7, "取消订单"), 委托失败(8, "委托失败");

    private final Integer key;
    private final String cn;
}
