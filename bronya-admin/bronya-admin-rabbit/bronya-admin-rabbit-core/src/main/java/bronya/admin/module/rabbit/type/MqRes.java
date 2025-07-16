package bronya.admin.module.rabbit.type;

import org.dromara.hutool.core.date.DateUnit;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MqRes {
    private DateUnit unit;
    private int time;
    private MsStatus msStatus;

    public enum MsStatus {
        success, retry;
    }

    /**
     * 修改策略了,废弃
     */
    @Deprecated
    public MqRes success() {
        this.msStatus = MsStatus.success;
        return this;
    }

    /**
     * 修改策略了,废弃
     *
     * @param unit
     * @param time
     * @return
     */
    @Deprecated
    public MqRes retry(DateUnit unit, int time) {
        this.msStatus = MsStatus.retry;
        this.unit = unit;
        this.time = time;
        return this;
    }
}
