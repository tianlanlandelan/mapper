package com.keller.commonutil.mybatis.provider;

import com.keller.commonutil.util.Console;
import com.keller.commonutil.mybatis.SqlFieldReader;
import com.keller.commonutil.util.ObjectUtils;
import com.keller.commonutil.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础的Delete语句提供者
 *
 * @author yangkaile
 * @date 2021-02-02 07:31:08
 */
public class BaseInsertProvider {
    /**
     * 缓存insert语句
     */
    public static Map<String, String> insertMap = new ConcurrentHashMap<>(16);

    /**
     * 基础的添加语句
     * 读取对象的所有字段属性，生成insert语句
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> String insert(T entity) {
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = insertMap.get(className);
        if (ObjectUtils.isEmpty(sql)) {
            List<String> fieldList = SqlFieldReader.getFields(entity);

            StringBuilder builder = new StringBuilder();
            builder.append("INSERT INTO ")
                    .append(SqlFieldReader.getTableName(entity)).append(" ")
                    .append("(")
                    .append(StringUtils.humpToLine(SqlFieldReader.getFieldStr(entity)))
                    .append(") ").append("VALUES(");

            StringBuilder valuesStr = new StringBuilder();
            for (String str : fieldList) {
                valuesStr.append("#{").append(str).append("}").append(",");
            }
            builder.append(valuesStr.substring(0, valuesStr.length() - 1))
                    .append(") ");
            sql = builder.toString();
            insertMap.put(className, sql);
        }
        Console.info("insert", SqlFieldReader.getTableName(entity), sql, entity);
        return sql;
    }

    //TODO 批量添加
    public static <T> String insertList(List<T> list) {
        T entity = list.get(0);
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = null;
        List<String> fieldList = SqlFieldReader.getFields(entity);

        StringBuilder builder = new StringBuilder();
        builder.append("<script> ")
                .append("INSERT INTO ")
                .append(SqlFieldReader.getTableName(entity)).append(" ")
                .append("(")
                .append(StringUtils.humpToLine(SqlFieldReader.getFieldStr(entity)))
                .append(") ")
                .append("VALUES ")
                .append("<foreach collection='list' item='item' index='index' separator=','>");

        StringBuilder valuesStr = new StringBuilder();
        valuesStr.append("(");
        for (String str : fieldList) {
            valuesStr.append("#{item.").append(str).append("}").append(",");
        }
        builder.append(valuesStr.substring(0, valuesStr.length() - 1))
                .append(") ")
                .append("</foreach>")
                .append("</script>");
        sql = builder.toString();
        Console.info("insertList", SqlFieldReader.getTableName(entity), sql, entity);
        return sql;
    }


    public static void main(String[] args) {

    }
}
