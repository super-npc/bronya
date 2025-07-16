package bronya.admin.autotable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.alibaba.cola.exception.SysException;
import org.dromara.autotable.core.constants.DatabaseDialect;
import org.dromara.autotable.core.interceptor.BuildTableMetadataInterceptor;
import org.dromara.autotable.core.strategy.TableMetadata;
import org.dromara.autotable.core.strategy.mysql.data.MysqlColumnMetadata;
import org.dromara.autotable.core.strategy.mysql.data.MysqlTableMetadata;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.lang.Assert;
import org.dromara.hutool.core.reflect.FieldUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Component;

import bronya.shared.module.common.type.AmisEnum;

@Component
public class MyBuildTableMetadataInterceptor implements BuildTableMetadataInterceptor {
    /**
     * 拦截器
     *
     * @param databaseDialect 数据库方言：MySQL、PostgreSQL、SQLite
     * @param tableMetadata   表元数据：MysqlTableMetadata、DefaultTableMetadata
     */
    public void intercept(final String databaseDialect, final TableMetadata tableMetadata) {
        // DatabaseDialect.MYSQL是框架内置常量，可以直接使用
        if (DatabaseDialect.MySQL.equals(databaseDialect)) {
            this.mysql(tableMetadata);
        }
    }

    private void mysql(TableMetadata tableMetadata) {
        if (tableMetadata instanceof MysqlTableMetadata mysqlTableMetadata) {
            // 此处会修改表注释，在原注释后，添加一段文本
            mysqlTableMetadata.setComment(mysqlTableMetadata.getComment());
            List<MysqlColumnMetadata> columnMetadataList = mysqlTableMetadata.getColumnMetadataList();
            for (MysqlColumnMetadata columnMetadata : columnMetadataList) {
                Field field = FieldUtil.getField(mysqlTableMetadata.getEntityClass(),
                        StrUtil.toCamelCase(columnMetadata.getName()));
                Assert.notNull(field, () -> new SysException(STR."不存在字段:\{mysqlTableMetadata.getEntityClass().getSimpleName()}.\{columnMetadata.getName()}"));
                if (AmisEnum.class.isAssignableFrom(field.getType())) {
                    List<String> collect = Arrays.stream(field.getType().getEnumConstants()).map(e -> {
                        if (e instanceof AmisEnum amisEnum) {
                            String value = amisEnum.toString();
                            String desc = amisEnum.getDesc();
                            return STR."\{value}:\{desc}";
                        }
                        return null;
                    }).filter(Objects::nonNull).toList();
                    columnMetadata.setComment(STR."\{columnMetadata.getComment()} \{CollUtil.join(collect, ",")}");
                    columnMetadata.getType().setLength(50); // 固定枚举长度
                }
            }
        }
    }
}