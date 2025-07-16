package bronya.admin.module.db.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import org.dromara.hutool.core.thread.NamedThreadFactory;

@Getter
public class MonitoredThreadPoolExecutor extends ThreadPoolExecutor {
    private String threadPrefixName;
    private final String remark;

    public MonitoredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, String remark) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.remark = remark;
    }

    public MonitoredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, String threadPrefixName, String remark) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
            new NamedThreadFactory(threadPrefixName, false));
        this.threadPrefixName = threadPrefixName;
        this.remark = remark;
    }

    public MonitoredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler, String remark) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        this.remark = remark;
    }

    /**
     *
     * 避坑指南：
     * <p>
     * IO密集型：线程数 ≈ CPU核数 * 2~3
     * <p>
     * CPU密集型：线程数 ≈ CPU核数 + 1
     * <p>
     * 队列选型：需要限流用ArrayBlockingQueue，不怕OOM用LinkedBlockingQueue
     * 
     * @param corePoolSize 核心打工人数
     * @param maximumPoolSize 临时工上限
     * @param keepAliveTime 临时工摸鱼时间
     * @param unit
     * @param workQueue
     * @param threadPrefixName
     * @param handler
     * @param remark
     */
    public MonitoredThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
        BlockingQueue<Runnable> workQueue, String threadPrefixName, RejectedExecutionHandler handler, String remark) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
            new NamedThreadFactory(threadPrefixName, false), handler);
        this.threadPrefixName = threadPrefixName;
        this.remark = remark;
    }
}