package com.code4j.mybatisutil.mybatis;

/**
 * 基本查询
 * @author yangkaile
 * @date 2021-06-21 13:46:58
 * @param <T>
 */
public class BaseQuery<T> {

    /**
     * 自定义查询条件
     */
    private String condition;

    /**
     * 实体对象
     */
    private T entity;

    /**
     * 页面大小
     */
    private Integer pageSize = 15;
    /**
     * 要查询的页码
     */
    private Integer pageNo = 1;
    /**
     * 根据页面大小和要查询的页码计算出的起始行号
     */
    private Integer offset;

    private static final int MAX_SIZE = 100;

    public Integer getPageSize() {
        if(this.pageSize == null || this.pageSize <= 0){
            return 0;
        }
        if(this.pageSize > MAX_SIZE){
            return MAX_SIZE;
        }
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return this.pageNo == null || this.pageNo <=0 ? 1 : this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BaseQuery(T entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "BasePageQuery{" +
                "entity=" + entity +
                ", pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", offset=" + offset +
                '}';
    }
}
