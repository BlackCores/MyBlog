package com.blog.admin.module.user.dao;

import com.blog.admin.common.base.BaseDao;
import com.blog.admin.module.user.entity.AppUser;


public abstract interface AppUserDao extends BaseDao<AppUser,Long> {
    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    AppUser findByUsername(String username);

}
