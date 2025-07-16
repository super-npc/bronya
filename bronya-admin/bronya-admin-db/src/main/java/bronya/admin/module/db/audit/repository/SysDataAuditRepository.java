package bronya.admin.module.db.audit.repository;

import org.dromara.mpe.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import bronya.admin.module.db.audit.domain.SysDataAudit;
import bronya.admin.module.db.audit.mapper.SysDataAuditMapper;

@Repository
public class SysDataAuditRepository extends BaseRepository<SysDataAuditMapper, SysDataAudit> {
}
