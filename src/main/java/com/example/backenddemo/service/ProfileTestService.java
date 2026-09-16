package com.example.backenddemo.service;


import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.mapper.UserMapper;
import com.example.backenddemo.response.UserProfileResponse;
import org.springframework.stereotype.Service;

@Service
public class ProfileTestService {
    private final UserMapper userMapper;

    public ProfileTestService(UserMapper userMapper){
        this.userMapper=userMapper;
    }


    public UserProfileResponse getCurrentUser(Long id){

        UserEntity user = userMapper.selectById(id);

        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getAge()
        ) ;
    }




}
