package com.example.backenddemo.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;

@TableName("user")  //MyBatis-Plus 默认会根据实体类名推测表名。UserEntity 默认可能会去找 user_entity，而你的数据库表是 use
//@TableName("user")  明确告诉 MyBatis-Plus： UserEntity 对应数据库里的 user 表
public class UserEntity {
    @Version  //乐观锁
    private Integer version;

//     这就是一个对应数据库user表的数据对象
    private Long id;
    private  String name;
    @TableField(updateStrategy = FieldStrategy.ALWAYS)  //意思是 age 无论是不是 null，都参与 UPDATE。
    private Integer age;

    private String password;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

/*
    UserEntity
        ↓
    代表数据库

    UpdateUserRequest
        ↓
    代表“前端要修改什么”

    UserResponse / UserVO
        ↓
    代表“返回给前端什么”

    Entity 管数据库，Request 管前端传进来的数据，Response/VO 管返回给前端的数据。

* */


