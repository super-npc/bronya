package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrdType {
    market(1, "市价单"), limit(2, "限价单"), post_only(3, "只做maker单"), fok(4, "全部成交或立即取消"), ioc(5, "立即成交并取消剩余"),
    optimal_limit_ioc(6, "市价委托立即成交并取消剩余（仅适用交割、永续）"),

    // ----- 止盈止损
    conditional(7, "单向止盈止损"), oco(8, "双向止盈止损"), trigger(9, "计划委托"), iceberg(10, "冰山委托"), twap(11, "时间加权委托");

    private final Integer key;
    private final String cn;
}
