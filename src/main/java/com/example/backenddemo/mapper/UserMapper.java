package com.example.backenddemo.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backenddemo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    //extends BaseMapper<UserEntity> 它相当于告诉 MyBatis-Plus： 我要操作的是 User 这张表，帮我提供常用的数据库操作。
//    所以以后可以直接 userMapper.selectById(1L); 查询 id = 1 的用户。
}
