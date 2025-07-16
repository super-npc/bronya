package bronya.admin.module.db.togglz.cfg;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.Feature;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.jdbc.JDBCStateRepository;
import org.togglz.core.user.SimpleFeatureUser;
import org.togglz.core.user.UserProvider;

@Configuration
@RequiredArgsConstructor
public class TogglzFeatureConfig implements TogglzConfig {
    private final DataSource dataSource;

    @Override
    public Class<? extends Feature> getFeatureClass() {
        return EnvFeature.class; // 返回定义的特性枚举类
    }

    /**
     * 存放数据库需要建立这张表
     * CREATE TABLE `TOGGLZ` (
     *   `FEATURE_NAME` varchar(100) NOT NULL,
     *   `FEATURE_ENABLED` int(11) NOT NULL,
     *   `STRATEGY_ID` varchar(200) DEFAULT NULL,
     *   `STRATEGY_PARAMS` varchar(2000) DEFAULT NULL,
     *   PRIMARY KEY (`FEATURE_NAME`)
     * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */
    @Override
    public StateRepository getStateRepository() {
        // return new InMemoryStateRepository(); // 放内存
        return JDBCStateRepository.newBuilder(dataSource).build(); // 使用 JDBC 存储特性状态
    }

    @Override
    public UserProvider getUserProvider() {
        // 定义用户提供者
        return () -> new SimpleFeatureUser("admin", true);
    }

    @Bean
    public FeatureManager featureManager() {
        return new FeatureManagerBuilder().featureEnum(EnvFeature.class) // 绑定特性枚举类
            .stateRepository(getStateRepository()) // 设置状态存储库
            .userProvider(getUserProvider()) // 设置用户提供者
            .build();
    }
}