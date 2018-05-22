package com.strive.MyBlog.manage.user.service;

import com.strive.MyBlog.controller.BaseService;
import com.strive.MyBlog.pojo.po.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.util.List;

public interface AppUserService extends BaseService<AppUser,Long>{

    List<AppUser> userList();

    AppUser findByUsername(String username);

    Page<AppUser> findByUsernameLike(String username);

    Page<AppUser> findAll(Pageable pageable);

    String isExistByUsername(String username);
}
