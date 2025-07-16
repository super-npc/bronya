package bronya.admin.module.db.threadpool;

import bronya.core.base.constant.IThreadPoolEnum;
import bronya.shared.module.common.type.RejectedExecutionHandlerEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalThreadPool implements IThreadPoolEnum {
    GLOBAL_POOL("global-pool-", "全局公共线程池", 1, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),

    RESTFUL_LOG("global-pool-web-log-", "记录restful请求", 1, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),

    HTTP_API_CLIENT("global-pool-http-", "http客户端访问记录池", 1, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),

    BIZ_LOG("global-pool-biz-log-", "业务日志持久化", 1, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),

    AUDIT_ADD_RECORD("global-pool-audit-add-record-", "修改记录审计", 1, 100,
        RejectedExecutionHandlerEnum.CallerRunsPolicy),

    FEI_SHU_SENDER("global-pool-fei-shu-sender-", "发送飞书", 1, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),

    BADGE_READ("global-pool-badge-read-", "已读徽章", 5, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),
    BADGE_UNREAD("global-pool-badge-unread-", "未读徽章", 5, 100, RejectedExecutionHandlerEnum.CallerRunsPolicy),

    ;

    private final String prefixName;
    private final String desc;
    // 线程池核心线程数量，核心线程不会被回收，即使没有任务执行，也会保持空闲状态。
    private final Integer corePoolSize;
    // 池允许最大的线程数，当线程数量达到corePoolSize，且workQueue队列塞满任务了之后，继续创建线程。
    private final Integer maximumPoolSize;
    // 拒绝策略
    private final RejectedExecutionHandlerEnum rejectedStrategy;
}