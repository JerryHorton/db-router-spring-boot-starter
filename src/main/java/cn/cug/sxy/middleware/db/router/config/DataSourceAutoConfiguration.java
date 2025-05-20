package cn.cug.sxy.middleware.db.router.config;

import cn.cug.sxy.middleware.db.router.DBRouterConfiguration;
import cn.cug.sxy.middleware.db.router.DBRouterJoinPoint;
import cn.cug.sxy.middleware.db.router.dynamic.DynamicDataSource;
import cn.cug.sxy.middleware.db.router.dynamic.DynamicMybatisPlugin;
import cn.cug.sxy.middleware.db.router.properties.DBRouterProperties;
import cn.cug.sxy.middleware.db.router.strategy.IDBRouterStrategy;
import cn.cug.sxy.middleware.db.router.strategy.impl.DBRouterStrategyHashCode;
import cn.cug.sxy.middleware.db.router.util.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2025/5/20 09:46
 * @Description 配置加载和创建数据源
 * @Author jerryhotton
 */

@Configuration
@EnableConfigurationProperties(DBRouterProperties.class)
public class DataSourceAutoConfiguration {

    @Bean
    public DBRouterConfiguration dbRouterConfiguration(DBRouterProperties properties) {
        Integer dbCount = properties.getDbCount();
        Integer tbCount = properties.getTbCount();
        String routerKey = properties.getRouterKey();
        return new DBRouterConfiguration(dbCount, tbCount, routerKey);
    }

    @Bean
    public Interceptor dynamicMybatisPlugin() {
        return new DynamicMybatisPlugin();
    }

    @Bean
    public DataSource createDataSource(DBRouterProperties properties) {
        // 1. 创建数据源
        Map<Object, Object> targetDataSourceMap = new HashMap<>();
        String[] dbList = properties.getList().split(",");
        // 1.1 分库数据源
        for (String dbKey : dbList) {
            DBRouterProperties.DataSourceConfig dataSourceConfig = properties.getDatasourceConfigs().get(dbKey);
            if (null != dataSourceConfig) {
                DataSource dataSource = createDataSource(dataSourceConfig);
                targetDataSourceMap.put(dbKey, dataSource);
            }
        }
        // 1.2 主库数据源
        String defaultKey = properties.getDefaultDataSource();
        DBRouterProperties.DataSourceConfig dataSourceConfig = properties.getDatasourceConfigs().get(defaultKey);
        DataSource defaultDataSource = createDataSource(dataSourceConfig);

        // 2. 设置数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
        return dynamicDataSource;
    }

    @Bean
    public IDBRouterStrategy dbRouterStrategy(DBRouterConfiguration dbRouterConfiguration) {
        return new DBRouterStrategyHashCode(dbRouterConfiguration);
    }

    @Bean
    public DBRouterJoinPoint dbRouterJoinPoint(DBRouterConfiguration dbRouterConfiguration, IDBRouterStrategy dbRouterStrategy) {
        return new DBRouterJoinPoint(dbRouterConfiguration, dbRouterStrategy);
    }

    @Bean
    public TransactionTemplate transactionTemplate(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(transactionManager);
        transactionTemplate.setPropagationBehavior(TransactionTemplate.PROPAGATION_REQUIRED);
        return transactionTemplate;
    }

    private DataSource createDataSource(DBRouterProperties.DataSourceConfig config) {
        try {
            DataSourceProperties dataSourceProperties = new DataSourceProperties();
            dataSourceProperties.setDriverClassName(config.getDriverClassName());
            dataSourceProperties.setUrl(config.getUrl());
            dataSourceProperties.setUsername(config.getUsername());
            dataSourceProperties.setPassword(config.getPassword());

            String typeClassName = config.getTypeClassName();
            @SuppressWarnings("unchecked")
            Class<? extends DataSource> typeClass = (Class<? extends DataSource>) Class.forName(typeClassName);
            DataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(typeClass).build();

            MetaObject metaObject = SystemMetaObject.forObject(dataSource);
            for (Map.Entry<String, Object> entry : config.getPool().entrySet()) {
                String proprietyName = StringUtils.middleScoreToCamelCase(entry.getKey());
                if (metaObject.hasGetter(proprietyName)) {
                    metaObject.setValue(proprietyName, entry.getValue());
                }
            }
            return dataSource;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find datasource pool class", e);
        }
    }

}
