package com.blog.admin.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
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
public class PwdUtil {

    private PwdUtil() {
    }

    //迭代次数
    public final static Integer ITERATION_COUNT = 17;

    //hex
    public final static Boolean HEX_ENCODED = false;

    //加密方式
    public final static String ALGORITHM_NAME = "MD5";

    private static final String FIX_STR="myblog20170415";

    private static final SimpleDateFormat sp = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 根据盐值加密
     * @param pwd   密码铭文
     * @param salt  盐值
     * @return
     */
    private static String encrypt(String pwd,String salt){
        Md5Hash md5Hash = new Md5Hash(pwd, salt, ITERATION_COUNT);
        if(HEX_ENCODED){
            return md5Hash.toHex();
        }else{
            return md5Hash.toBase64();
        }
    }

    /**
     * 密码盐值生成
     * @return
     */
    public synchronized static Map<String,String> encryptPwd(String pwd){
        try{
            Map<String,String> map = new LinkedHashMap<>(2);
            String format = sp.format(new Date());
            String md5Str = md5(FIX_STR+format);
            String salt = RandomStringUtils.random(16, true, true)+md5Str.substring(0,6);
            map.put("salt",salt);
            Md5Hash md5Hash = new Md5Hash(pwd, salt, ITERATION_COUNT);
            if(HEX_ENCODED){
                map.put("password",md5Hash.toHex());
            }else{
                map.put("password",md5Hash.toBase64());
            }
            return map;
        }catch(Exception e){
            e.printStackTrace();
        }
        throw new RuntimeException("盐值加密失败");
    }


    /**
     * md5加密
     * @param str
     * @return
     * @throws Exception
     */
    public static String md5(String str) throws Exception {
        MessageDigest md5=MessageDigest.getInstance(ALGORITHM_NAME);
        BASE64Encoder base64en = new BASE64Encoder();
        String newstr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    public static void main(String[] args) {
        Map<String, String> stringStringMap = encryptPwd("123");
        System.out.println(stringStringMap);

        String encrypt = encrypt("123", stringStringMap.get("salt"));
        System.out.println(encrypt);


//        String random = RandomStringUtils.random(16, true, true);
//        System.out.println(random);
    }

}
