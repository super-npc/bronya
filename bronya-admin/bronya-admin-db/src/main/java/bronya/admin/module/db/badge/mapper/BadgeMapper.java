package bronya.admin.module.db.badge.mapper;

import bronya.admin.module.db.badge.mapper.dto.BeanCountsDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bronya.admin.module.db.badge.domain.Badge;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BadgeMapper extends BaseMapper<Badge> {

    List<BeanCountsDto> findBeanCounts();
}
