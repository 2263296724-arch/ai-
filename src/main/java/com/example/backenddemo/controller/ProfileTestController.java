package com.example.backenddemo.controller;

import com.example.backenddemo.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileTestController {
    @GetMapping("/test/profile")
    public String profile(
            @RequestHeader("Authorization") String authorization   //@RequestHeader("Authorization") 从 HTTP 请求头里面获取 Authorization。
    ){
//        去掉Bearer 前缀
        String token =authorization.substring(7);

//        解析Token，得到用户id
        String userId= JwtUtil.getUser(token);
        return "当前用户ID"+ userId;

    }
}
//前端登录成功后保存 Token，后续请求一般通过 HTTP 请求头 Authorization 携带 Token，后端验证 Token 后识别当前用户。