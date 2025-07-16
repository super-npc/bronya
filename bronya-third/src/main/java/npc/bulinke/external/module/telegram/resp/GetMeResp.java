package npc.bulinke.external.module.telegram.resp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetMeResp {
    private Long id;
    private Boolean isBot;
    private String firstName;
    private String username;
    /**
     * 返回True如果该机器人可以被邀请加入群组
     */
    private Boolean canJoinGroups;
    /**
     * 返回True如果该机器人禁用了隐私模式
     * 接收所有訊息
     * 預設隱私模式（Privacy Mode）是開啟的，開啟時只會收到：
     * <p>
     * 由 / 開頭的指令
     * 對機器人 Reply 的訊息
     * 系統訊息（e.g., 新成員加入）
     * 自己是管理員的頻道
     * 通常會希望關閉，才收得到全部訊息
     * <p>
     * 私訊 @BotFather
     * 輸入 /setprivacy 指令
     * 選擇 Bot
     * 點擊 Disable
     */
    private Boolean canReadAllGroupMessages;
    /**
     * 返回True，支持内联查询
     */
    private Boolean supportsInlineQueries;
    private Boolean canConnectToBusiness;
    private Boolean hasMainWebApp;
}
