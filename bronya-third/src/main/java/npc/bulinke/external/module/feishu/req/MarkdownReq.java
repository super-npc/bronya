package npc.bulinke.external.module.feishu.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import npc.bulinke.external.module.feishu.type.MsgType;

import java.util.List;

@Data
public class MarkdownReq {
    private final MsgType msgType = MsgType.interactive;
    private CardDTO card;

    public MarkdownReq(String title, List<CardDTO.Elements> elements) {
        CardDTO.Header header = new CardDTO.Header(new CardDTO.Header.TitleDTO(title));
        MarkdownReq.CardDTO cardDTO = new MarkdownReq.CardDTO();
        cardDTO.setHeader(header);
        cardDTO.setElements(elements);
        this.card = cardDTO;
    }

    @NoArgsConstructor
    @Data
    public static class CardDTO {
        private Header header;
        private List<Elements> elements;

        @Data
        @RequiredArgsConstructor
        public static class Header {
            private final TitleDTO title;

            @Data
            @RequiredArgsConstructor
            public static class TitleDTO {
                private final String content;
                private final String tag = "plain_text";
            }
        }

        @Data
        public static class Elements {
            private final String tag = "markdown";
            private final String textAlign = "text_align";
            private final String content;

            public Elements(String content) {
                this.content = content;
            }

            public Elements(String at, String content) {
                this.content = STR."""
                        \{content}
                        <at user_id="\{at}">\{at}</at>
                        """;
            }
        }
    }
}
