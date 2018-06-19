package com.blog.admin.common.aspect;

import com.blog.admin.common.annotation.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:HelloHeSir@gmail.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/6/12 20:42
 * @Description:aop日志记录
 */
@Aspect
@Component
public class SysLogAspect {

    @Pointcut("@annotation(com.blog.admin.common.annotation.SysLog)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long begin = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        long time = System.currentTimeMillis()-begin;
        //保存日志
        saveSysLog(point,time);
        return result;
    }

    /**
     * 保存日志
     * @param point
     * @param time
     */
    private void saveSysLog(ProceedingJoinPoint point, long time) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        //得到方法对象
        Method method = signature.getMethod();
        //获得方法上注解对象
        SysLog sysLog = method.getAnnotation(SysLog.class);
        //获得注解对象的值
        String value = sysLog.value();
        //获得方法名
        String methodName = signature.getName();
        //获得类名
        String className = point.getTarget().getClass().getName();
        //获得请求参数
        Object[] args = point.getArgs();

        System.out.println(value);

    }

}