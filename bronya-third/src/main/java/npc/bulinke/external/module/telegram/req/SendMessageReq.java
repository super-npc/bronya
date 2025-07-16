package npc.bulinke.external.module.telegram.req;

import lombok.*;

@Data
@RequiredArgsConstructor
public class SendMessageReq {
    /**
     * 目标聊天的唯一标识符或频道用户名
     */
    private final String chatId;

    /**
     * 待发送消息的文本，实体解析后为1-4096个字符
     */
    private final String text;

    /**
     * 消息文本中的实体解析模式
     */
    private String parseMode;

    /**
     * 禁用此消息中链接的链接预览
     */
    private Boolean disableWebPagePreview;

    /**
     * 静默发送消息。用户将收到没有声音的通知
     */
    private Boolean disableNotification;

    /**
     * 如果消息是答复，则为原始消息的ID
     */
    private Integer replyToMessageId;

    /**
     * 其他界面选项。内联键盘，自定义回复键盘等
     */
    private Object replyMarkup;
}
