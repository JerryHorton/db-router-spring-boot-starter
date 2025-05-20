package cn.cug.sxy.middleware.db.router.dynamic;

import cn.cug.sxy.middleware.db.router.DBContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @version 1.0
 * @Date 2025/5/20 09:28
 * @Description 动态数据源
 * @Author jerryhotton
 */

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Value("${mini-db-router.jdbc.datasource.default-datasource}")
    private String defaultDataSource;

    public DynamicDataSource() {
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return null == DBContextHolder.getDBKey() ? this.defaultDataSource : "db" + DBContextHolder.getDBKey();
    }

}
