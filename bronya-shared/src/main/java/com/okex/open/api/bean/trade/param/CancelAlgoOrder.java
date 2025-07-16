package com.okex.open.api.bean.trade.param;

import java.io.Serializable;

public class CancelAlgoOrder implements Serializable {

    private String algoId;
    private String instId;

    public String getAlgoId() {
        return algoId;
    }

    public void setAlgoId(String algoId) {
        this.algoId = algoId;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }
}
