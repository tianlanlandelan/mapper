package com.keller.commonutil.mybatis;

import com.keller.commonutil.mybatis.provider.*;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * @author yangkaile
 * @date 2019-07-12 15:27:00
 * BaseMapper提供了通用的Mapper
 * 实现了insert、insertAndReturnKey、deleteById、SelectById、updateById等基本的增删改查方法
 * 数据实体的Mapper继承该接口即可
 *
 * BaseMapper还提供了带条件的删除和查询操作，以及带条件的分页查询，需要实体类继承BaseEntity方可使用
 *
 * @param <T>
 */
public interface BaseMapper<T> {
    /**
     * 插入数据
     * 将实体类的所有字段和字段的值分别列出来，适用于主键不是自增的表
     * @param entity
     * @return INSERT INTO tableName (id,name...) VALUES (#{id},#{name}...)
     * @throws DuplicateKeyException 当唯一字段重复插入时，会抛该异常
     */
    @InsertProvider(type = BaseInsertProvider.class,method = "insert")
    Integer baseInsert(T entity) throws DuplicateKeyException;

    /**
     * 保存数据
     * 如果存在 id 相同的数据，更新
     * 如果不存在，新增
     * @param entity
     * @return 该操作影响的数据条数
     */
    default Integer baseSave(T entity){
        T result = baseSelectById(entity);
        if(result == null){
            return baseInsert(entity);
        }
        return  baseUpdateById(entity);
    }

    /**
     * 批量插入
     * @param list
     * @return
     * @throws DuplicateKeyException
     */
    @InsertProvider(type = BaseInsertProvider.class,method = "insertList")
    Integer baseInsertList(@Param(value = "list") List<T> list) throws DuplicateKeyException;

    /**
     * 插入数据并返回自增的主键(建议使用id)
     * @param entity
     * @return INSERT INTO tableName (name...) VALUES(#{name}...)
     * @throws DuplicateKeyException 当唯一字段重复插入时，会抛该异常
     */
    @InsertProvider(type = BaseInsertProvider.class,method = "insert")
    @Options(useGeneratedKeys=true,keyProperty = "id", keyColumn = "id")
    Integer baseInsertAndReturnKey(T entity) throws DuplicateKeyException;


    /**
     * 根据Id删除数据，要求必须有id字段
     * @param entity
     * @return
     */
    @DeleteProvider(type = BaseDeleteProvider.class,method = "deleteById")
    Integer baseDeleteById(T entity);

    /**
     * 根据id 更新数据，空值也更新 ，要求必须有id字段
     * @param entity
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class,method = "updateById")
    Integer baseUpdateById(T entity);

    /**
     * 根据id 更新数据，空值不更新 ，要求必须有id字段
     * @param entity
     * @return
     */
    @UpdateProvider(type = BaseUpdateProvider.class,method = "updateByIdWithoutNull")
    Integer baseUpdateByIdWithoutNull(T entity);


    /**
     * 根据Id 查找数据，要求必须有id 字段
     * @param entity
     * @return
     */
    @SelectProvider(type= BaseSelectProvider.class,method = "selectById")
    T baseSelectById(T entity);

    /**
     * 查询列表，所有非空字段都会作为查询条件
     * @param query
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC
     */
    @SelectProvider(type= BaseSelectProvider.class,method = "selectList")
    List<T> baseSelectList(BaseQuery<T> query);



    /**
     * 分页查询，可指定查询条件
     * @param query
     * @return SELECT id,name... FROM router  WHERE name = #{name} AND serviceName = #{serviceName}  ORDER BY createTime ASC LIMIT #{startRows},#{pageSize}
     */
    @SelectProvider(type = BaseSelectProvider.class,method = "selectPageList")
    List<T> baseSelectPageList(BaseQuery<T> query);

    /**
     * 分页查询
     * @param query
     * @return BasePage 包含查询结果、分页参数、数据总量等
     */
    default BasePage<T> baseSelectPage(BaseQuery<T> query) {
        T entity = query.getEntity();
        // 查询出的数据
        List list = baseSelectPageList(query);

        // 数据总数
        Integer count = baseSelectCount(query);

        BasePage<T> page = new BasePage<>();
        page.setPageSize(query.getPageSize());
        page.setPageNo(query.getPageNo());
        page.setCount(count);
        page.setPageCount(count / page.getPageSize() + (count % page.getPageSize() > 0 ? 1 : 0));
        page.setList(list);

        return page;
    }

    /**
     * 根据条件查询记录总数
     * @param query
     * @return SELECT COUNT(1) FROM router WHERE name = #{name} AND serviceName = #{serviceName}
     */
    @SelectProvider(type = BaseSelectProvider.class,method = "selectCount")
    Integer baseSelectCount(BaseQuery<T> query);
}
