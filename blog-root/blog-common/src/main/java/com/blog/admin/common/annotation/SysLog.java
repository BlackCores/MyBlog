package com.blog.admin.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志记录
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLog {

    String value() default "";

}
