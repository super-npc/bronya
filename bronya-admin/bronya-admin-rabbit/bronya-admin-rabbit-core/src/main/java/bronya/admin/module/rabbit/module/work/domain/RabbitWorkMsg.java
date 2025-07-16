package bronya.admin.module.rabbit.module.work.domain;

import java.util.Date;

import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.hutool.http.meta.Method;
import org.dromara.mpe.annotation.InsertFillTime;
import org.dromara.mpe.annotation.InsertUpdateFillTime;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.rabbit.type.MqStatus;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "工作模式消息")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "rabbit", menu = "工作模式"),
    btns = @Btns(add = false, edit = false, delete = false),
    operation = @Operation(optBtns = {@Operation.OptBtn(name = "重发", level = Operation.BtnLevelType.warning,
        method = Method.GET, batch = true, url = "/admin/rabbit/worker/re-send")}))
@TableIndexes({@TableIndex(fields = {RabbitWorkMsg.Fields.entityClass}, type = IndexTypeEnum.NORMAL),
    @TableIndex(fields = {RabbitWorkMsg.Fields.tradeId}, type = IndexTypeEnum.NORMAL)})
public class RabbitWorkMsg {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField(search = true)
    @Column(notNull = true,comment = "实例")
    private String entityClass;

    @AmisField(search = true)
    @Column(notNull = true,comment = "链路")
    private String tradeId;

    @AmisField
    @Column(notNull = true,comment = "状态")
    private MqStatus status;

    @AmisField(toggled = false)
    @Column(comment = "broker失败原因")
    private String brokerCause;

    @AmisField(editor = @AmisEditor.Editor(language = EditorLanguage.json),
        table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
        detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器))
    @Column(notNull = true,comment = "消息", type = MysqlTypeConstant.TEXT)
    private String msg;

    @Column(notNull = true, comment = "关联工作模式")
    @BindMany2One(entity = RabbitWork.class, valueField = RabbitWork.Fields.id,
        labelField = RabbitWork.Fields.entityClass)
    @AmisField
    private Long rabbitWorkId;

    @AmisField(sort = 100)
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @AmisField(sort = 101)
    @InsertUpdateFillTime
    @Column(notNull = true, comment = "更新时间")
    private Date updateTime;
}
