package com.code4j.mybatisutil.demo.po;

import com.code4j.mybatisutil.mybatis.annotation.FieldAttribute;
import com.code4j.mybatisutil.mybatis.annotation.TableAttribute;
import lombok.Data;


/**
 *
 * @author yangkaile
 * @date 2021-06-21 21:45:24
 */
@Data
@TableAttribute(name = "user_info")
public class UserInfo{
    /**
    * id
    */
    @FieldAttribute
    private Integer id;
    /**
    * 昵称
    */
    @FieldAttribute
    private String name;


  }
