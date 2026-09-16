package com.example.backenddemo.interceptor;

import com.example.backenddemo.Result;
import com.example.backenddemo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;


public class JwtInterceptor implements HandlerInterceptor {
//    Alt + Insert → Implement Methods → 选择 preHandle

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public boolean preHandle(
            HttpServletRequest request,  //代表“当前这一次 HTTP 请求”的对象。 把它理解成一个请求信息盒子。
            HttpServletResponse response, // 要返回给前端的响应
            Object handler  //这次请求最终要执行的 Controller 方法
    ) throws Exception{
        // 从请求头获取 Authorization
        String authorization = request.getHeader("Authorization"); //从 HTTP 请求头里面拿 Authorization。
        // 没有 Token，直接拦截
//        if (authorization == null){ //请求头里面没有 Authorization。
//            return false;  //拦截，不让 Controller 继续执行。


        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(401); //告诉浏览器：这次 HTTP 请求的状态码是 401。
            // 告诉浏览器：返回的是 JSON
            response.setContentType("application/json;charset=UTF-8"); //我接下来返回给你的内容，是 JSON ，否则浏览器可能把它当成普通文本。
//            response.getWriter().write(
//                    "{\"code\":401,\"message\":\"请先登录\",\"data\":null}"
//            );
            // 创建统一返回对象
            Result<Void> result= new Result<>(
                    401,
                    "请先登录",
                    null);
            // Java 对象 → JSON 字符串
            String json =objectMapper.writeValueAsString(result);

            // 把 JSON 返回给前端
            response.getWriter().write(json);
            return false;
        }
        // 去掉 Bearer 前缀
        String token =authorization.substring(7);

        // 验证 JWT，并获取用户 ID
        String userId = JwtUtil.getUser(token);
        // 把用户 ID 放进当前请求
        request.setAttribute("userId",userId); //把数据暂时放到当前这一次 HTTP 请求里面，后面的 Controller 可以取出来。


        return true;
    }
}
/*
    先记一句话
    HttpServletRequest request
    就是：
    代表“当前这一次 HTTP 请求”的对象。

    你可以把它理解成一个请求信息盒子。
    例如你访问：
    GET /test/current-user
    浏览器实际上会向后端发送一个 HTTP 请求。
    这个请求里面可能包含很多东西：
    请求
    ├── 请求方法：GET
    ├── 请求地址：/test/current-user
    ├── 请求头：Authorization: Bearer xxx
    ├── 参数：page=1
    └── 其他请求信息
    Spring/Servlet 就把这些请求信息放进：
    HttpServletRequest request
    所以你可以通过它获取各种信息。
    你已经用过一个
    在 JWT 拦截器里：
    String authorization = request.getHeader("Authorization");
    意思就是：
    从当前请求的请求头里，拿出 Authorization。

    所以：
    浏览器
     ↓
    HTTP 请求
     ↓
    HttpServletRequest request
     ↓
    request.getHeader()
     ↓
    Authorization


    request.getHeader(...) 获取请求头
    request.getParameter(...)  获取请求参数
    request.setAttribute(...)  往当前请求里暂存数据
*/