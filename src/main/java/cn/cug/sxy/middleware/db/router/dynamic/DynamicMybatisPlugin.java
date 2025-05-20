package cn.cug.sxy.middleware.db.router.dynamic;

import cn.cug.sxy.middleware.db.router.DBContextHolder;
import cn.cug.sxy.middleware.db.router.annotation.DBRouterStrategy;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 1.0
 * @Date 2025/5/20 14:28
 * @Description 动态分表 SQL 改写
 * @Author jerryhotton
 */

@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
})
public class DynamicMybatisPlugin implements Interceptor {

    private static final Pattern pattern = Pattern.compile("(from|into|update)\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取目标对象
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 获取底层属性
        MetaObject metaObject = MetaObject.forObject(
                statementHandler,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory()
        );
        // 获取当前执行的 Mapper 方法对应的 MappedStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        String statementId = mappedStatement.getId();
        // 解析 Mapper 类名
        String className = statementId.substring(0, statementId.lastIndexOf("."));
        Class<?> clazz = Class.forName(className);
        // 判断 Mapper 是否使用了 DBRouterStrategy 注解
        DBRouterStrategy dbRouterStrategy = clazz.getAnnotation(DBRouterStrategy.class);
        // 如果启用了分表
        if (null != dbRouterStrategy && dbRouterStrategy.splitTable()) {
            // 获取原始 SQL
            BoundSql boundSql = statementHandler.getBoundSql();
            String originalSql = boundSql.getSql();
            Matcher matcher = pattern.matcher(originalSql);
            // 查找第一个匹配的表名
            if (matcher.find()) {
                String keyWord = matcher.group(1);
                String originalTableName = matcher.group(2);
                // 构造分表后缀
                String suffixTableName = originalTableName + "_" + DBContextHolder.getTBKey();
                // 构造替换后的 SQL
                String replacedSql = matcher.replaceFirst(keyWord + " " + suffixTableName);
                // 将替换后的 SQL 设置回 BoundSql
                Field sqlField = boundSql.getClass().getDeclaredField("sql");
                sqlField.setAccessible(true);
                sqlField.set(boundSql, replacedSql);
                sqlField.setAccessible(false);
            }
        }
        return invocation.proceed();
    }

}
