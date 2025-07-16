package bronya.admin.module.db.telegram.domain;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.gencode.table.BindMiddleParent;
import bronya.core.base.annotation.amis.gencode.table.JoinCondition;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.gencode.MiddleTableEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import java.util.Date;

@Data
@Table(comment = "小飞机BotChat关联")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis(extBean = TelegramBotChatRef.TelegramBotChatRefExt.class)
@TableIndexes({@TableIndex(name = "uni_botId_chatId",
        fields = {TelegramBotChatRef.Fields.botId, TelegramBotChatRef.Fields.chatId}, type = IndexTypeEnum.UNIQUE)})
public class TelegramBotChatRef extends MiddleTableEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField
    @Column(notNull = true, comment = "bot")
    @BindMiddleParent(entity = TelegramBot.class,
            conditionRef = @JoinCondition(selfField = Fields.botId, joinField = TelegramBot.Fields.id, joinFieldLabel = TelegramBot.Fields.username))
    private Long botId;

    @AmisField
    @Column(notNull = true, comment = "chat")
    @BindMiddleParent(entity = TelegramBotChat.class,
            conditionRef = @JoinCondition(selfField = Fields.chatId, joinField = TelegramBotChat.Fields.id, joinFieldLabel = TelegramBotChat.Fields.title))
    private Long chatId;

    @Data
    public static class TelegramBotChatRefExt {

    }

}
