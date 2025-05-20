package cn.cug.sxy.middleware.db.router.strategy;

/**
 * @version 1.0
 * @Date 2025/5/20 15:00
 * @Description 分库分表策略
 * @Author jerryhotton
 */

public interface IDBRouterStrategy {

    void doRouter(String key);

    void setDbIdx(int dbIdx);

    void setTbIdx(int tbIdx);

    int dbCount();

    int tbCount();

    void clear();

}
