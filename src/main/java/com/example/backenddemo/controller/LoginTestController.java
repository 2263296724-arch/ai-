package com.example.backenddemo.controller;

import com.example.backenddemo.Result;
import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.request.LoginRequest;
import com.example.backenddemo.response.LoginResponse;

import com.example.backenddemo.service.LoginTestService;
import com.example.backenddemo.service.UserService;
import com.example.backenddemo.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginTestController {
//    构造器注入
    private final UserService userService;
    private final LoginTestService loginTestService;

    public LoginTestController(
            UserService userService,
            LoginTestService loginTestService) {
        this.userService = userService;
        this.loginTestService = loginTestService;
    }

    @PostMapping("/test/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest request){
        System.out.println("========== 进入登录方法 ==========");
        System.out.println("用户名"+ request.getUsername());
        System.out.println("密码"+ request.getPassword());


        // 暂时注释：之前固定生成用户 1 的 Token
        // String token = JwtUtil.createToken(1L);


        LoginResponse loginresponse = loginTestService.login(  //Controller 告诉 LoginTestService：“帮我执行登录业务。”
                request.getUsername(),
                request.getPassword()
        ) ;

        return new Result<>(
                200,
                "登录成功",
                loginresponse
        );
    }
}
