package bronya.admin.module.db.threadpool.service;

import java.util.Map;
import java.util.concurrent.Callable;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.*;

import bronya.admin.module.db.threadpool.MonitoredThreadPoolExecutor;
import bronya.admin.module.db.threadpool.ThreadPoolUtil;
import bronya.admin.module.db.threadpool.domain.SysThreadPool;
import bronya.admin.module.db.threadpool.repository.SysThreadPoolRepository;
import bronya.core.base.constant.IThreadPoolEnum;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.shared.module.util.TraceId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Order
@RequiredArgsConstructor
public class SysThreadPoolService implements ApplicationListener<ApplicationReadyEvent> {
    private static final Map<String, MonitoredThreadPoolExecutor> THREAD_POOL_EXECUTORS = Maps.newHashMap(); // Map<PrefixName,Executor>
    private final SysThreadPoolRepository threadPoolRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        for (Class<?> classesThreadPool : AdminBaseConstant.CLASSES_THREAD_POOLS) {
            for (Object e : classesThreadPool.getEnumConstants()) {
                if (e instanceof IThreadPoolEnum threadPool) {
                    this.registerThreadPool(threadPool);
                }
            }
        }
    }

    public MonitoredThreadPoolExecutor getMemoryPoolByPrefixName(String prefixName) {
        return THREAD_POOL_EXECUTORS.get(prefixName);
    }

    public <T> void callback(IThreadPoolEnum threadPool, Callable<T> task, FutureCallback<T> callback) {
        MonitoredThreadPoolExecutor threadPoolExecutor = THREAD_POOL_EXECUTORS.get(threadPool.getPrefixName());
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPoolExecutor);
        TraceId.setMdcTraceId(TraceId.getMdcTradeId());
        ListenableFuture<T> submit = service.submit(() -> {
            TraceId.setMdcTraceId(TraceId.getTraceId());
            return task.call();
        });
        FutureCallback<T> futureCallback = new FutureCallback<>() {
            @Override
            public void onSuccess(T result) {
                TraceId.setMdcTraceId(TraceId.getTraceId());
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(@NotNull Throwable t) {
                TraceId.setMdcTraceId(TraceId.getTraceId());
                callback.onFailure(t);
            }
        };
        Futures.addCallback(submit, futureCallback, service);
    }

    private void registerThreadPool(IThreadPoolEnum threadPool) {
        String prefixName = threadPool.getPrefixName();
        if (THREAD_POOL_EXECUTORS.containsKey(prefixName)) {
            // 线程池已注册
            return;
        }
        SysThreadPool sysThreadPool = threadPoolRepository.findByThreadPrefixName(prefixName);
        if (sysThreadPool == null) {
            sysThreadPool = new SysThreadPool();
            sysThreadPool.setPrefixName(prefixName);
            sysThreadPool.setCorePoolSize(threadPool.getCorePoolSize());
            sysThreadPool.setMaximumPoolSize(threadPool.getMaximumPoolSize());
            sysThreadPool.setRejectedStrategy(threadPool.getRejectedStrategy());
            threadPoolRepository.save(sysThreadPool);
            // 新的线程池初始化完成
        }
        MonitoredThreadPoolExecutor monitoredThreadPoolExecutor =
            ThreadPoolUtil.newThreadPool(threadPool, sysThreadPool);
        THREAD_POOL_EXECUTORS.put(prefixName, monitoredThreadPoolExecutor);
        // 注册线程池完成
    }

}
