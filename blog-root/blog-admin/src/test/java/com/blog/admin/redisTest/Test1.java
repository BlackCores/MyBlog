package com.blog.admin.redisTest;

import com.blog.admin.common.core.redis.RedisService;
import com.blog.admin.module.user.entity.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author <a href="mailto:HelloHeSir@gmail.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/6/11 16:44
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Test1 {
    @Autowired
    private RedisService redisService;

    @Test
    public void test(){
        AppUser appUser = new AppUser();
        appUser.setEmail("123213@qq.com");
        appUser.setUsername("sdfa43");
        redisService.hadd("ss","111",appUser);
    }
}