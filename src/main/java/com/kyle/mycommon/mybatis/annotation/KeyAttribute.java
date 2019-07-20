package com.kyle.mycommon.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库主键的注解
 * 1. 支持单字段作为主键也支持多个字段作为联合主键使用
 * 2. 单字段作为主键时，将该字段上添加该注解即可
 * 3. 多个字段作为主键时，将每个主键字段添加该注解即可
 *
 * 4. 建议使用单字段作为主键
 * 5. 当使用id字段作为主键时，不需要添加该注解
 * 6. BaseMapper里后缀为ById的方法均是依照id字段作为主键设计的
 * @author yangkaile
 * @date 2019-7-17 20:39:40
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyAttribute {
}
