package com.example.backenddemo.controller;

import com.example.backenddemo.Result;
import com.example.backenddemo.request.LoginRequest;
import com.example.backenddemo.response.LoginResponse;
import com.example.backenddemo.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginTestController {

    @PostMapping("/test/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request){
        System.out.println("用户名"+ request.getUsername());
        System.out.println("密码"+ request.getPassword());


        String token= JwtUtil.createToken(1L);

        return new Result<>(
                200,
                "登录成功",
                new LoginResponse(token)
        );
    }
}
