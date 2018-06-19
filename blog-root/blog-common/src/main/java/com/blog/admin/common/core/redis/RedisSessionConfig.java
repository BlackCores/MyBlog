package com.blog.admin.common.core.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author <a href="mailto:HelloHeSir@gmail.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/6/12 18:33
 * @Description:基于redis的session共享机制
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1200)
public class RedisSessionConfig {
}