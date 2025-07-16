package bronya.admin.module.db.threadpool.domain;

import bronya.admin.module.db.threadpool.proxy.SysThreadPoolProxy;
import bronya.core.base.annotation.amis.inputdata.AmisEditor;
import bronya.core.base.annotation.amis.type.editor.EditorLanguage;
import bronya.shared.module.common.type.ActivationStatus;
import bronya.shared.module.common.type.ProcessStatus;
import bronya.core.base.annotation.amis.AmisField;
import bronya.core.base.annotation.amis.AmisFieldView;
import bronya.core.base.annotation.amis.page.Amis;
import bronya.core.base.constant.AmisPage;
import bronya.core.base.annotation.amis.page.Btns;
import bronya.core.base.annotation.amis.page.Menu;
import bronya.shared.module.common.type.RejectedExecutionHandlerEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
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

@Data
@Table(comment = "线程池")
@MysqlCharset(charset = "utf8mb4", collate = "utf8mb4_general_ci")
@Accessors(chain = true)
@FieldNameConstants
@Amis(extBean = SysThreadPool.SysThreadPoolExt.class, dataProxy = SysThreadPoolProxy.class)
@AmisPage(menu = @Menu(module = "系统", group = "配置", menu = "系统配置"), btns = @Btns(add = false, delete = false))
@TableIndexes({@TableIndex(fields = {SysThreadPool.Fields.prefixName}, type = IndexTypeEnum.UNIQUE)})
public class SysThreadPool {
    @ColumnId(mode = IdType.AUTO, comment = "id主键", type = MysqlTypeConstant.BIGINT, length = 32)
    private Long id;

    @AmisField(edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏), search = true)
    @Column(notNull = true, comment = "线程前缀")
    private String prefixName;

    @AmisField(remark = "线程池核心线程数量，核心线程不会被回收，即使没有任务执行，也会保持空闲状态。")
    @Column(notNull = true, comment = "核心线程")
    private Integer corePoolSize;

    @AmisField(remark = "池允许最大的线程数，当线程数量达到corePoolSize，且workQueue队列塞满任务了之后，继续创建线程。")
    @Column(notNull = true, comment = "最大线程")
    private Integer maximumPoolSize;

    @AmisField(edit = @AmisFieldView(type = AmisFieldView.ViewType.隐藏))
    @Column(notNull = true, comment = "拒绝策略")
    private RejectedExecutionHandlerEnum rejectedStrategy;

    /**
     * 一个任务的开始会开两个任务, 例如: 执行一次 = activeCount(两次) = taskCount(执行中+执行完成) = completedTaskCount(两次) =
     * 实际触发次数(completedTaskCount/2)
     */
    @Data
    public static class SysThreadPoolExt {
        @AmisField(comment = "状态", table = @AmisFieldView(type = AmisFieldView.ViewType.单选))
        private ActivationStatus status = ActivationStatus.DISABLE;

        @AmisField(comment = "流程", table = @AmisFieldView(type = AmisFieldView.ViewType.单选))
        private ProcessStatus process = ProcessStatus.CREATED;

        @AmisField(comment = "运行信息",
                editor = @AmisEditor.Editor(language = EditorLanguage.markdown, markdownRender = true),
                table = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器),
                detail = @AmisFieldView(type = AmisFieldView.ViewType.代码编辑器), width = 300)
        private String running;

        // 实际触发的次数是
//        @AmisField(comment = "实际次数")
//        private Long execCount;
//
//        @AmisField(comment = "名称")
//        private String cnName;

        // @AmisField(comment = "poolSize")
        // private Integer poolSize;

//        @AmisField(comment = "队列容量(待执行)")
//        private Integer queueSize;
//
//        @AmisField(comment = "活跃量(执行中)")
//        private Integer activeCount;
//
//        @AmisField(comment = "已执行+未执行(池)")
//        private Long taskCount;
//
//        @AmisField(comment = "已完成(池)")
//        private Long completedTaskCount;
//
//        // 超过corePoolSize之后的“临时线程”的存活时间。
//        @AmisField(comment = "存活秒")
//        private Long keepAliveTime;
//
//        @AmisField(comment = "错误信息")
//        private String errorMsg;

        // @AmisField(comment = "存活单位", table = @AmisFieldView(type = AmisFieldView.ViewType.隐藏),
        // detail = @AmisFieldView(type = AmisFieldView.ViewType.文本))
        // private String unit;

        // @AmisField(comment = "阻塞队列", detail = @AmisFieldView(type = AmisFieldView.ViewType.文本))
        // private String blockingQueueName;
    }
}
