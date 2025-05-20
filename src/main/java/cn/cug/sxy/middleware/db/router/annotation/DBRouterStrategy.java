package cn.cug.sxy.middleware.db.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version 1.0
 * @Date 2025/5/20 11:39
 * @Description 路由策略
 * @Author jerryhotton
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DBRouterStrategy {

    boolean splitTable() default false;

}
