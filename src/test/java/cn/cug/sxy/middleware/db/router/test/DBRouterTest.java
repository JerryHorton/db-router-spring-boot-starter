package cn.cug.sxy.middleware.db.router.test;

import cn.cug.sxy.middleware.db.router.strategy.IDBRouterStrategy;
import cn.cug.sxy.middleware.db.router.test.dao.IUserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @Date 2025/5/20 17:05
 * @Description
 * @Author jerryhotton
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBRouterTest {

    private Logger logger = LoggerFactory.getLogger(DBRouterTest.class);

    @Resource
    private IUserDao userDao;

    @Resource
    private IDBRouterStrategy dbRouter;

    @Test
    public void test() {
        userDao.getUserById(1);
    }

}
