package com.strive.MyBlog.util;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/19 22:41
 * @Description:校验
 */
public class Validator {


    /**
     * 判断字符串长度是否符合规则
     * @param str
     * @param max   最大长度
     * @param min   最小长度
     * @return
     */
    public static boolean isLength(String str,int max,int min){
        if(str!=null){
            if(str.length()<=max&&str.length()>=min){
                return true;
            }
        }
        return false;
    }

}
