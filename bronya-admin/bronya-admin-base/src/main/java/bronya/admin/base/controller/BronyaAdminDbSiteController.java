package bronya.admin.base.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dromara.hutool.core.tree.MapTree;
import org.dromara.hutool.core.tree.TreeNode;
import org.dromara.hutool.core.tree.TreeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import bronya.admin.base.annotation.AmisSite;
import bronya.admin.base.util.SiteNodeUtil;
import bronya.shared.module.common.vo.ResultVO;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.platform.dto.AmisSiteDto;
import bronya.shared.module.platform.dto.SiteDto;
import bronya.shared.module.platform.dto.SitePage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin/base/site")
@RequiredArgsConstructor
public class BronyaAdminDbSiteController {
    private final IPlatform platformService;

    @SneakyThrows
    @GetMapping
    public ResultVO<?> site(@AmisSite AmisSiteDto amisSiteDto) {
        Optional<SiteDto> site = platformService.site(amisSiteDto);
        if (site.isEmpty()) {
            return ResultVO.failed("不支持site");
        }
        SiteDto siteDto = site.get();
        SitePage sitePage = siteDto.getSitePage();
        List<MapTree<String>> pages = sitePage.getPages();

//        pages.addAll(0, this.customSite()); // 自定义菜单 todo 后续需要自定义

        ResultVO<?> success = ResultVO.success(sitePage);
        success.setCookies(siteDto.getCookies());
        return success;
    }

    private List<MapTree<String>> customSite() {
        TreeNode<String> nodeGroup = new TreeNode<>("main", "0", "我的", "0");

        TreeNode<String> dashboard = new TreeNode<>("dashboard", "main", "dashboard", "0");
        Map<String, Object> extra = Maps.newHashMap();
        extra.put("url", "/");
        extra.put("icon", "/icon/主页.svg");
        extra.put("schemaApi", "get:/public/amis/dashboard.json");
//        extra.put("badge",5);
//        extra.put("badgeClassName","bg-info");
        dashboard.setExtra(extra);

        TreeNode<String> personal = new TreeNode<>("personal", "main", "个人中心", "1");
        Map<String, Object> personalExtra = Maps.newHashMap();
        personalExtra.put("url", "/personal");
        personalExtra.put("icon", "/icon/主页.svg");
        personalExtra.put("schemaApi", "get:/public/amis/dashboard.json");
//        extra.put("badge",8);
//        extra.put("badgeClassName","bg-info");
        personal.setExtra(personalExtra);

        List<TreeNode<String>> treeNodes = Lists.newArrayList(nodeGroup, dashboard, personal);

        return TreeUtil.build(treeNodes, "0", SiteNodeUtil.getTreeNodeConfig(), (treeNode, treeConf) -> {
            treeConf.setId(treeNode.getId());
            treeConf.setParentId(treeNode.getParentId());
            treeConf.setWeight(SiteNodeUtil.getAsciiSum(treeNode.getWeight().toString()));
            treeConf.setName(treeNode.getName());
            if (treeNode.getExtra() != null) {
                treeNode.getExtra().forEach(treeConf::putExtra);
            }
        });
    }
}
