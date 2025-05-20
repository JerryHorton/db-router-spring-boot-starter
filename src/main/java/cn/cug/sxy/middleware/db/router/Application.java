package cn.cug.sxy.middleware.db.router;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @version 1.0
 * @Date 2025/5/20 17:11
 * @Description
 * @Author jerryhotton
 */

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
@Configurable
public class Application {

}
