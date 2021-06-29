package com.keller.commonutil.mybatis;

import lombok.Data;

import java.util.List;

/**
 * 基础的页面
 * @author yangkaile
 * @date 2021-02-09 00:00:01
 */
@Data
public class BasePage<T> {
    /**
     * 页面大小
     */
    private Integer pageSize;

    /**
     * 页码
     */
    private Integer pageNo;

    /**
     * 数据总数
     */
    private Integer count;

    /**
     * 总页数
     */
    private Integer pageCount;

    /**
     * 数据
     */
    private List<T> list;
}
