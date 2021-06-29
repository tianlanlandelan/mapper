package com.keller.commonutil.demo.mapper;

import com.keller.commonutil.mybatis.BaseMapper;
import com.keller.commonutil.demo.po.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 *  UserInfoMapper接口
 * @author yangkaile
 * @date 2021-06-21 21:45:37
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo>{

    /**
     *  表名
     */
    String tableName = "user_info";

    /**
     * 表中所有字段
     */
    String fullFields ="id, name";}
