package com.keller.common.config;

/**
 * 公用常量，项目中要到的一些常量
 * @author yangkaile
 * @date 2019-09-16 14:41:23
 */
public class Constants {

    /**
     * 默认的用户类型，即普通用户类型
     */
    public static final int DEFAULT_USER_TYPE = 0;

    /**
     * 管理员的用户类型
     */
    public static final int ADMIN_USER_TYPE = 100;

    public static boolean notUserType(Integer userType){
        return !isUserType(userType);
    }

    public static boolean isUserType(Integer userType){
        if(userType == null){
            return false;
        }
        return userType == DEFAULT_USER_TYPE || userType == ADMIN_USER_TYPE;
    }


    /**
     * JWT 有效时间 12 小时
     */
    public static final long JWT_EXP_TIME = 12 * 60 * 60 * 1000;


    /**
     * 应用名
     */
    public static final String appName = "KellerNotes";


    /**
     * 不需要身份验证就可以访问的接口
     */
    public static final String baseMapping = "/base";

    /**
     * 需要管理员身份才能访问的接口
     */
    public static final String adminMapping = "/admin";


    public static String nginxPath = "/Users/yangkaile/Projects/nginx/keller/";

    public static String nginxUrl = "http://localhost:8081/keller/";

    /**
     * 图片保存路径
     */
    public static String imgPath = "image/";

    /**
     * 头像保存路径
     */
    public static String portraitPath = "portrait/";

    /**
     * 原图保存路径
     */
    public static String originImgPath = "img/";

    /**
     * 缩略图保存路径
     */
    public static String thumbnailPath = "thum/";


    /**
     * 头像的前缀名
     */
    public static final String PORTRAIT_PREFIX = "kellerNotes";


    /**
     *  头像格式
     */
    public static final String PORTRAIT_TYPE = "png";
    /**
     * 缩略图前缀名
     */
    public static final String THUM_PREFIX = "thum";

    /**
     * 缩略图最大宽度
     */
    public static final int THUM_MAX_WIDTH = 120;

    /**
     * 缩略图最大高度
     */
    public static final int THUM_MAX_HEIGHT = 120;


    /**
     * 用户id参数固定名称，不接收外部传入的用户id
     */
    public static final String USER_ID_KEY = "kellerUserId";

    /**
     * 管理员id参数固定名称，不接收外部传入的管理员id
     */
    public static final String ADMIN_ID_KEY = "kellerAdminId";

    public static final String packageName = "com.keller";
}
