package bronya.admin.module.db.telegram.domain;

import bronya.admin.module.db.proxy.domain.ProxyDo;
import bronya.admin.module.db.telegram.proxy.TelegramBotProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.gencode.table.BindMiddleChild;
import bronya.core.base.annotation.amis.gencode.table.MiddleJoinCondition;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.dromara.autotable.annotation.IndexField;
import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexSortTypeEnum;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.annotation.Exclude;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import java.util.Date;

@Data
@Table(comment = "小飞机Bot")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis(extBean = TelegramBot.TelegramBotExt.class, dataProxy = TelegramBotProxy.class)
@AmisPage(menu = @Menu(module = "系统", group = "消息中心", menu = "小飞机"),
        autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5),
        operation = @Operation(optBtns = {@Operation.OptBtn(name = "验证发送", level = Operation.BtnLevelType.info, method = Method.GET, batch = true, url = "/admin/telegram-bot/send-test"),
                @Operation.OptBtn(name = "更新GetMe", level = Operation.BtnLevelType.info, method = Method.GET, batch = true, url = "/admin/telegram-bot/update-by-get-me"),
                @Operation.OptBtn(name = "getUpdates", level = Operation.BtnLevelType.info, method = Method.GET, batch = true, url = "/admin/telegram-bot/send-test-update")}))
@TableIndexes({@TableIndex(indexFields = {@IndexField(field = TelegramBot.Fields.token, sort = IndexSortTypeEnum.DESC)}, type = IndexTypeEnum.UNIQUE)})
public class TelegramBot extends BaseEntity<Long, Date> {

    @ColumnId(mode = IdType.INPUT, comment = "botId", type = MysqlTypeConstant.BIGINT, length = 52)
    @AmisField(toggled = false, sortable = true, search = true, remark = "该聊天的唯一标识符。这个数字可能会大于32位但是一定小于52位所以编程时因指定int64类型")
    private Long id;

    @Column(notNull = true, comment = "启用", defaultValue = "true")
    @AmisField
    private Boolean enable;

    @Column(comment = "firstName")
    @AmisField(
            add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
            edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏)
    )
    private String firstName;

    @Column(comment = "username")
    @AmisField(
            add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
            edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏)
    )
    private String username;


    @Column(notNull = true, comment = "token")
    @AmisField
    private String token;

    @Column(notNull = true, comment = "代理", defaultValue = "true")
    @AmisField
    private Boolean proxyEnable;

    @Column(notNull = true, comment = "关联代理")
    @BindMany2One(entity = ProxyDo.class, valueField = ProxyDo.Fields.id, labelField = ProxyDo.Fields.name)
    @AmisField
    private Long proxyId;

    @Exclude
    @BindMiddleChild(entity = TelegramBotChatRef.class,
            conditionRef = @MiddleJoinCondition(selfField = TelegramBot.Fields.id, joinField = TelegramBotChatRef.Fields.botId))
    private TelegramBotChatRef telegramBotChatRef;

    @Data
    public static class TelegramBotExt {

        @AmisField(comment = "getMe", editor = @AmisEditor.Editor(language = EditorLanguage.markdown, markdownRender = true), table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), width = 300)
        private String botGetMeInfo;
    }
}
