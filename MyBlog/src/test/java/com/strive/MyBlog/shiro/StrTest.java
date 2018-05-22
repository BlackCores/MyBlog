package com.strive.MyBlog.shiro;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/3 0:12
 * @Description:
 */
public class StrTest {

    @Test
    public void test1(){
        String s = RandomStringUtils.random(10,true,true);
        System.out.println(s);
        String s1 = RandomStringUtils.randomGraph(100);
        System.out.println(s1);
    }

}
