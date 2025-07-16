package npc.bulinke.external.module.telegram.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SendMessageResp {
    private Integer messageId;
    private FromDTO from;
    private ChatDTO chat;
    private Integer date;
    private String text;

    @NoArgsConstructor
    @Data
    public static class FromDTO {
        private Long id;
        private Boolean isBot;
        private String firstName;
        private String username;
    }

    @NoArgsConstructor
    @Data
    public static class ChatDTO {
        private Long id;
        private String title;
        private String type;
    }
}
