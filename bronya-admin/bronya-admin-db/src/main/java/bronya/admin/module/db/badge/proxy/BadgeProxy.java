package bronya.admin.module.db.badge.proxy;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.db.badge.domain.Badge;
import bronya.admin.module.db.badge.domain.Badge.BadgeExt;
import bronya.admin.module.db.badge.repository.BadgeRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.bean.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class BadgeProxy extends DataProxy<Badge> {
    private final BadgeRepository badgeRepository;

    @Override
    public void table(Map<String, Object> map) {
        Badge badge = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), Badge.class);
        BadgeExt badgeExt = new BadgeExt();

        // 配置拓展属性信息
        map.putAll(BronyaAdminBaseAmisUtil.obj2map(Badge.class, badgeExt));
    }
}
