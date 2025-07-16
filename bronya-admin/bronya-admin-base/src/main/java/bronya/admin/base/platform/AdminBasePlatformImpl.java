package bronya.admin.base.platform;

import bronya.shared.module.common.vo.ResultVO;
import bronya.shared.module.platform.IPlatform;
import bronya.shared.module.platform.dto.AmisBeanDto;
import bronya.shared.module.platform.dto.AmisSiteDto;
import bronya.shared.module.platform.dto.SiteDto;
import bronya.shared.module.user.SysUserVo;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Optional;

@RequiredArgsConstructor
public class AdminBasePlatformImpl implements IPlatform {

    @Override
    public String info(){
        return "初级模块";
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
        return Optional.empty();
    }

    @Override
    public Optional<SysUserVo> getLoginInfo() {
        return null;
    }

}
