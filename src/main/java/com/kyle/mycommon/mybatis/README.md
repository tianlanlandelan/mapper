### 假设场景：When we need?

``` java
    public class User{
        //id
        private int id;
        //名字
        private String name;
        //性别
        private int gender;
        //国家、地区
        private String region;
    }

```

### 预期目标：What we want?
我们想做到不写Mapper的代码来实现以下SQL:
#### Insert
- 基础插入语句：Insert
#### Delete
- 根据 ID 删除：DeleteById
- 根据条件删除：DeleteByName、DeleteByNameAndGender
#### Update
- 根据 ID 更新：UpdateById
- 根据条件更新：UpdateByName、UpdateByNameAndGender
#### Select
- 根据 ID 查询：SelectById
- 查询全量数据：SelectAll
- 根据条件查询：SelectByName、SelectByNameAndGender
- 查询全部记录条数：SelectCount
- 查询满足指定条件的记录条数：SelectCountByName、SelectCountByNameAndGender
  - 查询和你名字相同的人有多少
- 分页查询：SelectPageList
- 带条件的分页查询：SelectPageListByName、SelectPageListByNameAndGender

### 所需数据：What we need?
我们分析分析想要生成上面我们期望的那些SQL的话，我们需要知道哪些信息：
#### 表名
所有的查询语句离不开表名

#### 字段名


#### 可以用来作为查询条件的字段名


#### 分页参数
在MySql中，分页查询的格式为：
```sql
SELECT * FROM tableName LIMIT startRows,pageSize;
```
相比较其他查询语句而言，多了两个参数：startRows 和 pageSize，这两个参数无法在实体类中拿到，我们需要额外传递过去

```java
    @Select("SELECT * FROM user LIMIT #{startRows},#{pageSize}")
    List<User> selectPageList(User user,int startRows,int pageSize);
```


### 设计思路：How we do it?
#### 标注表名
表名与类名和类的属性没有什么必然关系，是独立存在的。
当然，你也可以说：我设计的类名和表名是一样的呢，用类名做表名不就好了吗？
在这里，我们不建议直接将表名做为类名：
1. 数据库表名和 Java 类名命名规则不一样
2. 表名设计和实体类名的设计带有很强烈的个人风格

我们可以用一个用于类上的自定义注解（如：**@TableAttribute** ）来定义表名
#### 标注字段名
用 **@FieldAttribute** 注解将数据库字段和实体类的其他属性区分开
#### 标注索引字段
用 **@IndexAttribute** 注解标注索引字段
#### 添加分页查询参数


### 性能优化：What we can do better?
