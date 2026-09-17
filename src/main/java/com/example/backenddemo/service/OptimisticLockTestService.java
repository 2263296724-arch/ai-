package com.example.backenddemo.service;


import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockTestService {

    private final UserMapper userMapper;

    public OptimisticLockTestService(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    public void testOptimisticLock(){
//        先查询用户
        UserEntity user=userMapper.selectById(1L);

//        修改年龄
        user.setAge(20);
//        更新用户
        userMapper.updateById(user);
    }
}
