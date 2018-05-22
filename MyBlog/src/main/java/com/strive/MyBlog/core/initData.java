package com.strive.MyBlog.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/16 1:06
 * @Description:配置初始化
 */
@Component
public class initData implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始1。。。。。。。。。。。。。。。。。。。。");
    }
}

/**
 * 加@component注解才能扫描到这个bean并使用
 * 实现InitializingBean接口会最先初始化该bean
 * 实现CommandLineRunner接口将会最后初始化该bean
 * 如果指定了@Order(value = 1)注解同样是最后进行初始化，只对CommandLineRunner接口有效
 */