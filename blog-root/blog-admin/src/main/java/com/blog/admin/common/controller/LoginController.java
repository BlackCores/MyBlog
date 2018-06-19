package com.blog.admin.common.controller;

import com.blog.admin.common.annotation.SysLog;
import com.blog.admin.common.pojo.JsonResult;
import com.blog.admin.module.user.entity.AppUser;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/31 21:32
 * @Description:
 */
@RestController
@Log4j2
public class LoginController {

    /**
     * 登录校验
     *
     * @param user
     * @return
     */
    @SysLog("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(AppUser user, @RequestParam(defaultValue = "false") Boolean rememberMe) {
        JsonResult result = new JsonResult(true);

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        //是否记住用户
        if (rememberMe) {
            token.setRememberMe(true);
        }
        //获得当前subject
        Subject currentUser = SecurityUtils.getSubject();
        //当前用户
        String username = user.getUsername();
        try {
            //登录校验
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
            result.setMsg("账户不存在").setSuccess(false);
        } catch (IncorrectCredentialsException ice) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            result.setMsg("密码不正确").setSuccess(false);
        } catch (LockedAccountException lae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            result.setMsg("账户已锁定").setSuccess(false);
        } catch (DisabledAccountException lae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,账户未审核");
            result.setMsg("账户未激活").setSuccess(false);
        } catch (ExcessiveAttemptsException eae) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            result.setMsg("用户名或密码错误次数过多").setSuccess(false);
        } catch (AuthenticationException ae) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            log.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            result.setMsg("用户名或密码不正确").setSuccess(false);
        } catch (Exception e) {
            result.setMsg(e.getMessage()).setSuccess(false);
            log.error(this.getClass(), e);
        }
        //验证登录状态
        if (currentUser.isAuthenticated() && result.getSuccess()) {
            log.info("用户：{}  登录成功", username);
            return result.setSuccess(true);
        } else {
            token.clear();
            return result.setSuccess(false);
        }
    }

    /**
     * 登陆状态验证
     * @return
     */
    @SysLog("登录状态校验")
    @RequestMapping("login")
    public JsonResult login(){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()){
            return new JsonResult(true);
        }else{
            return new JsonResult(false);
        }
    }

    /**
     * 用户登出
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public JsonResult logout() {
        SecurityUtils.getSubject().logout();
        return new JsonResult(true);
    }

}
