package cn.cug.sxy.middleware.db.router;

/**
 * @version 1.0
 * @Date 2025/5/20 09:20
 * @Description 本地线程设置路由结果
 * @Author jerryhotton
 */

public class DBContextHolder {

    private static final ThreadLocal<String> dbKey = new ThreadLocal<>();

    private static final ThreadLocal<String> tbKey = new ThreadLocal<>();

    public static void setDBKey(String dbKeyIdx) {
        dbKey.set(dbKeyIdx);
    }

    public static String getDBKey() {
        return dbKey.get();
    }

    public static void setTBKey(String tbKeyIdx) {
        tbKey.set(tbKeyIdx);
    }

    public static String getTBKey() {
        return tbKey.get();
    }

    public static void clearDBKey() {
        dbKey.remove();
    }

    public static void clearTBKey() {
        tbKey.remove();
    }

}
