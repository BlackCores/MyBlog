package com.strive.MyBlog.manage.user.controller;

import cn.hutool.core.lang.Validator;
import com.strive.MyBlog.controller.BaseController;
import com.strive.MyBlog.manage.user.service.AppUserService;
import com.strive.MyBlog.pojo.po.AppUser;
import com.strive.MyBlog.pojo.result.JsonResult;
import com.strive.MyBlog.pojo.result.PageResult;
import com.strive.MyBlog.util.PwdUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/2 23:08
 * @Description:
 */
@RestController
@RequestMapping("/user/appuser")
@Log4j2
public class AppUserController extends BaseController<AppUser,Long>{

    private AppUserService appUserService;

    @Autowired
    private PwdUtil pwdUtil;

    @Autowired
    public void setService(AppUserService appUserService) {
        super.setService(appUserService);
        this.appUserService = appUserService;
    }

    @RequestMapping("/list")
    public PageResult list(HttpServletRequest request){
        return super.findPage(request);
    }

    @Override
    @RequestMapping("/get/{id}")
    public JsonResult get(@PathVariable Long id){
        return super.get(id);
    }

    @RequestMapping(value = "add",method = RequestMethod.POST)
    public JsonResult add(AppUser appUser){
        if(!Validator.isGeneral(appUser.getUsername(),5,16)){
            return new JsonResult(false,"非法的用户名");
        }
        if(!com.strive.MyBlog.util.Validator.isLength(appUser.getPassword(),16,8)){
            return new JsonResult(false,"密码不符合规则");
        }
        String isExist = appUserService.isExistByUsername(appUser.getUsername());
        if(!isExist.equals("0")){
            return new JsonResult(false,"该用户名已被使用");
        }
        try {
            Map<String, String> map = pwdUtil.encryptPwd(appUser.getPassword());
            appUser.setPassword(map.get("password"));
            appUser.setSalt(map.get("salt"));
            return super.addObj(appUser);
        }catch (Exception e){
            log.error(this.getClass(),e);
            return new JsonResult(false,e.getClass().getName());
        }

    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public JsonResult update(Long id,String password){
        try {
            if(!com.strive.MyBlog.util.Validator.isLength(password,16,8)){
                return new JsonResult(false,"密码不符合规则");
            }
            AppUser appUser = super.see(id);
            Map<String, String> map = pwdUtil.encryptPwd(password);
            appUser.setPassword(map.get("password"));
            appUser.setSalt(map.get("salt"));
            return super.update(appUser);
        }catch (Exception e){
            log.error(this.getClass(),e);
            return new JsonResult(false,e.getClass().getName());
        }

    }

    /**
     * 激活用户
     * @param id
     * @return
     */
    @RequestMapping(value = "active",method = RequestMethod.POST)
    public JsonResult active(Long id){
        try{
            AppUser appUser = super.see(id);
            appUser.setStatus(1);
            return super.update(appUser);
        }catch(Exception e){
            log.error(this.getClass(),e);
            return new JsonResult(false,e.getClass().getName());
        }
    }


}
