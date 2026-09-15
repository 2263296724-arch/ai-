package com.example.backenddemo.controller;


import com.example.backenddemo.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterTestController {

    @PostMapping("/test/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest){  //@Valid 检查 RegisterRequest 里面的校验规则
        return "注册成功";
    }
}
/*
    前端传 username=""
            ↓
    @RequestBody 转成 RegisterRequest
            ↓
    @Valid 开始校验
            ↓
    @NotBlank 发现 username 是空的
            ↓
    校验失败
            ↓
    Spring 返回 400
*/