package com.example.backenddemo.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.mapper.UserMapper;
import com.example.backenddemo.response.LoginResponse;
import com.example.backenddemo.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service //告诉 Spring：这是一个 Service，交给 Spring 管理。
public class LoginTestService {

    //构造器注入。
    private final UserMapper userMapper;
    public LoginTestService(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    //根据用户名查询用户
    public UserEntity getUserByName(String name){
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.eq("name",name);
        return userMapper.selectOne(wrapper);
    }


//登录
    public LoginResponse login(String username, String password){

        UserEntity user=getUserByName(username);
        if (user==null){
            throw new RuntimeException("用户不存在");
        }

        if (!password.equals(user.getPassword())){    //user.getPassword().equals(password) 不这样写是因为避免 password 可能为 null 时出现空指针。
            throw new RuntimeException("密码错误");
        };

        String token= JwtUtil.createToken(user.getId());

        return new LoginResponse(token);

//        return token
        /*{结构会变成这个样子
          "code": 200,
          "message": "登录成功",
          "data": "abc123"
}*/
    }

}
