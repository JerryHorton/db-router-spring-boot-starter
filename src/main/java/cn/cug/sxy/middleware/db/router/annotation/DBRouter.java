package cn.cug.sxy.middleware.db.router.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0
 * @Date 2025/5/20 09:18
 * @Description 路由注解
 * @Author jerryhotton
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBRouter {

    String key() default "";

}
