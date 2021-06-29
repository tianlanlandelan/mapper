package com.code4j.mybatisutil.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类，生成随机数、格式校验、判断空字符串等
 * @author yangkaile
 * @date 2018-08-28 16:15:10
 */
public class StringUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str == null || str.isEmpty();
    }

    /**
     * 将字符串的首字母转大写
     * @param str 需要转换的字符串
     * @return
     */
    public static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
