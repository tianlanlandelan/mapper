package com.code4j.mybatisutil.mybatis;

import com.code4j.mybatisutil.mybatis.annotation.FieldAttribute;
import com.code4j.mybatisutil.mybatis.annotation.TableAttribute;
import com.code4j.mybatisutil.util.ObjectUtils;
import com.code4j.mybatisutil.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provider工具类
 * 提供获取读取表名、字段名等公用方法
 *
 * @author yangkaile
 * @date 2019-09-12 15:29:19
 */
public class SqlFieldReader {

    public static ConcurrentHashMap<String, String> tableNameMap = new ConcurrentHashMap<>(16);

    /**
     * 读取表名，要求类上有@TableAttribute注解
     *
     * @param entity 实体对象
     * @return tableName
     */
    public static <T> String getTableName(T entity) {
        Class cls = entity.getClass();
        String tableName = tableNameMap.get(cls.getName());
        if (ObjectUtils.noEmpty(tableName)) {
            return tableName;
        }
        TableAttribute table = entity.getClass().getAnnotation(TableAttribute.class);
        if (table == null) {
            throw new BaseException("需要解析表名，但未找到@TableAttribute注解");
        }
        tableNameMap.put(cls.getName(), table.name());
        return table.name();
    }

    /**
     * 生成查询条件，所有有值的字段都会作为查询条件
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return
     */
    private static <T> String getConditionSuffix(T entity) {
        StringBuilder builder = new StringBuilder();
        List<String> fields = getFields(entity);
        for (String field : fields) {
            if (SqlFieldReader.hasValue(entity, field)) {
                builder.append(" ")
                        .append(StringUtils.humpToLine(field))
                        .append(" =#{entity.")
                        .append(field)
                        .append("} ")
                        .append("AND");
            }
        }
        if (builder.length() > 0) {
            return " WHERE " + builder.substring(0, builder.lastIndexOf("AND"));
        }
        return "";
    }

    /**
     * @param query
     * @param <T>
     * @return
     */
    public static <T> String getCondition(BaseQuery<T> query) {
        T entity = query.getEntity();
        if (query == null) {
            return getConditionSuffix(entity);
        }
        String condition = query.getCondition();
        if (StringUtils.isEmpty(condition)) {
            return getConditionSuffix(entity);
        }
        if (condition.contains("WHERE")) {
            return condition;
        }
        return " WHERE " + condition;

    }

    /**
     * 获取排序条件
     *
     * @param entity
     * @param <T>
     * @return
     */
//    public static <T  > String getSortSuffix(T entity) {
//        String condition;
//        if (entity.getBaseAsc() == null) {
//            return "";
//        }
//        if (entity.getBaseAsc()) {
//            condition = "ASC";
//        } else {
//            condition = "DESC";
//        }
//        Class cls = entity.getClass();
//        Field[] fields = cls.getDeclaredFields();
//        StringBuilder builder = new StringBuilder();
//        builder.append(" ORDER BY ");
//        try {
//            for (Field field : fields) {
//                if (field.getAnnotation(SortAttribute.class) != null) {
//                    builder.append(field.getName()).append(" ")
//                            .append(condition).append(",");
//
//                }
//            }
//            int index = builder.lastIndexOf(",");
//            if (index < 0) {
//                return "";
//            }
//            return builder.substring(0, index);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    /**
     * 获取所有字段列表
     * 读取类中带@FieldAttribute注解的字段
     *
     * @return {id,name}
     */
    public static <T> List<String> getFields(T entity) {
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> fieldList = new ArrayList<>();
        //带@FieldAttribute注解的属性名
        for (Field field : fields) {
            FieldAttribute fieldAttribute = field.getAnnotation(FieldAttribute.class);
            if (fieldAttribute != null) {
                fieldList.add(field.getName());
            }
        }
        return fieldList;
    }

    /**
     * 将所有字段名以逗号拼接起来返回
     * 从属性前的@FieldAttribute注解解析要查询的字段名
     * 当所有属性都没有@FieldAttribute注解时，解析所有属性名作为字段名
     *
     * @param entity 实体对象
     * @return id, name
     */
    public static <T> String getFieldStr(T entity) {
        List<String> list = getFields(entity);
        if (list == null || list.isEmpty()) {
            return "";
        }
        return String.join(",", list);
    }

    /**
     * 判断一个对象的指定字段有没有值
     *
     * @param entity    实体对象
     * @param fieldName 对象的字段名
     * @param <T>       实体类型
     * @return 值存在且不为null:返回true; 否则:返回false
     */
    public static <T> boolean hasValue(T entity, String fieldName) {
        try {
            Class cls = entity.getClass();
            Method method = cls.getMethod("get" + StringUtils.captureName(fieldName));
            if (method.invoke(entity) == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
