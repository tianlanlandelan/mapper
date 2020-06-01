### 依赖包

```xml
<dependencies>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
  
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
```



### 目录结构

- mybatis：MyBatis 通用 Mapper 相关
  - annotation：注解相关
    - FieldAttribute.java：字段注解，标示成员变量为数据库字段，并设置相应的约束信息，如：长度、非空、查询字段、索引字段等
    - KeyAttribute.java：主键注解，标示成员变量为主键，支持标注为自增主键
    - SortAttribute.java：排序注解，标示成员变量为排序字段
    - TableAttribute.java：数据表注解，标示实体类对应的表名称、说明信息
  - provider：Sql 语句生成相关
    - BaseCreateProvider.java：数据表创建语句的生成器，支持生成索引
    - BaseDeleteProvider.java：delete 语句生成器，支持根据 ID、主键、自定义条件删除
    - BaseInsertProvider.java：insert 语句生成器，支持自增主键的 `insert` 操作
    - BaseSelectProvider.java：select 语句生成器，支持分页查询、统计查询、自定义条件查询等
    - BaseUpdateProvider.java：update 语句生成器，支持根据 ID、主键修改数据
  - BaseEntity.java：所有实体类的父类，提供了自定义查询条件、分页查询、排序的扩展
  - BaseException.java：自定义异常，在通用 Mapper 运行过程中抛出的异常
  - BaseMapper.java：所有 Mapper 的父类，提供了通用 Mapper 功能
  - SqlFieldReader.java：Sql 字段解析类，用于解析实体类中的自定义注解，为生成 Sql 语句服务
  - TypeCaster.java：类型转换，用于将 Java 中的数据类型转换成相应的 MySql 数据类型
- util：工具类相关
  - Console.java：日志输出工具类，用于在控制台、日志文件中输出相关信息
  - ObjectUtils.java：Object 工具类，用于对象的空值判断
  - StringUtils.java：字符串工具类，用于生成指定格式的字符串及对字符串进行各种处理



### 使用方式



#### 实体类继承 BaseEntity 并使用注解

``` java
@TableAttribute(name = "user_info",comment = "用户信息表")
public class UserInfo   extends BaseEntity {
  	// 标注是自增主键
    @KeyAttribute(autoIncr = true)
  	// 标注是数据库字段
    @FieldAttribute
    private int id;
		
  	// 设置字段注释、非空约束、作为查询条件
    @FieldAttribute(value = "用户类型",notNull = true,isCondition = true)
    private Integer type;


    @FieldAttribute(value = "密码",length = 200, isDetailed = true)
    private String password;
		
  	// 设置字段注释、非空约束、字段长度、是索引字段
    @FieldAttribute(value = "邮箱",notNull = true,length = 200,isIndex = true)
    private String email;

    @FieldAttribute
  	@SortAttrtibute
    private Date createTime = new Date();

    @FieldAttribute(value = "用户账号状态",isCondition = true)
    private Integer status ;

    @FieldAttribute(value = "是否删除，1 表示删除",isCondition = true)
    private Integer isDelete;
  ... ...
}
```



#### Dao 层继承 BaseMapper



```java
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

}
```



#### 调用



```java
    /**
     * 根据类型和邮箱查询用户
     * @param type
     * @param email
     * @return
     */
    private UserInfo getByEmailAndType(int type,String email){
        UserInfo userInfo = new UserInfo();
      	// 设置查询条件1 Email
        userInfo.setEmail(email);
      	// 设置查询条件2 Type
        userInfo.setType(type);
      	// 设置使用 And 连接多个查询条件
        userInfo.setBaseKyleUseAnd(true);
      	// 执行查询并返回结果
        List<UserInfo> list = userMapper.baseSelectList(userInfo);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
```

