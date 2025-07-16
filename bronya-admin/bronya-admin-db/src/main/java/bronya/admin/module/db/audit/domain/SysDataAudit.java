package bronya.admin.module.db.audit.domain;

import java.util.Date;

import org.dromara.autotable.annotation.TableIndex;
import org.dromara.autotable.annotation.TableIndexes;
import org.dromara.autotable.annotation.enums.IndexTypeEnum;
import org.dromara.autotable.annotation.mysql.MysqlCharset;
import org.dromara.autotable.annotation.mysql.MysqlTypeConstant;
import org.dromara.mpe.autotable.annotation.Column;
import org.dromara.mpe.autotable.annotation.ColumnId;
import org.dromara.mpe.autotable.annotation.Table;

import com.baomidou.mybatisplus.annotation.IdType;

import bronya.admin.module.db.audit.proxy.SysDataAuditProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.inputdata.AmisEditorDiff;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.core.base.constant.AmisPage;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Table(comment = "数据审计")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@Amis(extBean = SysDataAudit.SysDataAuditExt.class,dataProxy = SysDataAuditProxy.class)
@AmisPage(menu = @Menu(module = "系统", group = "数据管理", menu = "审计管理"),
    btns = @Btns(add = false, edit = false, delete = false))
@TableIndexes({@TableIndex(fields = {SysDataAudit.Fields.tableBean}, type = IndexTypeEnum.NORMAL),
    @TableIndex(fields = {SysDataAudit.Fields.updateBy}, type = IndexTypeEnum.NORMAL)})
public class SysDataAudit {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField
    @Column(notNull = true, comment = "表")
    private String tableBean;

    @AmisField
    @Column(notNull = true, comment = "主键")
    private String recordPrimaryKey;

    @Column(notNull = true, comment = "修改前", type = MysqlTypeConstant.JSON)
    @AmisField(editor = @AmisEditor.Editor(language = EditorLanguage.json),
        table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        add = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器))
    private String oldData;

    @Column(notNull = true, comment = "修改后", type = MysqlTypeConstant.JSON)
    @AmisField(editor = @AmisEditor.Editor(language = EditorLanguage.json),
        table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        add = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
        edit = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器))
    private String newData;

    @Column(notNull = true, comment = "更新人")
    @AmisField
    protected Long updateBy;

    @Column(notNull = true, comment = "更新时间")
    @AmisField
    protected Date updateTime;

    @Data
    public static class SysDataAuditExt {
        @AmisField(comment = "差异",
            editorDiff = @AmisEditorDiff.EditorDiff(language = EditorLanguage.json,
                sourceValueName = "SysDataAudit__oldData", diffValue = "$SysDataAudit__newData"),
            table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器Diff), width = 1500)
        private String diff;
    }
}
