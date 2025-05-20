package cn.cug.sxy.middleware.db.router;

/**
 * @version 1.0
 * @Date 2025/5/20 16:40
 * @Description 基础路由
 * @Author jerryhotton
 */

public class DBRouterBase {

    private String tbIdx;

    public DBRouterBase() {
    }

    public String getTbIdx() {
        return DBContextHolder.getTBKey();
    }
}
