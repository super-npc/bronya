package bronya.shared.module.platform;

import java.io.File;
import java.util.Optional;

import bronya.shared.module.platform.dto.AmisBeanDto;
import bronya.shared.module.platform.dto.AmisSiteDto;
import bronya.shared.module.platform.dto.SiteDto;
import bronya.shared.module.user.SysUserVo;

public interface IPlatform {
    String info();

    Optional<String> upload(File file);

    /**
     * 是否鉴权
     */
    boolean isLoginRequired();

    void checkPermissionView(AmisBeanDto amisBeanDto);

    void checkPermissionCreate(AmisBeanDto amisBeanDto);

    void checkPermissionUpdate(AmisBeanDto amisBeanDto);

    void checkPermissionDelete(AmisBeanDto amisBeanDto);

    Optional<SiteDto> site(AmisSiteDto amisSiteDto);

    Optional<SysUserVo> getLoginInfo();
}
