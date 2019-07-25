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
        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        userMapper.baseInsertAndReturnKey(user);
        return user;
    }

    @GetMapping("getById")
    public Object getUserInfo(Integer id){
        User user = new User();
        user.setId(id);
        return userMapper.baseSelectById(user);
    }

    @GetMapping("getByPhone")
    public Object getByPhone(String phone){
        User user = new User();
        user.setPhone(phone);
        return userMapper.baseSelectByCondition(user);
    }
    @GetMapping("getByPhoneAndEmail")
    public Object getByPhoneAndEmail(String phone,String email){
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setBaseKyleUseAnd(true);
        return userMapper.baseSelectByCondition(user);
    }
    @GetMapping("getByPhoneOrEmail")
    public Object getByPhoneOrEmail(String phone,String email){
        User user = new User();
        user.setPhone(phone);
        user.setEmail(email);
        user.setBaseKyleUseAnd(false);
        return userMapper.baseSelectByCondition(user);
    }

    @GetMapping("getPageList")
    public Object getPageList(){
        User user = new User();
        user.setBaseKylePageSize(2);
        user.setBaseKyleUseASC(true);
        return userMapper.baseSelectPageList(user);
    }

    @GetMapping("getCount")
    public Object getCount(){
        User user = new User();
        return userMapper.baseSelectCount(user);
    }




}
