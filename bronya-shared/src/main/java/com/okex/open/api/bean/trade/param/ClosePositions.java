package com.okex.open.api.bean.trade.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClosePositions implements Serializable {
    private String instId;
    private String posSide;
    private String mgnMode;
    private String ccy;
    private Boolean autoCxl;

}
