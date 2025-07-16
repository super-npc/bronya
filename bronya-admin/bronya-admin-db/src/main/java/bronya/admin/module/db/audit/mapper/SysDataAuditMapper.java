package bronya.admin.module.db.audit.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import bronya.admin.module.db.audit.domain.SysDataAudit;

@Mapper
public interface SysDataAuditMapper extends BaseMapper<SysDataAudit> {
}
