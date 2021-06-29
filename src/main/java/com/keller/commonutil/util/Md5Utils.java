package com.keller.commonutil.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 规定统一的MD5加密方法
 * @author yangkaile
 * @date 2020-04-20 11:11:42
 *
 */
public class Md5Utils {
    /**
     * 将普通字符串用md5加密，并转化为16进制字符串
     * @param str
     * @return
     */
    public static String getMd5String(String str) {
        try {
            // 参数代表的是算法名称
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] result = md5.digest(str.getBytes());
            return new BASE64Encoder().encode(result);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception{
        String str = "你好";
        String md5Str = getMd5String(str);
        Console.println("GetMd5",md5Str);
    }

}
