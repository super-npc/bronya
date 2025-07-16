package bronya.admin.module.db.telegram.domain;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindMiddleChild;
import bronya.core.base.annotation.amis.gencode.table.MiddleJoinCondition;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.annotation.Exclude;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

@Data
@Table(comment = "小飞机chat")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@FieldNameConstants
@Amis(extBean = TelegramBotChat.TelegramBotChatExt.class)
@AmisPage(menu = @Menu(module = "系统", group = "消息中心", menu = "小飞机"),
        btns = @Btns(add = false, edit = false, delete = false),
        autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5))
public class TelegramBotChat {
    @ColumnId(mode = IdType.INPUT, comment = "chat标识", type = MysqlTypeConstant.BIGINT, length = 52)
    @AmisField(toggled = false, sortable = true, search = true, remark = "该聊天的唯一标识符。这个数字可能会大于32位但是一定小于52位所以编程时因指定int64类型")
    private Long id;

    @Column(comment = "群名称")
    @AmisField(search = true, remark = "针对 supergroups, channels 和 group 类型的聊天")
    private String title;

    @Column(notNull = true, comment = "聊天的类型")
    @AmisField(search = true, remark = "private,group,supergroup,channel")
    private String type;

    @Column(comment = "first_name")
    @AmisField(search = true, remark = "私人聊天")
    private String firstName;

    @Column(comment = "last_name")
    @AmisField(search = true, remark = "私人聊天")
    private String lastName;

    @Column(comment = "username")
    @AmisField(search = true, remark = "私有的聊天，如果可以的话也针对 supergroups 和 channels")
    private String username;

    @Exclude
    @BindMiddleChild(entity = TelegramBotChatRef.class,
            conditionRef = @MiddleJoinCondition(selfField = TelegramBotChat.Fields.id, joinField = TelegramBotChatRef.Fields.chatId))
    private TelegramBotChatRef telegramBotChatRef;

    @Data
    public static class TelegramBotChatExt {

    }
}
