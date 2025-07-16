package bronya.admin.module.db.amis.service;

import javax.sql.DataSource;

import org.dromara.hutool.core.reflect.method.MethodUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.db.config.DbConfig;
import org.dromara.hutool.db.dialect.DialectFactory;
import org.dromara.hutool.db.dialect.impl.MysqlDialect;
import org.dromara.hutool.db.ds.DSWrapper;
import org.dromara.mpe.autotable.annotation.Table;
import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.github.gavlyukovskiy.boot.jdbc.decorator.DecoratedDataSource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatasourceService {
    private final DataSource dataSource;

    public DSWrapper getDSWrapper(Class<?> mainClass) {
        DataSource ds = this.getDs(mainClass);
        DbConfig dbConfig = new DbConfig();
        DialectFactory.getDialect(ds);
        MysqlDialect mysqlDialect = new MysqlDialect(dbConfig);
        dbConfig.setDialect(mysqlDialect);
        return DSWrapper.wrap(ds, dbConfig);
    }

    public DSWrapper getDSWrapper(DataSource ds) {
        DbConfig dbConfig = new DbConfig();
        DialectFactory.getDialect(ds);
        MysqlDialect mysqlDialect = new MysqlDialect(dbConfig);
        dbConfig.setDialect(mysqlDialect);
        return DSWrapper.wrap(ds, dbConfig);
    }

    public DataSource getDs(Class<?> mainClass) {
        Table table = mainClass.getAnnotation(Table.class);
        String dsName = table.dsName();
        // 兼容多数据源
        DynamicRoutingDataSource realDataSource =
            (DynamicRoutingDataSource)((DecoratedDataSource)dataSource).getRealDataSource();

        if (StrUtil.isNotBlank(dsName)) {
            return realDataSource.getDataSource(dsName);
        }
        // String getJdbcUrl = MethodUtil.invoke(((ItemDataSource) dataSource).getRealDataSource(), "getJdbcUrl");
        return this.getPrimaryDs();
    }

    public DataSource getPrimaryDs() {
        DynamicRoutingDataSource realDataSource =
            (DynamicRoutingDataSource)((DecoratedDataSource)dataSource).getRealDataSource();
        String primary = MethodUtil.invoke(realDataSource, "getPrimary");
        return realDataSource.getDataSource(primary);
    }
}
