package bronya.core.base.util;

import java.io.File;

import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.StrUtil;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AmisPathUtil {

    /**
     * 获取关联所需要的json访问路径
     * 
     * @return /static/pages/xxx/domain/Xxx.json
     */
    public String getStaticDomainFile(Class<?> tableClass) {
        String pagesRootPath = StrUtil.format("/json");
        String packagePath = ClassUtil.getPackagePath(tableClass); // demo/one/module/naruto/domain
        File domainFolder = new File(pagesRootPath, packagePath);
        return new File(domainFolder, STR."\{tableClass.getSimpleName()}.json").getPath();
    }
}
