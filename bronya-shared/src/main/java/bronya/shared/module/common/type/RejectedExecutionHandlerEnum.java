package bronya.shared.module.common.type;

import bronya.shared.module.common.type.AmisEnum;
import com.alibaba.cola.exception.SysException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RejectedExecutionHandlerEnum implements AmisEnum {
    // AbortPolicy：为线程池默认的拒绝策略，该策略直接抛异常处理。 DiscardPolicy：直接抛弃不处理。 DiscardOldestPolicy：丢弃队列中最老的任务。
    // * CallerRunsPolicy：将任务分配给当前执行execute方法线程来处理。
    AbortPolicy("中止策略", Color.深岩暗蓝灰色), DiscardPolicy("丢弃策略", Color.深红色), DiscardOldestPolicy("丢弃最旧任务", Color.沙棕色),
    CallerRunsPolicy("调用者运行策略", Color.森林绿);

    public static RejectedExecutionHandlerEnum parse(String name) {
        return Arrays.stream(RejectedExecutionHandlerEnum.values()).filter(temp -> temp.name().equals(name)).findFirst()
            .orElseThrow(() -> new SysException(STR."未知策略:\{name}"));
    }

    private final String desc;
    private final Color color;
}