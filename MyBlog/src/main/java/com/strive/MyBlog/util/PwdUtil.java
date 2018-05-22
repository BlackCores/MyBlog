package com.strive.MyBlog.util;

import com.strive.MyBlog.core.shiro.ShiroProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/15 23:15
 * @Description:密码加密工具
 */
@EnableConfigurationProperties(ShiroProperties.class)
@Component
public class PwdUtil {

    @Autowired
    private ShiroProperties shiroProperties;

    private static final String FIX_STR="myblog20170415";

    private static final SimpleDateFormat sp = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 密码生成
     * @return
     */
    public synchronized Map<String,String> encryptPwd(String pwd) throws Exception {
        Map<String,String> map = new LinkedHashMap<>(2);
        String format = sp.format(new Date());
        String md5Str = md5(FIX_STR+format);
        String salt = RandomStringUtils.random(16, true, true)+md5Str.substring(0,6);
        map.put("salt",salt);
        Md5Hash md5Hash = new Md5Hash(pwd, salt, shiroProperties.getIteration());
        if(shiroProperties.getHexEncoded()){
            map.put("password",md5Hash.toHex());
        }else{
            map.put("password",md5Hash.toBase64());
        }
        return map;
    }


    /**
     * md5加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String md5(String str) throws Exception {
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        System.out.println(newstr);
        return newstr;
    }

    public static void main(String[] args) {
        try {
            md5("333");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
