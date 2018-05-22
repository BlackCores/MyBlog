package com.strive.MyBlog.shiro;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

import java.security.Key;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</b> HeC<br/>
 * @createTime 2018/3/31 22:59
 * @Description:密码加盐
 */
//@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
public class SaltPassword {

    /**
     * 1、首先创建一个DefaultHashService，默认使用SHA-512算法；
     * 2、可以通过hashAlgorithmName属性修改算法；
     * 3、可以通过privateSalt设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
     * 4、可以通过generatePublicSalt属性在用户没有传入公盐的情况下是否生成公盐；
     * 5、可以设置randomNumberGenerator用于生成公盐；
     * 6、可以设置hashIterations属性来修改默认加密迭代次数；
     * 7、需要构建一个HashRequest，传入算法、数据、公盐、迭代次数。
     */
    @Test
    public void test1() {
        DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(3); //生成Hash值的迭代次数

        long t1 = System.currentTimeMillis();

        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("123"))
                .setSalt(ByteSource.Util.bytes("123")).setIterations(300).build();
        String hex = hashService.computeHash(request).toHex();

        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        System.out.println(hex);
    }

    /**
     * SecureRandomNumberGenerator用于生成一个随机数：
     */
    @Test
    public void test2() {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        System.out.println(hex);
    }

    /**
     * aes加解密算法——shiro实现
     */
    @Test
    public void aesTest() {
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置key长度
        //生成随机key
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        System.out.println(encrptText);
        //解密
        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
        System.out.println(text2);
    }

    /**
     * shiro提供的盐值加密
     */
    @Test
    public void test3() {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = "123456";//密码原值
        Object salt = "www";//盐值
        int hashIterations = 1024;//加密1024次
        String result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations).toBase64();
        System.out.println("" + result);
    }

    /**
     * shiro提供的盐值加密
     */
    @Test
    public void test4() {
        String password = new Md5Hash("123456", "www", 1024).toBase64();
        System.out.println(password);
    }

}
