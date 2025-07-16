package bronya.core.base.constant;

import bronya.shared.module.common.type.RejectedExecutionHandlerEnum;

public interface IThreadPoolEnum {
    String getPrefixName();

    String getDesc();


    /**
     * 线程池核心线程数量，核心线程不会被回收，即使没有任务执行，也会保持空闲状态。
     */
    Integer getCorePoolSize();

    /**
     * 池允许最大的线程数，当线程数量达到corePoolSize，且workQueue队列塞满任务了之后，继续创建线程。
     */
    Integer getMaximumPoolSize();

    /**
     * 拒绝策略
     */
    RejectedExecutionHandlerEnum getRejectedStrategy();
}
