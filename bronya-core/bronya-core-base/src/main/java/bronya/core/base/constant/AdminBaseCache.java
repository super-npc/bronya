package bronya.core.base.constant;

import com.google.common.collect.HashBasedTable;

public class AdminBaseCache {
    /**
     * <basePackage, className, class>
     * 扫描到的java amis要生成的bean,用于代码生成
     */
    public static HashBasedTable<String, String, Class<?>> scanProjectTableBean;
}
