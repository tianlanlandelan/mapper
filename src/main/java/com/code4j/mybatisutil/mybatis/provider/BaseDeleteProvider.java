package com.code4j.mybatisutil.mybatis.provider;

import com.code4j.mybatisutil.mybatis.SqlFieldReader;
import com.code4j.mybatisutil.util.Console;
import com.code4j.mybatisutil.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础的删除语句提供者
 * @author yangkaile
 * @date 2019-07-18 09:56:43
 */
public class BaseDeleteProvider {

    public static Map<String,String> deleteByIdMap = new ConcurrentHashMap<>(16);

    /**
     * 根据Id 删除数据，要求必须有id字段
     * @param entity
     * @param <T>
     * @return DELETE FROM router  WHERE id = #{id}
     */
    public static <T  > String deleteById(T entity){
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = deleteByIdMap.get(className);
        if(ObjectUtils.isEmpty(sql)){
            sql = getDeletePrefix(entity) + " WHERE id = #{id} ";
            deleteByIdMap.put(className,sql);
        }
        Console.info("deleteById", SqlFieldReader.getTableName(entity),sql,entity);
        return sql;
    }

    private static <T  > String getDeletePrefix(T entity){
        return "DELETE FROM " + SqlFieldReader.getTableName(entity) + " ";
    }

    public static void main(String[] args){

    }

}
