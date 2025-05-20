package cn.cug.sxy.middleware.db.router.test.dao;

import cn.cug.sxy.middleware.db.router.annotation.DBRouter;
import cn.cug.sxy.middleware.db.router.annotation.DBRouterStrategy;
import cn.cug.sxy.middleware.db.router.test.dao.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version 1.0
 * @Date 2025/5/20 17:32
 * @Description
 * @Author jerryhotton
 */

@Mapper
@DBRouterStrategy(splitTable = false)
public interface IUserDao {

    @DBRouter(key = "id")
    User getUserById(Integer id);

}
