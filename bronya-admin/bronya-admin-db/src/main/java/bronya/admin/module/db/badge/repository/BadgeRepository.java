package bronya.admin.module.db.badge.repository;

import bronya.admin.module.db.badge.mapper.dto.BeanCountsDto;
import bronya.shared.module.common.type.ReadStatus;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import bronya.admin.module.db.badge.domain.Badge;
import bronya.admin.module.db.badge.mapper.BadgeMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BadgeRepository extends CrudRepository<BadgeMapper, Badge> {
    private final BadgeMapper badgeMapper;

    public boolean readAll(Class<?> cla) {
        LambdaUpdateWrapper<Badge> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Badge::getBean, cla.getSimpleName());
        updateWrapper.eq(Badge::getReadStatus, ReadStatus.UNREAD);

        Badge badge = new Badge();
        badge.setReadStatus(ReadStatus.READ);
        return this.update(badge, updateWrapper);
    }

    public void read(Class<?> cla, List<Long> primaryKey) {
        if (CollUtil.isEmpty(primaryKey)) {
            return;
        }
        LambdaUpdateWrapper<Badge> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Badge::getBean, cla.getSimpleName());
        updateWrapper.in(Badge::getPrimaryKey, primaryKey);

        Badge badge = new Badge();
        badge.setReadStatus(ReadStatus.READ);
        this.update(badge, updateWrapper);
    }

    public List<Long> findUnReadPrimaryKey(Class<?> cla, List<Object> primaryKeys) {
        if (CollUtil.isEmpty(primaryKeys)) {
            return Lists.newArrayList();
        }
        LambdaQueryWrapper<Badge> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Badge::getPrimaryKey);
        queryWrapper.eq(Badge::getReadStatus, ReadStatus.UNREAD);
        queryWrapper.eq(Badge::getBean, cla.getSimpleName());
        queryWrapper.in(Badge::getPrimaryKey, primaryKeys);
        return this.list(queryWrapper).stream().map(Badge::getPrimaryKey).toList();
    }

    public List<BeanCountsDto> findBeanCounts() {
        return badgeMapper.findBeanCounts();
    }

}
