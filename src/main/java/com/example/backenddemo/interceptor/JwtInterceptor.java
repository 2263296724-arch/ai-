package com.example.backenddemo.interceptor;

import com.example.backenddemo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


public class JwtInterceptor implements HandlerInterceptor {
//    Alt + Insert → Implement Methods → 选择 preHandle
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception{
        // 从请求头获取 Authorization
        String authorization = request.getHeader("Authorization"); //从 HTTP 请求头里面拿 Authorization。
        // 没有 Token，直接拦截
        if (authorization == null){ //请求头里面没有 Authorization。
            return false;  //拦截，不让 Controller 继续执行。
        }
        // 去掉 Bearer 前缀
        String token =authorization.substring(7);

        // 验证 JWT，并获取用户 ID
        String userId = JwtUtil.getUser(token);


        return true;
    }
}
