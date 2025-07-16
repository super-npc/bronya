package bronya.admin.module.scheduled.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@RequiredArgsConstructor
public class ScheduledConfig {
    private final String remark;
    private final String params;

    /**
     * 默认,数据库有以数据库为准
     */
    private String defaultCron = "0/10 * * * * ?";

    /**
     * 是否集群配置
     */
    private boolean cluster;
    /**
     * 超时时间
     */
    private long expireTime = 5;

    /**
     * 超时单位
     */
    private TimeUnit unit = TimeUnit.MINUTES;

    /**
     * 记录日志
     */
    private boolean recordLog = true;

}