package com.keller.commonutil.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yangkaile
 * @date 2019-04-16 11:00:56
 * 控制台日志输出工具类
 */
@Slf4j
public class Console {

    public static void print(String title,Object ... objects){
        System.out.println("=======" + title + "========");
        for(Object object : objects){
            System.out.print(object + "\t");
        }
        System.out.println();
    }

    public static void println(String title,Object ... objects){
        System.out.println("=======" + title + "========");
        for(Object object : objects){
            System.out.println(object);
        }
        System.out.println();
    }

    public static void info(String title,Object ... objects){
        if(log.isInfoEnabled()){
            StringBuilder builder = new StringBuilder();
            builder.append("======").append(title).append("======");
            for (Object object:objects){
                builder.append("\n").append(object);
            }
            builder.append("\n");
            log.info(builder.toString());
        }
    }

    public static void error(String title,Object ... objects){
        if(log.isErrorEnabled()){
            StringBuilder builder = new StringBuilder();
            builder.append("======").append(title).append("======");
            for (Object object:objects){
                builder.append("\n").append(object);
            }
            builder.append("\n");
            log.error(builder.toString());
        }
    }

}
