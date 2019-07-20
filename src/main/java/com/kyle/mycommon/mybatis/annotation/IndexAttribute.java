package com.kyle.mycommon.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引字段注解，可以根据索引字段进行查找、删除操作
 * @author yangkaile
 * @date 2019-07-12 16:43:43
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IndexAttribute {

}
