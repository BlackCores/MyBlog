package com.strive.MyBlog.core.scheduling;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/25 10:04
 * @Description:
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {

    /*@Scheduled(fixedRate = 10000)
    public void test1() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("定时任务1================");
    }

    @Scheduled(fixedRate = 10000)
    public void test2() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("定时任务2================");
    }*/
}
