package com.strive.MyBlog.testutil;

import com.strive.MyBlog.core.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/25 21:28
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestRedis {

    @Autowired
    private RedisService redis;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void set() {
        redisTemplate.opsForValue().set("test:set", "testValue1");
    }

    @Test
    public void test() throws Exception {
        redis.set("test:a", "abcaaaaaaaaaaaa", 30L);
        String abc = redis.get("test:a").toString();
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("222");
        list.add("333");
        list.add("444");
        redis.addList("test:list", list);
        System.out.println(abc);
    }

    @Test
    public void testObj() throws Exception {

    }
}
