package com.kyle.mycommon.mybatis.provider;

import com.kyle.mycommon.mybatis.BaseEntity;

import com.kyle.mycommon.util.Console;
import com.kyle.mycommon.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础的 Mybatis SQL 提供类
 * @author yangkaile
 * @date 2019-07-12 15:44:55
 *
 */
public class BaseSelectProvider {

    /**
     * 缓存selectById语句
     * @param args
     */
    public static Map<String,String> selectByIdMap = new ConcurrentHashMap<>(16);

    public static Map<String,String> selectAllMap = new ConcurrentHashMap<>(16);

    public static Map<String,String> selectPrefixMap = new ConcurrentHashMap<>(16);


    /**
     * 根据ID 查询数据
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return SELECT id,name... FROM route WHERE id = #{id}
     */
    public static  <T> String selectById(T entity){
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = selectByIdMap.get(className);
        if(StringUtils.isEmpty(sql)){
            sql = getSelectPrefix(cls) + " WHERE id = #{id}";
            selectByIdMap.put(className,sql);
        }
        Console.info("selectById",sql,entity);
        return sql;
    }

    /**
     * 根据主键查询数据，要求至少有一个主键，且主键必须有值
     * @param entity
     * @param <T>
     * @return
     */
    public static  <T> String selectByKey(T entity){
        try {
            String sql = getSelectPrefix(entity.getClass()) + ProviderUtil.getConditionByKeySuffix(entity);
            Console.info("selectByKey",sql,entity);
            return sql;
        }catch (Exception e){
            return null;
        }
    }


    /**
     * 查询所有数据，不带条件
     * @param entity 实体对象
     * @param <T> 实体类型
     * @return SELECT id,name... FROM router
     */
    public static <T> String selectAll(T entity){
        Class cls = entity.getClass();
        String className = cls.getName();
        String sql = selectAllMap.get(className);
        if(StringUtils.isEmpty(sql)){
            sql = getSelectPrefix(cls);
            selectAllMap.put(className,sql);
        }
        Console.info("selectAll",sql,entity);
        return sql;
    }


    /**
     *
     * 带条件的查询，该查询为动态查询，不可缓存
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * 传入对象中带@SortAttribute注解的字段作为排序字段
     * @param entity 实体对象
     * param and 多个查询条件组合方式 null:不指定查询条件  true:多个查询条件用AND连接  false:多个查询条件用OR连接
     * param asc 排序方式  null:不指定排序方式  true:按指定排序字段升序   false:按指定排序字段降序
     * @param <T> 实体类型
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC
     */
    public static <T extends BaseEntity> String selectByCondition(T entity){
        String sql = getSelectPrefix(entity.getClass())
                + ProviderUtil.getConditionSuffix(entity)
                + ProviderUtil.getSortSuffix(entity);
        Console.info("selectByCondition",sql,entity);
        return  sql;
    }

    /**
     * 查询记录总数
     * @param entity
     * @param <T>
     * @return SELECT COUNT(1) FROM router
     */
    public static <T> String selectCount(T entity){
        return "SELECT COUNT(1) FROM " + ProviderUtil.getTableName(entity.getClass());
    }

    /**
     * 根据条件查询记录总数
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity
     * param and 多个查询条件组合方式 null:不指定查询条件  true:多个查询条件用AND连接  false:多个查询条件用OR连接
     * @param <T>
     * @return SELECT COUNT(1) FROM router WHERE name = #{name} AND serviceName = #{serviceName}
     */
    public static <T extends BaseEntity> String selectCountByCondition(T entity){
        return selectCount(entity) + ProviderUtil.getConditionSuffix(entity);
    }

    /**
     * 不加条件的分页查询
     * @param entity 实体对象
     * param startRows 起始行
     * param pageSize 查询页大小
     * @param <T> 实体类型
     * @return  SELECT id,name... FROM router  LIMIT #{startRows},#{pageSize}
     */
    public static <T extends BaseEntity> String selectPageList(T entity){
        return selectAll(entity) + " LIMIT #{baseKyleStartRows},#{baseKylePageSize}";
    }

    /**
     * 加条件的分页查询
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     * @param entity
     * param and 多个查询条件组合方式 null:不指定查询条件  true:多个查询条件用AND连接  false:多个查询条件用OR连接
     * param asc 排序方式  null:不指定排序方式  true:按指定排序字段升序   false:按指定排序字段降序
     * param startRows 起始行数
     * param pageSize 查询条数
     * @param <T>
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC LIMIT #{startRows},#{pageSize}
     */
    public static <T extends BaseEntity> String selectPageListByCondition(T entity){
        return selectByCondition(entity) + " LIMIT #{startRows},#{pageSize}";
    }

    /**
     * 获取通用查询前缀
     * @param cls 实体类类型
     * @return SELECT 所有字段 FROM 表名
     */
    private static <T> String getSelectPrefix(Class cls){
        String className = cls.getName();
        String sql = selectPrefixMap.get(className);
        if(StringUtils.isNotEmpty(sql)){
            return sql;
        }else {
            sql = "SELECT " + ProviderUtil.getFieldStr(cls) + " FROM " + ProviderUtil.getTableName(cls) + " ";
            return sql;
        }
    }

    public static void main(String[] args){

    }

}
