package com.kyle.mycommon.mybatis.provider;



import com.kyle.mycommon.mybatis.BaseEntity;
import com.kyle.mycommon.mybatis.BaseException;
import com.kyle.mycommon.mybatis.annotation.*;
import com.kyle.mycommon.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Provider工具类
 * 提供获取读取表名、字段名等公用方法
 * Created by yangkaile on 2019/7/12.
 */
public class ProviderUtil {
    /**
     * 读取表名，要求类上有@TableAttribute注解
     * @param cls 实体类型
     * @return tableName
     */
    public static String getTableName(Class cls){
        TableAttribute table = (TableAttribute) cls.getAnnotation(TableAttribute.class);
        if(table == null){
            return null;
        }
        return table.value();
    }
    /**
     * 将所有字段名以逗号拼接起来返回
     * 从属性前的@FieldAttribute注解解析要查询的字段名
     * 当所有属性都没有@FieldAttribute注解时，解析所有属性名作为字段名
     * @param cls 实体类型
     * @return id,name
     */
    public static String getFieldStr(Class cls){
        Field[] fields = cls.getDeclaredFields();
        //带@FieldAttribute注解的属性名
        StringBuilder builder = new StringBuilder();
        //所有属性名
        StringBuilder allFields = new StringBuilder();
        for(Field field:fields){
            allFields.append(field.getName()).append(",");
            if(field.getAnnotation(FieldAttribute.class) != null){
                builder.append(field.getName()).append(",");
            }
        }
        if(builder.length() > 0){
            return builder.substring(0,builder.length() - 1);
        }else if(allFields.length() > 0){
            return allFields.substring(0,allFields.length() - 1);
        }else {
            return  null;
        }
    }

    /**
     * 根据索引字段生成查询条件
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return WHERE name = #{name} OR controllerName = #{controllerName}
     */
    public static <T extends BaseEntity> String getConditionSuffix(T entity){
        String condition;
        if(entity.getBaseKyleUseAnd() == null){
            return "";
        }
        if(entity.getBaseKyleUseAnd()){
            condition = "AND";
        }else {
            condition = "OR";
        }
        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE ");
        try {
            for(Field field:fields){
                if(field.getAnnotation(IndexAttribute.class) != null){
                    if(ProviderUtil.hasValue(entity,field.getName())){
                        builder.append(field.getName())
                                .append(" = #{").append(field.getName()).append("} ")
                                .append(condition).append(" ");
                    }

                }
            }
            int index = builder.lastIndexOf(condition);
            if(index < 0){
                return null;
            }
            return builder.substring(0,index);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取主键的查询条件
     * 传入的对象必须满足以下条件：
     * 1. 至少有一个带有@keyAttribute注解的字段
     * 2. 带有@KeyAttribute注解的字段必须有值
     * 这是为了避免产生因为没有设置@KeyAttribute注解而造成全部数据修改或删除的问题
     * @param entity
     * @param <T>
     * @return WHERE userId = #{userId} AND roleId = #{roleId}
     */
    public static <T> String getConditionByKeySuffix(T entity) throws BaseException{
        String condition = "AND";
        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" WHERE ");
        try {
            for(Field field:fields){
                if(field.getAnnotation(KeyAttribute.class) != null){
                    if(ProviderUtil.hasValue(entity,field.getName())){
                        builder.append(field.getName())
                                .append(" = #{").append(field.getName()).append("} ")
                                .append(condition).append(" ");
                    }else {
                        throw new BaseException("@KeyAttribute修饰的字段不能为空");
                    }

                }
            }
            int index = builder.lastIndexOf(condition);
            if(index < 0){
                throw new BaseException("没有找到@KeyAttribute修饰的字段");
            }
            return builder.substring(0,index);

        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException(e.getMessage());
        }
    }

    /**
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> String getSortSuffix(T entity){
        String condition;
        if(entity.getBaseKyleUseASC() == null){
            return "";
        }
        if(entity.getBaseKyleUseASC()){
            condition = "ASC";
        }else {
            condition = "DESC";
        }
        Class cls = entity.getClass();
        Field[] fields = cls.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append(" ORDER BY ");
        try {
            for(Field field:fields){
                if(field.getAnnotation(SortAttribute.class) != null){
                    builder.append(field.getName()).append(" ")
                            .append(condition).append(",");

                }
            }
            int index = builder.lastIndexOf(",");
            if(index < 0){
                return "";
            }
            return builder.substring(0,index);

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取所有字段列表
     * 读取类中带@FieldAttribute注解的字段，如果都没有带该注解，则返回类中所有字段
     * @param cls 实体类型
     * @return {id,name}
     */
    public static List<String> getFields(Class cls){
        Field[] fields = cls.getDeclaredFields();
        List<String> fieldList = new ArrayList<>();
        List<String> allList = new ArrayList<>();
        //带@FieldAttribute注解的属性名
        for(Field field:fields){
            allList.add(field.getName());
            if(field.getAnnotation(FieldAttribute.class) != null){
                fieldList.add(field.getName());
            }
        }
        if(fieldList.size() == 0){
            return allList;
        }
        return fieldList;
    }

    /**
     * 判断一个对象的指定字段有没有值
     * @param entity 实体对象
     * @param fieldName 对象的字段名
     * @param <T> 实体类型
     * @return 值存在且不为null:返回true; 否则:返回false
     */
    public static <T> boolean hasValue(T entity,String fieldName){
        try {
            Class cls = entity.getClass();
            Method method = cls.getMethod("get" + StringUtils.captureName(fieldName));
            if(method.invoke(entity) == null){
                return false;
            }else {
                return true;
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
       return false;
    }

    public static void main(String[] args){

    }
}
