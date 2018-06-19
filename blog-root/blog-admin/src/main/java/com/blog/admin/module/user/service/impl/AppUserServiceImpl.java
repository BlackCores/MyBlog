package com.blog.admin.module.user.service.impl;

import com.blog.admin.common.base.BaseServiceImpl;
import com.blog.admin.module.user.dao.AppUserDao;
import com.blog.admin.module.user.entity.AppUser;
import com.blog.admin.module.user.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</   b> HeC<br/>
 * @createTime 2018/3/25 19:09
 * @Description:
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUser, Long> implements AppUserService {

    private AppUserDao appUserDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public void setDao(AppUserDao appUserDao) {
        super.setDao(appUserDao);
        this.appUserDao = appUserDao;
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserDao.findByUsername(username);
    }

    @Override
    public boolean isExistByUsername(String username) {
        Query query = entityManager.createQuery("SELECT COUNT(1) FROM AppUser where username=:username");
        query.setParameter("username",username);
        Integer count = Integer.valueOf(query.setMaxResults(1).getSingleResult().toString());
        return count==0?false:true;
    }

}
