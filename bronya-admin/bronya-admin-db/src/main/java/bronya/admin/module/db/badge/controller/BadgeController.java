package bronya.admin.module.db.badge.controller;

import org.dromara.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import bronya.admin.module.db.badge.service.BadgeService;
import bronya.shared.module.common.constant.AdminBaseConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/badge")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping("/random-add-badge")
    public boolean randomAddBadge() {
        Class<?> clz = RandomUtil.randomEle(Lists.newArrayList(AdminBaseConstant.AMIS_TABLES.values()));
        badgeService.addUnRead(clz, RandomUtil.randomLong(), RandomUtil.randomString(10));
        return true;
    }
}
