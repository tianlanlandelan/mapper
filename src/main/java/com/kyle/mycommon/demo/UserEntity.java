package com.kyle.mycommon.demo;

import com.kyle.mycommon.mybatis.BaseEntity;
import com.kyle.mycommon.mybatis.annotation.IndexAttribute;
import com.kyle.mycommon.mybatis.annotation.SortAttribute;
import com.kyle.mycommon.mybatis.annotation.TableAttribute;

import java.util.Date;

/**
 * @author yangkaile
 * @date 2019-07-17 15:22:14
 * 用户实体类，对应user_info表
 */
@TableAttribute("user_info")
public class UserEntity extends BaseEntity {
    /**
     * id
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别 0:男  1:女
     */
    private Integer gender;
    /**
     * 国家、地区
     */
    private String region;
    /**
     * 手机号 可以作为查询条件
     */
    @IndexAttribute
    private String phone;
    /**
     * 邮箱  可以作为查询条件
     */
    @IndexAttribute
    private String email;
    /**
     * 注册时间 可以作为排序条件
     */
    @SortAttribute
    private Date createTime = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", region='" + region + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
