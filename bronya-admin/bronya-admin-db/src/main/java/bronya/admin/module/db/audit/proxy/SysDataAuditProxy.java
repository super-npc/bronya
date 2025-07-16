package bronya.admin.module.db.audit.proxy;

import java.util.Map;

import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.audit.domain.SysDataAudit;
import bronya.admin.module.db.audit.domain.SysDataAudit.SysDataAuditExt;
import bronya.admin.module.db.audit.repository.SysDataAuditRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysDataAuditProxy extends DataProxy<SysDataAudit> {
    private final SysDataAuditRepository sysDataAuditRepository;

    @Override
    public void table(Map<String, Object> map) {
        SysDataAudit sysDataAudit = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SysDataAudit.class);
        SysDataAuditExt sysDataAuditExt = new SysDataAuditExt();

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SysDataAudit.class, sysDataAuditExt));
    }
}
