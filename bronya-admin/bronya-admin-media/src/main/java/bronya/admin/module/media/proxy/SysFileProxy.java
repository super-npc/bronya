package bronya.admin.module.media.proxy;

import java.util.Map;

import org.springframework.stereotype.Service;

import bronya.admin.module.db.amis.util.BronyaAdminBaseAmisUtil;
import bronya.admin.module.media.domain.SysFile;
import bronya.admin.module.media.domain.SysFile.SysFileExt;
import bronya.admin.module.media.repository.SysFileRepository;
import bronya.core.base.dto.DataProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SysFileProxy extends DataProxy<SysFile> {
    private final SysFileRepository sysFileRepository;

    @Override
    public void table(Map<String, Object> map) {
//        SysFile sysFile = BeanUtil.toBean(BronyaAdminBaseAmisUtil.map2obj(map), SysFile.class);
        SysFileExt sysFileExt = new SysFileExt();

        // 配置拓展属性信息

        map.putAll(BronyaAdminBaseAmisUtil.obj2map(SysFile.class, sysFileExt));
    }
}
