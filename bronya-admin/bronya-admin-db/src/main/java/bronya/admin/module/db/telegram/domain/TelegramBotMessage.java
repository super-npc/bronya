package bronya.admin.module.db.telegram.domain;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.shared.module.common.type.AmisEnum;
import bronya.shared.module.common.type.Color;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;
import org.dromara.autotable.annotation.ColumnComment;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.annotation.InsertFillTime;
import org.dromara.mpe.annotation.InsertUpdateFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import java.util.Date;

@Data
@Table(comment = "小飞机Bot消息")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "消息中心", menu = "小飞机"), autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5), btns = @Btns(add = false, edit = false, delete = false))
public class TelegramBotMessage {

    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "状态", defaultValue = "CREATE")
    @AmisField(sortable = true, search = true)
    private BotMessageStatus status;

    @Column(notNull = true, comment = "来源")
    @AmisField(search = true)
    private String source;

    @Column(notNull = true, comment = "botId")
    @BindMany2One(entity = TelegramBot.class, valueField = TelegramBot.Fields.id, labelField = TelegramBot.Fields.username)
    @AmisField
    private Long botId;

    @Column(notNull = true, comment = "chatId")
    @BindMany2One(entity = TelegramBotChat.class, valueField = TelegramBotChat.Fields.id, labelField = TelegramBotChat.Fields.title)
    @AmisField
    private Long chatId;

    @Column(notNull = true, comment = "待发送消息的文本", type = MysqlTypeConstant.TEXT)
    @AmisField(remark = "实体解析后为1-4096个字符")
    private String text;

    @Column(notNull = true, comment = "静默发送消息")
    @AmisField(search = true, remark = "用户将收到没有声音的通知")
    private Boolean disableNotification;

    @Column(comment = "消息id")
    @AmisField(search = true, remark = "发送结果")
    private Long messageId;

    @Column(comment = "消息时间")
    @AmisField(search = true, remark = "发送结果")
    private Date messageDate;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @AmisField(sort = 101)
    @InsertUpdateFillTime
    @Column(notNull = true, comment = "更新时间")
    private Date updateTime;

    @Getter
    @AllArgsConstructor
    public enum BotMessageStatus implements AmisEnum {
        CREATE("待发送", Color.卡其布), PROCESSING("发送中", Color.天蓝色), FAIL("失败", Color.深红色), SUCCESS("成功", Color.酸橙绿);

        private final String desc;
        private final Color color;
    }
}
