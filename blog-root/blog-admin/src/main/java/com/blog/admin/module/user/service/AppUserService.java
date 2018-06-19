package com.blog.admin.module.user.service;

import com.blog.admin.common.base.BaseService;
import com.blog.admin.module.user.entity.AppUser;

public interface AppUserService extends BaseService<AppUser,Long> {

    AppUser findByUsername(String username);

    boolean isExistByUsername(String username);
}
