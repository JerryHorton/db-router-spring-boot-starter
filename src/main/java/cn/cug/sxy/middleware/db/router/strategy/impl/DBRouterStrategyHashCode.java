package cn.cug.sxy.middleware.db.router.strategy.impl;

import cn.cug.sxy.middleware.db.router.DBContextHolder;
import cn.cug.sxy.middleware.db.router.DBRouterConfiguration;
import cn.cug.sxy.middleware.db.router.strategy.IDBRouterStrategy;
import cn.cug.sxy.middleware.db.router.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version 1.0
 * @Date 2025/5/20 15:06
 * @Description hashCode 路由策略
 * @Author jerryhotton
 */

public class DBRouterStrategyHashCode implements IDBRouterStrategy {

    private final Logger logger = LoggerFactory.getLogger(DBRouterStrategyHashCode.class);

    private final DBRouterConfiguration dbRouterConfiguration;

    public DBRouterStrategyHashCode(DBRouterConfiguration dbRouterConfiguration) {
        this.dbRouterConfiguration = dbRouterConfiguration;
    }

    @Override
    public void doRouter(String routerKey) {
        if (StringUtils.isBlank(routerKey)) {
            throw new IllegalArgumentException("routerKey can't be null or empty");
        }
        // 数据库数量
        int dbCount = dbRouterConfiguration.getDbCount();
        // 每个数据库中的表数量
        int tbCount = dbRouterConfiguration.getTbCount();
        // 总分片数
        int total = dbCount * tbCount;
        // 使用扰动函数，提升 hash 分布均匀性
        int h = routerKey.hashCode();
        int hash = h ^ (h >>> 16);
        int idx = (total - 1) & hash;
        // 计算分库索引(从1开始)
        int dbIdx = idx / tbCount + 1;
        // 计算分表索引(从0开始)
        int tbIdx = idx % tbCount;
        // 设置上下文
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
        DBContextHolder.setTBKey(String.format("%03d", tbIdx));

        if (logger.isDebugEnabled()) {
            logger.debug("路由计算 routerKey:{} dbIdx:{} tbIdx:{}", routerKey, dbIdx, tbIdx);
        }
    }

    @Override
    public void setDbIdx(int dbIdx) {
        DBContextHolder.setDBKey(String.format("%02d", dbIdx));
    }

    @Override
    public void setTbIdx(int tbIdx) {
        DBContextHolder.setTBKey(String.format("%03d", tbIdx));
    }

    @Override
    public int dbCount() {
        return this.dbRouterConfiguration.getDbCount();
    }

    @Override
    public int tbCount() {
        return this.dbRouterConfiguration.getTbCount();
    }

    @Override
    public void clear() {
        DBContextHolder.clearDBKey();
        DBContextHolder.clearTBKey();
    }

}
