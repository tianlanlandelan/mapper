package com.kyle.mycommon.demo;

import com.kyle.mycommon.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangkaile
 * @date 2019-07-17 15:23:45
 * UserMapper 映射到UserEntity对象
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
