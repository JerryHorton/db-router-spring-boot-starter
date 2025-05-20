package cn.cug.sxy.middleware.db.router.test.dao.po;

import cn.cug.sxy.middleware.db.router.annotation.DBRouter;

/**
 * @version 1.0
 * @Date 2025/5/20 17:32
 * @Description
 * @Author jerryhotton
 */

public class User {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
