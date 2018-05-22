package com.strive.MyBlog.core.shiro;

import com.strive.MyBlog.core.RedisService;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/30 23:51
 * @Description:
 */
@Configuration
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroConfig {

    @Autowired
    private ShiroProperties properties;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置过滤链
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        Iterator<Map.Entry<String, String>> iterator = properties.getFilterUrl().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String> map = (Map.Entry<String,String>)iterator.next();
            filterChainDefinitionMap.put(map.getKey(),map.getValue());
        }
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.html"页面
        shiroFilterFactoryBean.setLoginUrl(properties.getLoginUrl());
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl(properties.getSuccessUrl());
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl(properties.getUnauthorizedUrl());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 自定义Realm创建
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm(CredentialsMatcher credentialsMatcher){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(credentialsMatcher);
        return myShiroRealm;
    }

    /**
     * 交由SecurityManage管理
     * @return
     */
    @Bean
    @DependsOn("credentialsMatcher")
    public SecurityManager securityManager(CredentialsMatcher credentialsMatcher){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm(credentialsMatcher));
        return securityManager;
    }

    /**
     * 功能增强
     * @param redisService
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(RedisService redisService) {
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(redisService);
        //加密方式
        credentialsMatcher.setHashAlgorithmName(properties.getAlgorithmName());
        //加密迭代次数
        credentialsMatcher.setHashIterations(properties.getIteration());
        //true加密用的hex编码，false用的base64编码
        credentialsMatcher.setStoredCredentialsHexEncoded(properties.getHexEncoded());
        //重新尝试的次数（自己定义的）
        credentialsMatcher.setRetryMax(properties.getRetryMax());
        return credentialsMatcher;
    }

    /**
     * (基于redis的)用户授权信息Cache
     */
    @Bean(name = "shiroCacheManager")
    public CacheManager redisCacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManage(redisTemplate);
    }

}
