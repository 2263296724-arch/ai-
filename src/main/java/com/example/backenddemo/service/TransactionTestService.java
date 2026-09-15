package com.example.backenddemo.service;

import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  //让 Spring 管理这个 Service。
public class TransactionTestService {

    private final UserMapper userMapper;
    //        Spring 自动把 UserMapper 注入进来
    public TransactionTestService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // @Transactional：让这个方法里的数据库操作成为一个事务
    @Transactional  //@Transactional 开启事务。
    public void testTransaction(){  //这个方法里面的数据库操作会参与这个事务。

        // 这里以后放数据库操作
        System.out.println("第一步");
        // 第一步：查询用户1
        UserEntity user = userMapper.selectById(1L);

        // 修改年龄
        user.setAge(99);

        // 保存修改
        userMapper.updateById(user);

        // 这里以后放数据库操作
        System.out.println("第二步");
        throw new RuntimeException("故意制造异常");
    }
}
//事务不是“失败的那一步回滚”，而是“整个事务一起回滚”。
//事务会回滚事务范围内已经执行的数据库修改操作，而不是把查询操作和 Java 对象操作“回滚”。