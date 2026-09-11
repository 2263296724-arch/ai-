package com.example.backenddemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.mapper.UserMapper;
import com.example.backenddemo.request.UpdateUserQuest;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@Service 是干什么的？
简单理解成：
告诉 Spring：UserService 是一个需要由 Spring 管理的对象。

项目启动的时候，Spring 会创建一个 UserService 对象。
然后看到：
public TestController(UserService userService)

Spring 发现：
TestController 需要一个 UserService。

于是自动把刚才创建的 UserService 传进来。
*/
@Service
public class UserService {
    private  final UserMapper userMapper;
    public UserService(UserMapper userMapper){
        this.userMapper=userMapper;
    }
//    public UserEntity updateUser(Long id, UpdateUserQuest request){
//        UserEntity user=new UserEntity();
//
//        user.setId(id);
//        user.setName(request.getName());
//        user.setAge(request.getAge());
//        userMapper.updateById(user);
//
//        return userMapper.selectById(id);
//    }

    public UserEntity updateUser(Long id,UpdateUserQuest request){
        UserEntity user= userMapper.selectById(id);
        if (user==null){
            throw new RuntimeException("用户不存在");
        }
        user.setName(request.getName());
        user.setAge(request.getAge());
        userMapper.updateById(user);
        return user;

    }


    public List<UserEntity> getAllUsers(){
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.gt("age",18);  //gt 可以理解成: greater than，大于
        return userMapper.selectList(wrapper);
    }
    public List<UserEntity> getUsersAgeLessOrEqual30(){
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.le("age",30);
        return  userMapper.selectList(wrapper);
    }
/*
        gt  → >
        ge  → >=

        lt  → <
        le  → <=

        eq  → =
        ne  → !=
*/
}
/*
项目启动
   ↓
发现 @Service
   ↓
Spring 创建 UserService 对象
   ↓
发现 TestController
   ↓
发现它需要 UserService
   ↓
Spring 自动传进去
   ↓
this.userService = userService
这就叫 依赖注入（Dependency Injection，DI）
*/