package com.strive.MyBlog.manage.role.service.impl;

import com.strive.MyBlog.manage.role.dao.AppRoleDao;
import com.strive.MyBlog.manage.role.service.AppRoleService;
import com.strive.MyBlog.pojo.po.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/25 19:09
 * @Description:
 */
@Service
public class AppRoleServiceImpl implements AppRoleService {

    @Autowired
    private AppRoleDao appRoleDao;

    @Override
    public List<AppRole> findAll() {
        return appRoleDao.findAll();
    }
}
