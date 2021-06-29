package com.keller.commonutil;

import com.keller.commonutil.demo.mapper.UserInfoMapper;
import com.keller.commonutil.demo.po.UserInfo;
import com.keller.commonutil.mybatis.BasePage;
import com.keller.commonutil.mybatis.BaseQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Resource
    public UserInfoMapper userInfoMapper;

    @Test
    public void insert(){
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setName("张三");

        Integer result = userInfoMapper.baseInsert(user);
    }

    @Test
    public void save(){
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setName("张三save");

        Integer result = userInfoMapper.baseSave(user);
        System.out.println(result);
    }

    @Test
    public void insertList(){
        List<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfo user = new UserInfo();
            user.setId(10 + i);
            user.setName("李四" + i);

            list.add(user);
        }
        Integer result = userInfoMapper.baseInsertList(list);
        System.out.println(result);
    }

    @Test
    public void delete(){
        UserInfo user = new UserInfo();
        user.setId(1);

        Integer result = userInfoMapper.baseDeleteById(user);
        System.out.println(result);
    }

    @Test
    public void update(){
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setName("Name");
        Integer result = userInfoMapper.baseUpdateById(user);
        System.out.println(result);
    }

    @Test
    public void updateWithoutNull(){
        UserInfo user = new UserInfo();
        user.setId(1);
        Integer result = userInfoMapper.baseUpdateById(user);
    }

    @Test
    public void select(){
        UserInfo user = new UserInfo();
        user.setId(1);

        UserInfo result = userInfoMapper.baseSelectById(user);
        System.out.println(result);
    }

    @Test
    public void selectList(){
        UserInfo user = new UserInfo();
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        List<UserInfo> result = userInfoMapper.baseSelectList(query);
        System.out.println(result);
    }

    @Test
    public void selectPageList(){
        UserInfo user = new UserInfo();
        user.setName("张三");
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        List<UserInfo> result = userInfoMapper.baseSelectPageList(query);
        System.out.println(result);
    }

    @Test
    public void selectPage(){
        UserInfo user = new UserInfo();
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        BasePage<UserInfo> result = userInfoMapper.baseSelectPage(query);
        System.out.println(result);
    }

    @Test
    public void selectCount(){
        UserInfo user = new UserInfo();
        user.setName("张三");
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        Integer result = userInfoMapper.baseSelectCount(query);
        System.out.println(result);
    }
}
