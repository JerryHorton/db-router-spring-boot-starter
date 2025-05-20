package cn.cug.sxy.middleware.db.router;

import cn.cug.sxy.middleware.db.router.annotation.DBRouter;
import cn.cug.sxy.middleware.db.router.strategy.IDBRouterStrategy;
import cn.cug.sxy.middleware.db.router.util.ReflectionUtils;
import cn.cug.sxy.middleware.db.router.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @version 1.0
 * @Date 2025/5/20 15:33
 * @Description 数据库路由切面
 * @Author jerryhotton
 */

@Aspect
public class DBRouterJoinPoint {

    private DBRouterConfiguration dbRouterConfiguration;

    private IDBRouterStrategy dbRouterStrategy;

    public DBRouterJoinPoint(DBRouterConfiguration dbRouterConfiguration, IDBRouterStrategy dbRouterStrategy) {
        this.dbRouterConfiguration = dbRouterConfiguration;
        this.dbRouterStrategy = dbRouterStrategy;
    }

    @Pointcut("@annotation(cn.cug.sxy.middleware.db.router.annotation.DBRouter)")
    public void aopPoint() {
    }

    @Around("aopPoint() && @annotation(dbRouter)")
    public Object doRouter(ProceedingJoinPoint joinPoint, DBRouter dbRouter) throws Throwable {
        // 获取注解中的路由键
        String routerKey = dbRouter.key();
        if (StringUtils.isBlank(routerKey) || StringUtils.isBlank(dbRouterConfiguration.getRouterKey())) {
            throw new RuntimeException("dbRouter key is empty");
        } else {
            // 若注解未设置则使用默认路由键
            routerKey = StringUtils.isNotBlank(routerKey) ? routerKey : dbRouterConfiguration.getRouterKey();
            // 获取路由键对应的值
            String routerValue = ReflectionUtils.getFirstNonBlankFieldValue(joinPoint.getArgs(), routerKey);
            // 执行路由逻辑
            this.dbRouterStrategy.doRouter(routerValue);
            Object result;
            try {
                result = joinPoint.proceed();
            } finally {
                this.dbRouterStrategy.clear();
            }
            return result;
        }
    }

}
