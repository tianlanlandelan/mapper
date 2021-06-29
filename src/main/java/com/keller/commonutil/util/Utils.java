package com.keller.commonutil.util;

import java.util.List;

public class Utils {

    public static boolean hasBit(long number, int index) {
        return BitUtils.hasBit(number, index);
    }

    public static long setBit(long number, int index, int status) {
        return BitUtils.setBit(number, index, status);
    }

    public static int getChineseStrokeCount(String str) {
        return ChineseUtils.getChineseStrokeCount(str);
    }

    public static boolean write2File(String filePath, boolean isAppend, List<String> list) {
        return FileUtils.write2File(filePath, isAppend, list);
    }

    public static List<String> readFromFile(String filePath) {
        return FileUtils.readFromFile(filePath);
    }

    public static String getMd5String(String str) {
        return Md5Utils.getMd5String(str);
    }

    public static String getUUID(){
        return UuidUtils.getUUID();
    }
    public static String getRandomNumber(int length) {
        return RandomUtils.getRandomNumber(length);
    }

    public static String getRandomStr(int length){
        return RandomUtils.getRandomStr(length);
    }

    public static boolean isEmpty(Object... parameters){
        return ObjectUtils.isEmpty(parameters);
    }

    public static boolean noEmpty(Object... parameters){
        return ObjectUtils.noEmpty(parameters);
    }

    public static boolean hasValue(Object... parameters){
        return ObjectUtils.hasValue(parameters);
    }

    public static boolean noValue(Object... parameters){
        return ObjectUtils.noValue(parameters);
    }

}
