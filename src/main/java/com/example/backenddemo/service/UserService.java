package com.example.backenddemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backenddemo.User;
import com.example.backenddemo.entity.UserEntity;
import com.example.backenddemo.mapper.UserMapper;
import com.example.backenddemo.request.UpdateUserQuest;
import com.example.backenddemo.request.UserQueryRequest;
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
//    and
    public List<UserEntity> getUserAgeAndName(){
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>();
        wrapper.gt("age",18);
        wrapper.eq("name","王五");  //类似于 WHERE age > 18 AND name = '王五'

        return userMapper.selectList(wrapper);
    }
//    or
    public List<UserEntity> getUserByAgeOrName(){
        QueryWrapper<UserEntity> wrapper=new QueryWrapper<>(); //默认 AND

        wrapper.gt("age",40);
        wrapper.or();    // 类似于 WHERE age > 40 OR name = '王五'
        wrapper.eq("name","王五");
        return userMapper.selectList(wrapper);
    }

    public List<UserEntity> queryUsers(UserQueryRequest request) {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();

        if (request.getAge()!=null){
            wrapper.gt("age", request.getAge());
        }
        if (request.getName()!=null){
            wrapper.eq("name", request.getName());
        }


        return userMapper.selectList(wrapper);
    }


    public List<UserEntity> searchUserByName(String name){
        QueryWrapper<UserEntity> wrapper =new QueryWrapper<>();
        wrapper.like("name",name);   //like() 是模糊查询  相当于 WHERE name LIKE '%王%'
        return userMapper.selectList(wrapper);
    }

    public  List<UserEntity> searchUserStartWith(String name){
        QueryWrapper<UserEntity> wrapper= new QueryWrapper<>();
        wrapper.likeRight("name",name);
        return userMapper.selectList(wrapper);  //likeRight 以王结尾 LIKE '王%'
    }

/*
        like("王")       → %王% → 包含王
        likeLeft("王")   → %王  → 以王结尾
        likeRight("王")  → 王%  → 以王开头
* */
    public List<UserEntity> searchUsersByAgeRange(Integer minAge,Integer maxAge){
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.between("age",minAge,maxAge);  //相当于：WHERE age BETWEEN 20 AND 30 而且 两边都包含。左右闭合
        return userMapper.selectList(wrapper);
    }


    public List<UserEntity> searchUsersByIds(List<Long> ids){
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.in("id",ids);  //in()：一次匹配多个值  相当于WHERE id IN (1, 3, 5) 从多个值里面匹配，只要符合其中一个就可以。
        return userMapper.selectList(wrapper);
    }


    public List<UserEntity> searchUsersExcludeIds(List<Long> ids){
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.notIn("id",ids); //notIn()：排除多个值 相当于 WHERE id NOT IN (1, 3, 5) 查询所有用户，但是排除 ID 为 1、3、5 的用户。
        return userMapper.selectList(wrapper);
    }


    public List<UserEntity> searchUsersOrderByAge(){
        QueryWrapper<UserEntity> wrapper= new QueryWrapper<>();
        wrapper.orderByAsc("age"); //排序 类似于 ORDER BY age ASC 升序
        return userMapper.selectList(wrapper);
    }

    public List<UserEntity> searchUsersOrderByAgeDesc(){
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("age");//降序
        return userMapper.selectList(wrapper);
    }

    public List<UserEntity> searchUserAgeNot18(){
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();
        wrapper.ne("age",18);//  ne() 不等于 相当于WHERE age <> 18
        return userMapper.selectList(wrapper);
    }

    public List<UserEntity> searchUsersAgeIsNull() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();

        // 查询年龄为空的用户
        wrapper.isNull("age"); //WHERE age IS NULL

        return userMapper.selectList(wrapper);
    }


    public List<UserEntity> searchUsersAgeIsNotNull() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();

        // 查询年龄为不空的用户
        wrapper.isNotNull("age"); //WHERE age IS  NOT NULL

        return userMapper.selectList(wrapper);
    }


    public List<UserEntity> searchUsersComplex() {
        QueryWrapper<UserEntity> wrapper = new QueryWrapper<>();

        // 年龄大于20
        wrapper.gt("age", 20);

        // and()：表示后面的条件整体作为一个 AND 条件
        //
        // w -> w.eq(...).or().eq(...)
        // 这是 Java 的 Lambda 表达式：
        // w：表示传进来的 Wrapper 对象
        // ->：表示“使用这个对象做下面的操作”
        //
        // 最终相当于：
        // AND (name = '王五' OR name = '李四')
        wrapper.and(w ->
                w.eq("name", "王五")
                        .or()
                        .eq("name", "李四")
        );

        // 最终 SQL：
        // WHERE age > 20
        // AND (name = '王五' OR name = '李四')

        return userMapper.selectList(wrapper);
    }
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