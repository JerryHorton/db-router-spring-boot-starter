package cn.cug.sxy.middleware.db.router;

/**
 * @version 1.0
 * @Date 2025/5/20 10:17
 * @Description 数据库路由配置
 * @Author jerryhotton
 */

public class DBRouterConfiguration {

    /**
     * 分库数
     */
    private int dbCount;
    /**
     * 分表数
     */
    private int tbCount;
    /**
     * 路由key
     */
    private String routerKey;

    public DBRouterConfiguration(int dbCount, int tbCount, String routerKey) {
        this.dbCount = dbCount;
        this.tbCount = tbCount;
        this.routerKey = routerKey;
    }

    public int getDbCount() {
        return dbCount;
    }

    public void setDbCount(int dbCount) {
        this.dbCount = dbCount;
    }

    public int getTbCount() {
        return tbCount;
    }

    public void setTbCount(int tbCount) {
        this.tbCount = tbCount;
    }

    public String getRouterKey() {
        return routerKey;
    }

    public void setRouterKey(String routerKey) {
        this.routerKey = routerKey;
    }

}
