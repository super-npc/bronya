package npc.bulinke.external.module.telegram.resp;

import lombok.Data;

@Data
public class TelegramRespBase<T> {
    private Boolean ok;
    private T result;
    private Integer errorCode;
    private String description;
}
