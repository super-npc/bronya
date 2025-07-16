package bronya.admin.module.scheduled.dto;

import bronya.shared.module.common.type.ProcessStatus;
import lombok.Data;
import org.dromara.hutool.core.date.DateUtil;

@Data
public class TaskLog {
    private ProcessStatus process;
    private String beanName;
    private String cron;
    private String params;
    private long start;
    private long end;

    @Override
    public String toString() {
        long cost = end - start;
        return STR."\{process.getDesc()}-\{beanName},\{cron},cost:\{DateUtil.formatBetween(cost)} params:\{params}";
    }
}
