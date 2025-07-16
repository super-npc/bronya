package bronya.admin.module.db.threadpool;

import bronya.admin.module.db.threadpool.domain.SysThreadPool;
import bronya.core.base.constant.IThreadPoolEnum;
import bronya.shared.module.common.type.RejectedExecutionHandlerEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @formatter:off
 * ThreadPoolExecutor 是 Java 中用于创建线程池的类，它支持多种队列模型，这些队列模型主要用于存储等待执行的任务。以下是几种常见的队列模型：
 * 1. 无界队列（LinkedBlockingQueue）
 * 特点：LinkedBlockingQueue 是一个基于链表的阻塞队列，默认容量为 Integer.MAX_VALUE，可以近似认为是无界的。
 * 适用场景：当任务量较大且线程池的线程数较少时，使用无界队列可以避免任务丢失，但可能会导致线程池的线程长时间处于忙碌状态，甚至可能耗尽系统资源。
 * 缺点：由于队列无界，可能会导致任务积压过多，占用大量内存。
 * 2. 有界队列（ArrayBlockingQueue）
 * 特点：ArrayBlockingQueue 是一个基于数组的阻塞队列，需要在创建时指定容量。它是有界的，可以限制队列的最大长度。
 * 适用场景：适用于任务量可控的场景，通过限制队列大小，可以防止任务积压过多，避免占用过多系统资源。
 * 缺点：如果队列容量设置不合理，可能会导致任务被拒绝执行。
 * 3. 同步队列（SynchronousQueue）
 * 特点：SynchronousQueue 是一个特殊的队列，它没有容量，每个任务必须立即被线程池中的线程处理，否则会直接被拒绝。
 * 适用场景：适用于任务处理速度较快，且希望任务能够立即被处理的场景。通常与可拒绝策略（如 CallerRunsPolicy）配合使用。
 * 缺点：对线程池的线程数量要求较高，如果线程池线程不足，任务很容易被拒绝。
 * 4. 优先队列（PriorityBlockingQueue）
 * 特点：PriorityBlockingQueue 是一个基于优先级的无界阻塞队列。任务会根据优先级顺序出队，优先级高的任务会先被处理。
 * 适用场景：适用于任务有优先级区分的场景，例如高优先级的任务需要优先处理。
 * 缺点：虽然队列本身无界，但由于线程池的线程数量有限，高优先级任务可能会积压低优先级任务。
 * 5. 自定义队列
 * 特点：用户可以根据需求实现自己的阻塞队列，例如通过继承 AbstractQueue 或实现 BlockingQueue 接口。
 * 适用场景：当默认的队列模型无法满足需求时，可以通过自定义队列来实现特定的队列行为。
 * @formatter:on
 */
@Slf4j
@UtilityClass
public class ThreadPoolUtil {

    /**
     * 避坑指南：
     *
     * IO密集型：线程数 ≈ CPU核数 * 2~3
     * 
     * CPU密集型：线程数 ≈ CPU核数 + 1
     * 
     * 队列选型：需要限流用ArrayBlockingQueue，不怕OOM用LinkedBlockingQueue
     */
    public MonitoredThreadPoolExecutor newThreadPool(IThreadPoolEnum poolEnum, SysThreadPool conf) {
        RejectedExecutionHandler rejectedExecutionHandler = switchRejectedStrategy(conf.getRejectedStrategy());
        return new MonitoredThreadPoolExecutor(conf.getCorePoolSize(), conf.getMaximumPoolSize(), 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(), poolEnum.getPrefixName(), rejectedExecutionHandler, poolEnum.getDesc());
    }

    public RejectedExecutionHandler switchRejectedStrategy(RejectedExecutionHandlerEnum rejectedExecutionHandlerEnum) {
        return switch (rejectedExecutionHandlerEnum) {
            case AbortPolicy -> new ThreadPoolExecutor.AbortPolicy();
            case DiscardPolicy -> new ThreadPoolExecutor.DiscardPolicy();
            case DiscardOldestPolicy -> new ThreadPoolExecutor.DiscardOldestPolicy();
            case CallerRunsPolicy -> new ThreadPoolExecutor.CallerRunsPolicy();
            // 默认使用调用者策略
            default -> new ThreadPoolExecutor.CallerRunsPolicy();
        };
    }

    // public MonitoredThreadPoolExecutor getPool(IThreadPoolEnum poolEnum) {
    // return getPool(poolEnum.getThreadPrefixName());
    // }

    // /**
    // * 线程池前端获取对应线程池bean
    // */
    // public MonitoredThreadPoolExecutor getPool(String threadPrefixName) {
    // String beanName = BulinkeModelConstant.GLOBAL_THREAD_POOLS.get(threadPrefixName);
    // // Assert.notBlank(beanName, "全局线程池管理未找到线程bean名称:{}", threadPrefixName);
    // if (beanName == null) {
    // return null;
    // }
    // try {
    // return SpringUtil.getBean(beanName, MonitoredThreadPoolExecutor.class);
    // } catch (Exception e) {
    // throw new BusinessException(BusinessError.NO_THREAD_POOL_FOUND);
    // }
    // }
    //
    // /**
    // * 所有线程池的前缀
    // */
    // public List<String> getAllPoolThreadPrefixName() {
    // return BulinkeModelConstant.GLOBAL_THREAD_POOLS.keySet().stream().toList();
    // }

    // /**
    // * 全局公共线程池
    // */
    // public MonitoredThreadPoolExecutor getGlobalPool() {
    // return getPool(GlobalPoolBulinke.GLOBAL_POOL_STARTER);
    // }
}
