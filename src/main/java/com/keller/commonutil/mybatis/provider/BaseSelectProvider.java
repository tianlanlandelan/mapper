package com.keller.commonutil.mybatis.provider;

import com.keller.commonutil.mybatis.BaseQuery;
import com.keller.commonutil.util.Console;
import com.keller.commonutil.mybatis.SqlFieldReader;
import com.keller.commonutil.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基础的Select提供类
 *
 * @author yangkaile
 * @date 2019-07-12 15:44:55
 */
public class BaseSelectProvider {

    public static Map<String, String> selectPrefixMap = new ConcurrentHashMap<>(16);

    /**
     * 根据ID 查询数据
     *
     * @param entity 实体对象
     * @param <T>    实体类型
     * @return SELECT id,name... FROM route WHERE id = #{id}
     */
    public static <T> String selectById(T entity) {
        String sql = getSelectPrefix(entity) + " WHERE id = #{id}";
        Console.info("selectById", SqlFieldReader.getTableName(entity), sql, entity);
        return sql;
    }

    /**
     * 使用自定义的查询条件查询列表
     * @param query
     * @param <T>
     * @return
     */
    public static <T> String selectList(BaseQuery<T> query) {
        T entity = query.getEntity();
        String sql = getSelectPrefix(entity) + SqlFieldReader.getCondition(query,entity,false);
        Console.info("selectList", SqlFieldReader.getTableName(entity), sql, entity);
        return sql;
    }

    /**
     * 加条件的分页查询
     * @param query
     * @param <T>
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC LIMIT #{startRows},#{pageSize}
     */
    public static <T> String selectPageList(BaseQuery<T> query) {
        T entity = query.getEntity();
        String sql = getSelectPrefix(entity)
                + SqlFieldReader.getCondition(query,entity,true)
                + " LIMIT #{offset},#{pageSize}";
        Console.info("selectPageList", SqlFieldReader.getTableName(entity), sql, entity);
        return sql;
    }

    /**
     * 根据条件查询记录总数
     * 传入的对象中带@IndexAttribute注解的字段有值的都作为查询条件
     *
     * @param query param and 多个查询条件组合方式 null:不指定查询条件  true:多个查询条件用AND连接  false:多个查询条件用OR连接
     * @param <T>
     * @return SELECT COUNT(1) FROM router WHERE name = #{name} AND serviceName = #{serviceName}
     */
    public static <T> String selectCount(BaseQuery<T> query) {
        T entity = query.getEntity();
        String sql = "SELECT COUNT(*) FROM "
                + SqlFieldReader.getTableName(entity)
                + SqlFieldReader.getCondition(query,entity,false);
        Console.info("selectCount", SqlFieldReader.getTableName(entity) , sql, entity);
        return sql;
    }

    /**
     * 获取通用查询前缀
     *
     * @param entity 实体类类型
     * @return SELECT 所有字段 FROM 表名
     */
    private static <T> String getSelectPrefix(T entity) {
        String className = entity.getClass().getName();
        if (selectPrefixMap.containsKey(className)) {
            return selectPrefixMap.get(className);
        }

        String sql = "SELECT " + StringUtils.humpToLine(SqlFieldReader.getFieldStr(entity))
                + " FROM " + SqlFieldReader.getTableName(entity) + " ";

        selectPrefixMap.put(className, sql);
        return sql;

    }
}
