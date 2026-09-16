package com.example.backenddemo.controller;

import com.example.backenddemo.Result;
import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.response.UserProfileResponse;
import com.example.backenddemo.service.ProfileTestService;
import com.example.backenddemo.service.UserService;
import com.example.backenddemo.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileTestController {

    private final UserService userService;

    private final ProfileTestService profileTestService;
    public ProfileTestController(UserService userService,ProfileTestService profileTestService){
        this.userService=userService;
        this.profileTestService=profileTestService;
    }


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


    @GetMapping("/test/current-user")
    public String currentUser(HttpServletRequest request){
        // 从当前请求中取出拦截器保存的 userId
        String userId=(String) request.getAttribute("userId");
        return "当前登录用户ID："+userId;
    }

    @GetMapping("/test/my-profile")
    public Result<UserProfileResponse> myProfile(HttpServletRequest request) {

        // 从拦截器拿到用户 ID
        String userId = (String) request.getAttribute("userId");

        // String → Long
        Long id = Long.valueOf(userId);

        // 根据 ID 查询数据库
//        UserEntity user=userService.getUserById(id);
//        UserEntity user=profileTestService.getCurrentUser(id);
        UserProfileResponse response=profileTestService.getCurrentUser(id);
//        UserEntity
        //   ↓ 转换
        //UserProfileResponse
        //        对象转换 / 数据转换
//        UserProfileResponse response= new UserProfileResponse( 这部分代码都放到service中去了
//                user.getId(),
//                user.getName(),
//                user.getAge()
//        );
        return new Result<>(
                200,
                "查询成功",
                response
        );
    }
}
//前端登录成功后保存 Token，后续请求一般通过 HTTP 请求头 Authorization 携带 Token，后端验证 Token 后识别当前用户。