package npc.bulinke.external.module.feishu;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FeishuResp {
    private int statusCode;
    private String statusMessage;
    private int code;
    private Data data;
    private String msg;

    @NoArgsConstructor
    @lombok.Data
    public static class Data {}
}
