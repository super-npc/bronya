package bronya.admin.module.scheduled.dto;

import bronya.shared.module.common.type.ProcessStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CronRunningDto {
    private final String hutoolScheduleId;
    private final ScheduledConfig config;
    private ProcessStatus process = ProcessStatus.CREATED;
}
