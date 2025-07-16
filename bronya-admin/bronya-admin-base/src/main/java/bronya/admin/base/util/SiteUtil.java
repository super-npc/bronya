package bronya.admin.base.util;

import bronya.core.base.constant.AmisPage;
import bronya.core.base.annotation.amis.page.Menu;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.annotation.AnnotationUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@UtilityClass
public class SiteUtil {
    /**
     * @return <模块, group, List<Class<?>> 例如: <系统,权限管理,List<Class<?>>
     */
    public HashBasedTable<String, String, List<Class<?>>> getTables(Map<String, Class<?>> amisTables){
        Collection<Class<?>> classes = amisTables.values();
        return getTables(Lists.newArrayList(classes));
    }

    public HashBasedTable<String, String, List<Class<?>>> getTables(List<Class<?>> classes){
        HashBasedTable<String, String, List<Class<?>>> table = HashBasedTable.create();

        for (Class<?> tableClass : classes) {
            AmisPage amisPage = AnnotationUtil.getAnnotation(tableClass, AmisPage.class);
            if (amisPage == null || !amisPage.menu().show()) {
                continue;
            }
            Menu menu = amisPage.menu();
            List<Class<?>> list = table.get(menu.module(), menu.group());
            if (list == null) {
                list = Lists.newArrayList();
            }
            list.add(tableClass);
            table.put(menu.module(), menu.group(), list);
        }
        return table;
    }
}
