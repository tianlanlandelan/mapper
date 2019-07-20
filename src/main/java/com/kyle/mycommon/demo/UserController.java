package com.kyle.mycommon.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yangkaile
 * @date 2019-07-17 15:46:16
 */
@RestController
public class UserController {
    @Resource
    private UserMapper userMapper;

    @PostMapping("insert")
    public Object insert(String name,String phone,String email){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setPhone(phone);
        userEntity.setEmail(email);
        userMapper.baseInsertAndReturnKey(userEntity);
        return userEntity;
    }

    @GetMapping("getById")
    public Object getUserInfo(Integer id){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        return userMapper.baseSelectById(userEntity);
    }

    @GetMapping("getByPhone")
    public Object getByPhone(String phone){
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        return userMapper.baseSelectByCondition(userEntity);
    }
    @GetMapping("getByPhoneAndEmail")
    public Object getByPhoneAndEmail(String phone,String email){
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        userEntity.setEmail(email);
        userEntity.setBaseKyleUseAnd(true);
        return userMapper.baseSelectByCondition(userEntity);
    }
    @GetMapping("getByPhoneOrEmail")
    public Object getByPhoneOrEmail(String phone,String email){
        UserEntity userEntity = new UserEntity();
        userEntity.setPhone(phone);
        userEntity.setEmail(email);
        userEntity.setBaseKyleUseAnd(false);
        return userMapper.baseSelectByCondition(userEntity);
    }

    @GetMapping("getPageList")
    public Object getPageList(){
        UserEntity userEntity = new UserEntity();
        userEntity.setBaseKylePageSize(2);
        return userMapper.baseSelectPageList(userEntity);
    }

    @GetMapping("getCount")
    public Object getCount(){
        UserEntity userEntity = new UserEntity();
        return userMapper.baseSelectCount(userEntity);
    }




}
