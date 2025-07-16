package bronya.shared.module.util;

import bronya.shared.module.common.constant.AdminBaseConstant;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;
import org.dromara.hutool.core.text.StrUtil;
import org.slf4j.MDC;

@UtilityClass
public class TraceId {
    /**
     * 尽管 TRACE_ID 是一个静态变量，但它存储的是 TransmittableThreadLocal 的实例。TransmittableThreadLocal 的内部机制确保了每个线程都有自己的 TRACE_ID
     * 值副本。具体来说： 线程隔离：每个线程调用 TRACE_ID.set(traceId) 时，设置的是该线程自己的副本，不会影响其他线程的副本。
     * 线程池复用：当线程池中的线程被复用时，TransmittableThreadLocal 会自动清理或恢复线程局部变量的值，避免值被错误地传递给下一个任务。
     */
    private static final TransmittableThreadLocal<String> tl = new TransmittableThreadLocal<>();

    public void setMdcTraceId(final CharSequence template, final Object... params) {
        String traceId = StrUtil.format(template, params);
        tl.set(traceId);
        MDC.put(AdminBaseConstant.TRACE_ID, traceId);
    }

    public String getTraceId() {
        return tl.get();
    }

    public static String getMdcTradeId() {
        return MDC.get(AdminBaseConstant.TRACE_ID);
    }

    public void remove() {
        tl.remove();
        MDC.remove(AdminBaseConstant.TRACE_ID);
    }
}
