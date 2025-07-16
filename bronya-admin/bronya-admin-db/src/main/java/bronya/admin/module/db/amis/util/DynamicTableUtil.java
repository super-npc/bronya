package bronya.admin.module.db.amis.util;

import java.util.List;

import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrUtil;

import com.google.common.collect.Lists;

import bronya.core.base.constant.AmisPage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DynamicTableUtil {

    /**
     * 是否为动态表
     */
    public boolean isDynamicTable(Class<?> beanClass) {
        AmisPage amisPage = beanClass.getAnnotation(AmisPage.class);
        if (amisPage == null) {
            return false;
        }
        AmisPage.DynamicTable dynamicTable = amisPage.dynamicTable();
        String[] cols = dynamicTable.cols();
        return cols.length != 0;
    }

    /**
     * 获取动态表格名称
     */
    public String getDynamicTableName(Object obj) {
        String dynamicTableNameSuffix = getDynamicTableNameSuffix(obj);
        String tableMainName = StrUtil.toUnderlineCase(obj.getClass().getSimpleName());
        return STR."\{tableMainName}_\{dynamicTableNameSuffix}";
    }

    /**
     * 获取分表的后缀构成
     */
    public String getDynamicTableNameSuffix(Object obj) {
        Class<?> aClass = obj.getClass();
        AmisPage amisPage = aClass.getAnnotation(AmisPage.class);
        Assert.notNull(amisPage, "分表未配置页面信息:{}", aClass.getSimpleName());
        AmisPage.DynamicTable dynamicTable = amisPage.dynamicTable();
        String[] cols = dynamicTable.cols();
        Assert.notNull(amisPage, "分表未配置字段信息:{}", aClass.getSimpleName());
        List<Object> colValue = Lists.newArrayList();
        for (String col : cols) {
            Object fieldValue = FieldUtil.getFieldValue(obj, col);
            Assert.notNull(fieldValue, STR."分表必备字段 \{aClass}.\{col} 值为null");
            colValue.add(fieldValue);
        }
        return CollUtil.join(colValue, "_");
    }
}
