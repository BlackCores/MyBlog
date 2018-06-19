package com.blog.admin.core.shiro;

import com.blog.admin.common.core.redis.RedisService;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/1 23:02
 * @Description:自定义校验过程以及散列加密
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private RedisService<String,Integer> redisService;

    public static final String LOGIN_SECOND = "passwordRetryCache:second:";

    public RetryLimitHashedCredentialsMatcher(RedisService redisService){
        this.redisService = redisService;
    }

    /**
     * 自定义密码错误上限
     */
    @Getter
    @Setter
    private Integer retryMax;

    /**
     * 对该方法装饰
     * @param token
     * @param info
     * @return
     * @throws ExcessiveAttemptsException
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws ExcessiveAttemptsException {
        String username = LOGIN_SECOND+token.getPrincipal();
        Integer retryCount = redisService.get(username);
        if(retryCount == null) {
            retryCount = new Integer(0);
            redisService.set(username, retryCount,60L);
        }

        if(retryCount >= retryMax) {
            throw new ExcessiveAttemptsException("您已连续错误达" + retryMax + "次！请N分钟后再试");
        }

        redisService.incr(username);

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            redisService.remove(username);
        }else {
            throw new IncorrectCredentialsException("密码错误，已错误" + retryCount + "次，最多错误" + retryMax + "次");
        }
        return true;
    }

}
