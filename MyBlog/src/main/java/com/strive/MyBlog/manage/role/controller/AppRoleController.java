package com.strive.MyBlog.manage.role.controller;

import com.strive.MyBlog.manage.role.service.AppRoleService;
import com.strive.MyBlog.pojo.po.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/6 22:37
 * @Description:
 */
@Controller
@RequestMapping("/role")
public class AppRoleController {

    @Autowired
    private AppRoleService appRoleService;

    @RequestMapping("/roleList")
    public String roleList(Model model){
        List<AppRole> roleList = appRoleService.findAll();
        model.addAttribute("roleList",roleList);
        return "/admin/user/appRoleList";
    }
}
