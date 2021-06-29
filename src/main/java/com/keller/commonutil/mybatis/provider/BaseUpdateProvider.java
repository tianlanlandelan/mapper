package com.keller.commonutil.mybatis.provider;

import com.keller.commonutil.mybatis.SqlFieldReader;
import com.keller.commonutil.util.Console;
import com.keller.commonutil.util.StringUtils;

import java.util.List;

/**
 * 基础的Update语句提供类
 * @author yangkaile
 * @date 2019-07-18 14:27:30
 */
public class BaseUpdateProvider {

    /**
     * 根据id 更新数据，空值不更新 ，要求数据有id字段
     * @param entity
     * @param <T>
     * @return UPDATE router SET methodName = #{methodName} ,createTime = #{createTime} WHERE id = #{id}
     */
    public static <T> String updateById(T entity){
        String sql = getUpdatePrefix(entity,true) + " WHERE id = #{id}";
        Console.info("updateById", SqlFieldReader.getTableName(entity),sql,entity);
        return sql;
    }

    /**
     *
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> String updateByIdWithoutNull(T entity){
        String sql = getUpdatePrefix(entity,false) + " WHERE id = #{id}";
        Console.info("updateById", SqlFieldReader.getTableName(entity),sql,entity);
        return sql;
    }

    /**
     * 获取更新操作的前缀部分
     * @param entity    实体对象
     * @param allowNull   是否允许更新空值
     * @return UPDATE 表名 SET
     */
    private static <T> String getUpdatePrefix(T entity,boolean allowNull){
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(SqlFieldReader.getTableName(entity)).append(" SET ");
        List<String> fields = SqlFieldReader.getFields(entity);
        try{
            for(String field:fields){
                if(allowNull || SqlFieldReader.hasValue(entity,field)){
                    builder.append(StringUtils.humpToLine(field))
                            .append(" = #{")
                            .append(field)
                            .append("} ")
                            .append(",");
                }
            }
            return builder.substring(0,builder.lastIndexOf(",")) ;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
