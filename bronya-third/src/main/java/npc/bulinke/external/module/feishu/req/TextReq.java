package npc.bulinke.external.module.feishu.req;

import lombok.Data;
import lombok.NoArgsConstructor;
import npc.bulinke.external.module.feishu.type.MsgType;
import org.dromara.hutool.core.text.StrUtil;

@NoArgsConstructor
@Data
public class TextReq {
    private final MsgType msgType = MsgType.text;
    private Content content;

    public TextReq(CharSequence template, Object... params) {
        this.content = new Content();
        this.content.text = StrUtil.format(template, params);
    }

    public TextReq(String at, CharSequence template, Object... params) {
        this.content = new Content();
        String format = StrUtil.format(template, params);
        this.content.text = STR."""
            \{format}
            <at user_id="\{at}">\{at}</at>
            """;
    }

    public TextReq(Boolean atAll, CharSequence template, Object... params) {
        this.content = new Content();
        String format = StrUtil.format(template, params);
        String all = atAll ? "<at user_id=\"all\">所有人</at>" : "";
        this.content.text = STR."""
                \{format}
                \{all}
                """;
    }

    @NoArgsConstructor
    @Data
    public static class Content {
        private String text;
    }
}
