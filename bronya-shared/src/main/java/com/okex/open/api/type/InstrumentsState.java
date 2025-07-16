package com.okex.open.api.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstrumentsState {
    live(1, "交易中"), suspend(2, "暂停中"), preopen(3, "预上线");

    private final Integer key;
    private final String cn;
}
