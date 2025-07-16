package bronya.admin.module.db.amis.service;

import java.util.concurrent.Callable;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTaskService {

    private final ThreadPoolTaskExecutor adminFrontTaskExecutor;

    public <T> WebAsyncTask<T> createWebAsyncTask(long timeout, Callable<T> task) {
        WebAsyncTask<T> result = new WebAsyncTask<>(timeout, adminFrontTaskExecutor, task);

        // 设置超时回调
        result.onTimeout(() -> {
            log.info("timeout callback");
            return (T) ("timeout callback");
        });

        // 设置完成回调
        result.onCompletion(() -> log.info("finish callback"));

        return result;
    }
}