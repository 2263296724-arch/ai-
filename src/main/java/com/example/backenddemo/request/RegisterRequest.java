package com.example.backenddemo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

//    用户名不能为空
    @NotBlank //这个字段不能为空，而且不能只是空格。
    private String username;
    @NotBlank
    @Size(min=6,message = "密码长度不能少于6位")  //@Size(min = 6) 字符串长度至少为 6。
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
/*
* @Valid 用来触发 Bean Validation 参数校验，会根据 DTO 上的校验注解，比如 @NotBlank、@Size，检查前端传入的数据是否符合要求。*/