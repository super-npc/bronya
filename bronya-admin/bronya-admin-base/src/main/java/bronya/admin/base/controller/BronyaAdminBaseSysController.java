package bronya.admin.base.controller;

import bronya.admin.base.annotation.AmisSite;
import bronya.admin.base.cfg.git.GitBronyaCfg;
import bronya.admin.base.cfg.git.GitProjectCfg;
import bronya.admin.base.util.SiteUtil;
import bronya.core.base.annotation.amis.db.resp.TopRightHeaderResp;
import bronya.core.base.annotation.amis.db.resp.TopRightHeaderResp.SubSystemButton;
import bronya.shared.module.common.constant.AdminBaseConstant;
import bronya.core.base.annotation.amis.db.resp.AmisAppInfo;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.platform.dto.AmisSiteDto;
import bronya.shared.module.common.vo.ResultVO;
import bronya.core.base.annotation.amis.page.Operation.BtnLevelType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/base/sys")
@RequiredArgsConstructor
public class BronyaAdminBaseSysController {
    private final IPlatform platformService;
    private final GitBronyaCfg gitBronyaCfg;
    private final GitProjectCfg gitProjectCfg;

    @GetMapping("/change-site")
    public ResultVO<?> changeSite(@RequestParam("site") String siteType) {
        ResultVO<?> res = ResultVO.success("ok");
        Map<String, Object> cookies = Maps.newHashMap();
        cookies.put("site", siteType);
        res.setCookies(cookies);
        return res;
    }

    @GetMapping("/top-right-header")
    public TopRightHeaderResp topRightHeader(@AmisSite AmisSiteDto amisSiteDto) { // todo 需要鉴权
        // todo 需要校验用户角色是否有该系统,后续做
        HashBasedTable<String, String, List<Class<?>>> table = SiteUtil.getTables(AdminBaseConstant.AMIS_TABLES);
        List<SubSystemButton> subSystemButtons = table.rowKeySet().stream().map(label -> {
//            AmisApi amisApi = new AmisApi(Method.GET, STR."/admin/base/sys/change-site?site=\{label}");
            SubSystemButton subSystemButton = new SubSystemButton(label);
            subSystemButton.setLevel(BtnLevelType.light);
            if (label.equals(amisSiteDto.getSite())) {
                // 选中按钮
                subSystemButton.setLevel(BtnLevelType.primary);
            }
            return subSystemButton;
        }).toList();
        TopRightHeaderResp topRightHeaderResp = new TopRightHeaderResp(subSystemButtons);
        platformService.getLoginInfo().ifPresent(topRightHeaderResp::setLoginUser);
        return topRightHeaderResp;
    }

    @GetMapping("/app-info")
    public AmisAppInfo appInfo() {
        AmisAppInfo amisAppInfo = new AmisAppInfo();
        amisAppInfo.setAppName("我的应用");
        amisAppInfo.setGitBaseVersion(gitBronyaCfg.getGitVersion());
        amisAppInfo.setGitProjectVersion(gitProjectCfg.getGitVersion());

        return amisAppInfo;
    }
}
