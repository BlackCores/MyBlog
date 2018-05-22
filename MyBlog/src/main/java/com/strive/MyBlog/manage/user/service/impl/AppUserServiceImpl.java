package com.strive.MyBlog.manage.user.service.impl;

import com.strive.MyBlog.controller.BaseServiceImpl;
import com.strive.MyBlog.manage.user.dao.AppUserDao;
import com.strive.MyBlog.manage.user.service.AppUserService;
import com.strive.MyBlog.pojo.po.AppUser;
import com.strive.MyBlog.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
    public List<AppUser> userList() {
        return super.dao.findAll();
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserDao.findByUsername(username);
    }

    @Override
    public Page<AppUser> findByUsernameLike(String username) {
        PageRequest pageRequest = new PageRequest(1, 10);
        if (StrUtil.isNotEmpty(username)) {
            username = "%" + username + "%";
        }
        return appUserDao.findByUsernameLike(username, pageRequest);
    }

    @Override
    public Page<AppUser> findAll(Pageable pageable) {
        return super.dao.findAll(pageable);
    }

    @Override
    public String isExistByUsername(String username) {
        Query query = entityManager.createQuery("SELECT COUNT(1) FROM AppUser where username=:username");
        query.setParameter("username",username);
        return query.setMaxResults(1).getSingleResult().toString();
    }

}
