package com.kyle.mycommon.mybatis.provider;


import com.kyle.mycommon.util.Console;
import com.kyle.mycommon.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yangkaile on 2019/7/12.
 */
public class BaseInsertProvider {
    /**
     * 缓存insert语句
     */
    public static Map<String,String> insertMap = new ConcurrentHashMap<>(16);

    public static Map<String,String> insertAndReturnKeyMap = new ConcurrentHashMap<>(16);

    /**
     * 基础的添加语句
     * 读取对象的所有字段属性，生成insert语句
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> String insert(T entity) {
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = insertMap.get(className);
        if(StringUtils.isEmpty(sql)){
            String fieldStr = ProviderUtil.getFieldStr(cls);

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO ")
                    .append(ProviderUtil.getTableName(cls)).append(" ")
                    .append("(").append(fieldStr).append(") ")
                    .append("VALUES(");

            StringBuilder valuesStr = new StringBuilder();
            String[] arrays = fieldStr.split(",");
            for(String str:arrays){
                valuesStr.append("#{").append(str).append("}").append(",");
            }
            builder.append(valuesStr.substring(0,valuesStr.length() - 1))
                    .append(") ");
            sql = builder.toString();
            insertMap.put(className,sql);
        }
        Console.info("insert",sql,entity);
        return sql;
    }

    public static <T> String insertAndReturnKey(T entity) {
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = insertAndReturnKeyMap.get(className);
        if(StringUtils.isEmpty(sql)){
            String fieldStr = ProviderUtil.getFieldStr(cls);
            String[] arrays = fieldStr.split(",");

            StringBuilder builder = new StringBuilder();

            StringBuilder valuesStr = new StringBuilder();

            builder.append("INSERT INTO ")
                    .append(ProviderUtil.getTableName(cls)).append(" ")
                    .append("(");
            for(String str:arrays){
                if("id".equals(str)){
                    continue;
                }
                valuesStr.append(str).append(",");
            }
            builder.append(valuesStr.substring(0,valuesStr.length() - 1));

            builder.append(") ").append("VALUES(");

            valuesStr = new StringBuilder();
            for(String str:arrays){
                if("id".equals(str)){
                    continue;
                }
                valuesStr.append("#{").append(str).append("}").append(",");
            }
            builder.append(valuesStr.substring(0,valuesStr.length() - 1))
                    .append(") ");
            sql = builder.toString();
            insertAndReturnKeyMap.put(className,sql);
        }
        Console.info("insertAndReturnKey",sql,entity);
        return sql;
    }


    public static void main(String[] args){

    }
}
