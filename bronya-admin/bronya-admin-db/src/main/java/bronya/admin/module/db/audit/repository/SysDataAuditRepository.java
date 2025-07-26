package bronya.admin.module.db.audit.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import bronya.admin.module.db.audit.domain.SysDataAudit;
import bronya.admin.module.db.audit.mapper.SysDataAuditMapper;

@Repository
public class SysDataAuditRepository extends CrudRepository<SysDataAuditMapper, SysDataAudit> {
}
