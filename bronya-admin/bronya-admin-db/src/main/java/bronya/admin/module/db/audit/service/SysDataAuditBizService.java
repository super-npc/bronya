package bronya.admin.module.db.audit.service;

import java.util.Date;

import org.dromara.hutool.json.JSONUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import com.google.common.util.concurrent.FutureCallback;

import bronya.admin.module.db.audit.domain.SysDataAudit;
import bronya.admin.module.db.audit.repository.SysDataAuditRepository;
import bronya.admin.module.db.threadpool.GlobalThreadPool;
import bronya.admin.module.db.threadpool.service.SysThreadPoolService;
import bronya.shared.module.platform.dto.AmisBeanDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysDataAuditBizService {
    private final SysDataAuditRepository auditRepository;
    private final SysThreadPoolService threadPoolService;

    public void addAuditRecord(Long userId, AmisBeanDto amisBeanDto, Object primaryVal, Object before, Object after) {
        threadPoolService.callback(GlobalThreadPool.AUDIT_ADD_RECORD, () -> {
            SysDataAudit sysDataAudit = new SysDataAudit();
            sysDataAudit.setTableBean(amisBeanDto.getMainClassSimpleName());
            sysDataAudit.setRecordPrimaryKey(primaryVal.toString());
            sysDataAudit.setOldData(JSONUtil.toJsonPrettyStr(before));
            sysDataAudit.setNewData(JSONUtil.toJsonPrettyStr(after));
            sysDataAudit.setUpdateBy(userId);
            sysDataAudit.setUpdateTime(new Date());
            log.info("准备保存审计记录: {}", sysDataAudit);
            auditRepository.save(sysDataAudit);
            return sysDataAudit;
        }, new FutureCallback<>() {
            @Override
            public void onSuccess(SysDataAudit sysDataAudit) {
                log.info("成功保存审计记录: {}", sysDataAudit);
            }

            @Override
            public void onFailure(@NotNull Throwable t) {
                log.error("保存审计记录失败", t);
            }
        });
    }
}
