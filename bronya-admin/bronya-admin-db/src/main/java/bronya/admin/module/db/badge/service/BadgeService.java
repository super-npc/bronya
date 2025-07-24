package bronya.admin.module.db.badge.service;

import java.util.List;

import bronya.admin.module.db.threadpool.GlobalThreadPool;
import bronya.admin.module.db.threadpool.service.SysThreadPoolService;
import com.google.common.util.concurrent.FutureCallback;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.lang.Assert;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import bronya.admin.module.db.badge.domain.Badge;
import bronya.admin.module.db.badge.mapper.dto.BeanCountsDto;
import bronya.admin.module.db.badge.repository.BadgeRepository;
import bronya.shared.module.common.constant.AdminBaseConstant;
import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final SysThreadPoolService threadPoolService;

    public void addUnRead(Class<?> cla, Long primaryKey, String reason) {
        threadPoolService.callback(GlobalThreadPool.BADGE_UNREAD, () -> {
            String beanName = cla.getSimpleName();
            Assert.isTrue(AdminBaseConstant.AMIS_TABLES.containsKey(beanName), "插入徽标异常,非库表:{}", beanName);

            Badge badge = new Badge();
            badge.setBean(beanName);
            badge.setPrimaryKey(primaryKey);
            badge.setReason(reason);
            badgeRepository.save(badge);
            return badge;
        }, new FutureCallback<>() {
            @Override
            public void onSuccess(Badge badge) {
                log.info("插入徽标完成:{},主键:{},原因:{}", cla.getSimpleName(), primaryKey, reason);
            }

            @Override
            public void onFailure(@NotNull Throwable t) {
                log.error(STR."插入徽标异常:\{cla.getSimpleName()}", t);
            }
        });
    }

    public boolean toReadAll(Class<?> cla) {
        return badgeRepository.readAll(cla);
    }

    /**
     * 标记为已读
     */
    public void toRead(Class<?> cla, List<Long> primaryKey) {
        threadPoolService.callback(GlobalThreadPool.BADGE_READ, () -> {
            badgeRepository.read(cla, primaryKey);
            return true;
        }, new FutureCallback<>() {
            @Override
            public void onSuccess(Boolean flag) {
                log.debug("标记读取徽标完成:{},主键:{}", cla.getSimpleName(), primaryKey);
            }

            @Override
            public void onFailure(@NotNull Throwable t) {
                log.warn(STR."标记读取徽标异常:\{cla.getSimpleName()},主键:\{primaryKey}", t);
            }
        });
    }

    public List<Long> findUnReadPrimaryKey(Class<?> cla, List<Object> primaryKeys) {
        return badgeRepository.findUnReadPrimaryKey(cla, primaryKeys);
    }

    public List<BeanCountsDto> findBeanCounts() {
        return badgeRepository.findBeanCounts();
    }
}
