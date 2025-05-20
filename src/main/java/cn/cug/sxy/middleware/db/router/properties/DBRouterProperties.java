package cn.cug.sxy.middleware.db.router.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2025/5/20 10:58
 * @Description 数据库路由配置
 * @Author jerryhotton
 */

@ConfigurationProperties(prefix = "mini-db-router.jdbc.datasource")
public class DBRouterProperties {

    /**
     * 数据库数量
     */
    private Integer dbCount;
    /**
     * 表数量
     */
    private Integer tbCount;
    /**
     * 路由键
     */
    private String routerKey;
    /**
     * 分库数据源 key 列表
     */
    private String list;
    /**
     * 默认主库数据源
     */
    private String defaultDataSource;
    /**
     * 每个数据源的配置项
     */
    private Map<String, DataSourceConfig> datasourceConfigs = new HashMap<>();

    public static class DataSourceConfig {

        /**
         * 驱动类名
         */
        private String driverClassName;
        /**
         * 连接地址
         */
        private String url;
        /**
         * 用户名
         */
        private String username;
        /**
         * 密码
         */
        private String password;
        /**
         * 数据源连接池类名
         */
        private String typeClassName;
        /**
         * 连接池配置
         */
        private Map<String, Object> pool = new HashMap<>();

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTypeClassName() {
            return typeClassName;
        }

        public void setTypeClassName(String typeClassName) {
            this.typeClassName = typeClassName;
        }

        public Map<String, Object> getPool() {
            return pool;
        }

        public void setPool(Map<String, Object> pool) {
            this.pool = pool;
        }
    }

    public Integer getDbCount() {
        return dbCount;
    }

    public Integer setDbCount(Integer dbCount) {
        return this.dbCount = dbCount;
    }

    public Integer getTbCount() {
        return tbCount;
    }

    public void setTbCount(Integer tbCount) {
        this.tbCount = tbCount;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public Map<String, DataSourceConfig> getDatasourceConfigs() {
        return datasourceConfigs;
    }

    public void setDatasourceConfigs(Map<String, DataSourceConfig> datasourceConfigs) {
        this.datasourceConfigs = datasourceConfigs;
    }

    public String getRouterKey() {
        return routerKey;
    }

    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

    public String getDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(String defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

}
