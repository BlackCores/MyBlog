package com.blog.admin.core.shiro;

import com.blog.admin.common.constants.ConstantsUser;
import com.blog.admin.module.user.entity.AppFn;
import com.blog.admin.module.user.entity.AppRole;
import com.blog.admin.module.user.entity.AppUser;
import com.blog.admin.module.user.service.AppUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/30 23:52
 * @Description:
 */
public class MyShiroRealm extends AuthorizingRealm{

    @Autowired
    private AppUserService appUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AppUser appUser  = (AppUser)principals.getPrimaryPrincipal();
        for(AppRole role:appUser.getRoleList()){
            //添加角色
            authorizationInfo.addRole(role.getRoleKey());
            for(AppFn p:role.getAppFnList()){
                //添加拥有的权限
                authorizationInfo.addStringPermission(p.getFnKey());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        AppUser user = appUserService.findByUsername(token.getUsername());
        //用户是否存在
        if(user==null){
            throw new UnknownAccountException();
        }
        //是否激活
        if(user!=null&&user.getStatus().equals(ConstantsUser.ZERO.getCode())){
            throw new  DisabledAccountException();
        }
        //是否锁定
        if(user!=null&&user.getStatus().equals(ConstantsUser.THREE.getCode())){
            throw new  LockedAccountException();
        }
        //若存在，将此用户存放到登录认证info中，无需自己做密码对比Shiro会为我们进行密码对比校验
        if(user!=null&&user.getStatus().equals(ConstantsUser.ONE.getCode())) {
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    user.getUsername(), //用户名
                    user.getPassword(), //密码
                    credentialsSalt,//salt=username+salt
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        return null;
    }

}
