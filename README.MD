## 项目简介

### 项目特点

- 体积小，能量大
  - jar 包 58Kb
  - 引入后即可实现 增删改查操作，包含条件查询、分页查询、数据统计、添加、删除、修改等功能
- 简单易用
  - 不需要书写Mapper代码
  - 不需要复杂配置
  - 引入 jar包，实体类添加注解，即可使用
- 快速高效
  - 内部设置了缓存，同一张数据表只解析一次，代码执行效率高
  - 简化你的代码，让你的搬砖效率大幅提高
- 开放源代码
  - 可以在此基础上自由发挥

### 目录结构

- mybatis

  - annotation
    - FieldAttribute.java：数据库字段注解
    - TableAttribute.java：数据表注解
  - provider
    - BaseDeleteProvider.java：Delete语句生成器
    - BaseInsertProvider.java：Insert语句生成器
    - BaseSelectProvider.java：Select语句生成器
    - BaseUpdateProvider.java：Update语句生成器
  - BaseMapper.java：BaseMapper接口，所有Mapper 继承此接口即可拥有基础增删改查能力
  - BasePage.java：分页查询结果封装类，封装了页码、数据总数、页面总数、数据列表等
  - BaseQuery.java：基础查询类
  - SqlFieldReader.java：SQL 字段解析类
  - TypeCaster.java：类型转换器

  

### 功能展示

#### 举例

##### user_info 表

```mysql
CREATE TABLE `user_info` (
  `id` int(8) NOT NULL  COMMENT 'id',
  `name` varchar(20)   NOT NULL COMMENT '昵称',
  PRIMARY KEY (`id`)
)
```



##### UserInfo 类

```java
private Integer id;
private String name;
```



#### Entity 处理



```java
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
```





#### Mapper 处理



```java
public interface UserInfoMapper extends BaseMapper<UserInfo>{
}
```



#### 增

##### 添加数据

```java
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setName("张三");

        Integer result = userInfoMapper.baseInsert(user);
```



##### 批量添加

```java
        List<UserInfo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfo user = new UserInfo();
            user.setId(10 + i);
            user.setName("李四" + i);

            list.add(user);
        }
        Integer result = userInfoMapper.baseInsertList(list);
```



##### 保存数据

```java
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setName("张三save");

        Integer result = userInfoMapper.baseSave(user);
```



#### 删

##### 删除数据

```java
        UserInfo user = new UserInfo();
        user.setId(1);

        Integer result = userInfoMapper.baseDeleteById(user);
```



#### 改

##### 更新数据

```java
        UserInfo user = new UserInfo();
        user.setId(1);
        user.setName("Name");
        Integer result = userInfoMapper.baseUpdateById(user);
```



##### 部分更新

```java
        UserInfo user = new UserInfo();
        user.setId(1);
        Integer result = userInfoMapper.baseUpdateById(user);
```



#### 查

##### 根据ID查询

```java
        UserInfo user = new UserInfo();
        user.setId(1);

        UserInfo result = userInfoMapper.baseSelectById(user);
```



##### 查询列表

```java
        UserInfo user = new UserInfo();
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        List<UserInfo> result = userInfoMapper.baseSelectList(query);
```



##### 分页查询列表

```java
        UserInfo user = new UserInfo();
        user.setName("张三");
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        List<UserInfo> result = userInfoMapper.baseSelectPageList(query);
```



##### 分页查询

```java
        UserInfo user = new UserInfo();
        user.setName("张三");
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        BasePage<UserInfo> result = userInfoMapper.baseSelectPage(query);
```



##### 统计总数

```java
        UserInfo user = new UserInfo();
        user.setName("张三");
        BaseQuery<UserInfo> query = new BaseQuery<>(user);
        Integer result = userInfoMapper.baseSelectCount(query);
```

