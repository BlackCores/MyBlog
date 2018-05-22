package com.strive.MyBlog.manage.user.dao;

import com.strive.MyBlog.controller.BaseDao;
import com.strive.MyBlog.pojo.po.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public abstract interface AppUserDao extends BaseDao<AppUser,Long> {
    /**
     * 根据username查询用户
     * @param username
     * @return
     */
    AppUser findByUsername(String username);

    /**
     * 根据username分页查询
     * @param pageable
     * @return
     */
    Page<AppUser> findByUsernameLike(String username,Pageable pageable);

}
