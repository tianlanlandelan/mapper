package com.kyle.mycommon.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段名注解，用于要查询的字段名之前
 * 当一个实体类中的所有属性都作为字段名时，可以都不加该注解
 * 当部分属性作为字段名时，需要在要作为字段名的属性前加该注解
 * 在select * from tableName时,代替*的位置
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAttribute {

}
