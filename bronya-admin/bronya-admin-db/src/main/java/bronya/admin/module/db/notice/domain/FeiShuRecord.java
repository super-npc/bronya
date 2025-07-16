package bronya.admin.module.db.notice.domain;

import java.util.Date;

import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;
import org.dromara.mpe.base.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.gencode.table.BindMany2One;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.LevelType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "飞书消息")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Amis
@AmisPage(menu = @Menu(module = "系统", group = "消息中心", menu = "飞书"),
    autoGenerateFilter = @AmisPage.AutoGenerateFilter(defaultCollapsed = false, columnsNum = 5),
    btns = @Btns(add = false, edit = false, delete = false))
public class FeiShuRecord extends BaseEntity<Long, Date> {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    @AmisField(toggled = false)
    private Long id;

    @Column(notNull = true, comment = "消息级别", defaultValue = "DISABLE")
    @AmisField(sortable = true, search = true)
    private LevelType levelType;

    @Column(notNull = true, comment = "关联飞书")
    @BindMany2One(entity = FeiShuBot.class, valueField = FeiShuBot.Fields.id, labelField = FeiShuBot.Fields.name)
    @AmisField
    private Long feiShuId;

    @AmisField(width = 500, tableStatic = false, editor = @AmisEditor.Editor(language = EditorLanguage.json),
        table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器))
    @Column(notNull = true, comment = "消息", type = MysqlTypeConstant.TEXT)
    private String msg;

    @AmisField
    @Column(comment = "发送结果", type = MysqlTypeConstant.TEXT)
    private String res;
}
