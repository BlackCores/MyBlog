package com.strive.MyBlog.core.shiro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    /**
     * 权限过滤链
     */
    private Map<String,String> filterUrl;

    /**
     * 登录url
     */
    private String loginUrl;

    /**
     * 成功url
     */
    private String successUrl;

    /**
     * 未授权url
     */
    private String unauthorizedUrl;

    /**
     * 加密方式
     */
    private String algorithmName;

    /**
     * 迭代次数
     */
    private Integer iteration;

    /**
     * true为hex编码 false为base64编码
     */
    private Boolean hexEncoded;

    /**
     * 登录密码错误限制次数
     */
    private Integer retryMax;



    public Map<String, String> getFilterUrl() {
        Map<String,String> map = new HashMap<>();
        map.put("/static/**","anon");
        map.put("/login","anon");
        map.put("/logout","logout");
        map.put("/**","authc");
        this.filterUrl = map;
        return filterUrl;
    }
}
