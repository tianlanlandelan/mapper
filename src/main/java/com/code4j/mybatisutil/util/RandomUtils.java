package com.code4j.mybatisutil.util;

import java.util.Random;

/**
 * Random工具类
 *
 * @author yangkaile
 * @date 2021-01-24 13:12:51
 */
public class RandomUtils {

    /**
     * 纯数字集合
     */
    private static final String numbersChar = "0123456789";

    /**
     * 大小写字母、数字集合
     */
    private static final String allChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


    /**
     * 生成指定长度的数字字符串
     * @param length
     *  字符串长度
     * @return
     */
    public static String getRandomNumber(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(numbersChar.charAt(random.nextInt(numbersChar.length())));
        }
        return sb.toString();
    }

    /**
     * 生成指定长度的字符串（含大小写字母及数字）
     * @param length
     * @return
     */
    public static String getRandomStr(int length){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        Console.println("生成6位纯数字的随机字符串",getRandomNumber(6));
        Console.println("生成8位随机字符串",getRandomStr(8));
    }
}
