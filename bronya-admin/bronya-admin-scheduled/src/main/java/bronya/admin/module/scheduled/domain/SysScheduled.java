package bronya.admin.module.scheduled.domain;

import bronya.admin.module.scheduled.proxy.SysScheduledProxy;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.core.base.annotation.amis.page.Operation;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.core.base.constant.AmisPage;
import bronya.shared.module.common.type.ProcessStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
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

import java.util.Date;

@Data
@Table(comment = "调度配置")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@Amis(extBean = SysScheduled.SysScheduledExt.class, dataProxy = SysScheduledProxy.class)
@FieldNameConstants
@AmisPage(menu = @Menu(module = "系统", group = "调度系统", menu = "调度管理"), btns = @Btns(add = false, delete = false),
        operation = @Operation(optBtns = {
                @Operation.OptBtn(name = "执行", level = Operation.BtnLevelType.info, method = Method.GET, batch = true,
                        url = "/admin/scheduled/trigger"),
                @Operation.OptBtn(name = "启动", level = Operation.BtnLevelType.success, method = Method.GET, batch = true,
                        url = "/admin/scheduled/start"),
                @Operation.OptBtn(name = "停止", level = Operation.BtnLevelType.warning, method = Method.GET, batch = true,
                        url = "/admin/scheduled/stop")}))
@TableIndexes({@TableIndex(fields = {SysScheduled.Fields.beanName}, type = IndexTypeEnum.UNIQUE)})
public class SysScheduled {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField
    @Column(comment = "启用")
    private Boolean enable;

    @Column(notNull = true, comment = "对象名")
    @AmisField(edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    private String beanName;

    @Column(notNull = true, comment = "cron")
    @AmisField
    private String cron;

    @AmisField
    @Column(notNull = true, comment = "备注")
    private String remark;

    @AmisField(table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(comment = "参数")
    private String params;

    @AmisField(sort = 100,
            add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
            edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @InsertFillTime
    @Column(notNull = true, comment = "创建时间")
    private Date createTime;

    @AmisField(sort = 101, add = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
            edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @InsertUpdateFillTime
    @Column(notNull = true, comment = "更新时间")
    private Date updateTime;

    @Data
    public static class SysScheduledExt {
        @AmisField(comment = "状态")
        private ProcessStatus process;
//
//        @AmisField(comment = "下次执行", sort = 5)
//        private Date nextTime;
//
//        @AmisField(comment = "异常信息", sort = 6)
//        private String errorMsg;
//
//        @AmisField(comment = "任务id", sort = 6)
//        private String hutoolScheduleId;

        @AmisField(comment = "运行信息",
                editor = @AmisEditor.Editor(language = EditorLanguage.markdown, markdownRender = true),
                table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
                detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), width = 300)
        private String running;
    }
}
