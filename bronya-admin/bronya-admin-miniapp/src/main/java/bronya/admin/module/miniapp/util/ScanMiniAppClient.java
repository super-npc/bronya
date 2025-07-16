package bronya.admin.module.miniapp.util;

import java.util.Set;

import org.dromara.hutool.core.reflect.ClassUtil;

import bronya.shared.module.miniapp.MiniAppTypeScriptController;
import bronya.shared.module.util.MyRefUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class ScanMiniAppClient {
    /**
     * 扫描小程序controller
     */
    public Set<Class<?>> getMiniAppControllers(final Class<?> rootClass) {
        Set<Class<?>> miniAppControllers =
            MyRefUtil.findAnnotation(ClassUtil.getPackage(rootClass), MiniAppTypeScriptController.class);
        log.info("小程序控制器数量:{}", miniAppControllers);
        return miniAppControllers;
    }

}
