package com.keller.commonutil.demo.po;

import com.keller.commonutil.mybatis.annotation.TableAttribute;
import com.keller.commonutil.mybatis.annotation.FieldAttribute;
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
