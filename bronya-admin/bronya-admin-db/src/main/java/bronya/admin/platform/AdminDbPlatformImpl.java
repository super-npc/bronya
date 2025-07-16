package bronya.admin.platform;

import bronya.shared.module.platform.dto.SitePage;
import bronya.admin.module.db.amis.service.BronyaAdminBaseSiteService;
import bronya.shared.module.platform.dto.AmisBeanDto;

import bronya.shared.module.platform.dto.AmisSiteDto;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.common.vo.ResultVO;
import bronya.shared.module.platform.dto.SiteDto;
import bronya.shared.module.user.SysUserVo;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.tree.MapTree;
import org.dromara.hutool.extra.spring.SpringUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class AdminDbPlatformImpl implements IPlatform {
    private final BronyaAdminBaseSiteService siteService = SpringUtil.getBean(BronyaAdminBaseSiteService.class);

    @Override
    public String info() {
        return "数据库模块";
    }

    @Override
    public Optional<String> upload(File file) {
        return Optional.empty();
    }

    @Override
    public boolean isLoginRequired() {
        return false;
    }

    @Override
    public void checkPermissionView(AmisBeanDto amisBeanDto) {

    }

    @Override
    public void checkPermissionCreate(AmisBeanDto amisBeanDto) {

    }

    @Override
    public void checkPermissionUpdate(AmisBeanDto amisBeanDto) {

    }

    @Override
    public void checkPermissionDelete(AmisBeanDto amisBeanDto) {

    }

    @Override
    public Optional<SiteDto> site(AmisSiteDto amisSiteDto) {
        String site = amisSiteDto.getSite();
        if (StrUtil.isNotBlank(site)) {
            Map<String, List<Class<?>>> menuTreeBySite = siteService.findMenuTreeBySite(site);
            List<MapTree<String>> mapTrees = siteService.buildTree(menuTreeBySite, Lists.newArrayList(),Lists.newArrayList());
            return Optional.of(new SiteDto(new SitePage(mapTrees),Maps.newHashMap()));
        }

        // 没有指定,则使用第一棵树
        Map<String, List<Class<?>>> menuTreeBySite = siteService.findMenuTreeBySite(siteService.findFirstSite());
        List<MapTree<String>> mapTrees = siteService.buildTree(menuTreeBySite, Lists.newArrayList(),Lists.newArrayList());
        Map<String, Object> cookies = Maps.newHashMap();
        cookies.put("site", site);
//        result.setCookies(cookies);
        return Optional.of(new SiteDto(new SitePage(mapTrees),cookies));
    }

    @Override
    public Optional<SysUserVo> getLoginInfo() {
        return Optional.empty();
    }
}
