package com.example.backenddemo.controller;

import com.example.backenddemo.Result;
import com.example.backenddemo.User;
// import com.example.backenddemo.mapper.UserMapper;
import com.example.backenddemo.request.UpdateUserQuest;
import com.example.backenddemo.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.example.backenddemo.entity.UserEntity;


/**
 * Controller：负责接收前端发送过来的 HTTP 请求。
 *
 * 可以先简单理解成：
 *
 * Controller = 前端和后端之间的“入口”
 *
 * 前端
 *   ↓
 * HTTP 请求
 *   ↓
 * Controller
 *   ↓
 * 业务处理
 *   ↓
 * 返回数据
 */
@RestController
public class TestController {


    // ============================================================
    // ① 最基础的 GET 请求
    // ============================================================

    /**
     * @GetMapping("/hello")
     *
     * 告诉 Spring：
     *
     * 当有人发送：
     *
     * GET /hello
     *
     * 就执行下面的 hello() 方法。
     */
    @GetMapping("/hello")
    public String hello() {

        // String 表示这个方法返回一个字符串
        return "Hello Full Stack";
    }


    // ============================================================
    // ② @PathVariable：获取 URL 中的参数
    // ============================================================

    /**
     * 前端请求：
     *
     * GET /user/100
     *
     * URL：
     * /user/{id}
     *
     * {id} 是一个占位符。
     *
     * @PathVariable 会把 URL 中的 100 取出来，
     * 放到 Java 方法的 id 变量中。
     */
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id) {

        return "用户id：" + id;
    }


    /*
     * 整个过程：
     *
     *     /user/100
     *          ↓
     *     {id} = 100
     *          ↓
     *     @PathVariable
     *          ↓
     *     Java 中 id = 100
     *
     *
     * 记忆：
     *
     * URL 参数
     *     ↓
     * @PathVariable
     */


    // ============================================================
    // ③ @RequestBody：接收前端 JSON
    // ============================================================

    /**
     * 前端发送：
     *
     * POST /user
     *
     * Body：
     *
     * {
     *     "name": "张三",
     *     "age": 25
     * }
     *
     *
     * @RequestBody 的作用：
     *
     * 把请求 Body 中的 JSON
     * 转换成 Java 对象 User。
     */
    @PostMapping("/user")
    public Result<User> createUser(@RequestBody User user) {

        return new Result<>(200, "success", user);
    }


    /*
     * 整个过程：
     *
     *     前端 JSON
     *        ↓
     *   @RequestBody
     *        ↓
     *    User 对象
     *        ↓
     *     return
     *        ↓
     *   Spring Boot
     *        ↓
     *      JSON
     *
     *
     * 前端数据在哪里？       Java 怎么接？
     *
     * /user/100              @PathVariable
     * JSON Body              @RequestBody
     */


    // ============================================================
    // ④ MyBatis-Plus：根据 ID 查询数据库
    // ============================================================

    /*
     * 注意：
     *
     * 这个例子暂时注释掉。
     *
     * 因为现在正在学习 Service 层，
     * 所以先不要让 Controller 直接操作 Mapper。
     *
     *
     * 以前的写法：
     *
     * Controller
     *      ↓
     * UserMapper
     *      ↓
     * MySQL
     *
     *
     * 现在要学习的写法：
     *
     * Controller
     *      ↓
     * UserService
     *      ↓
     * UserMapper
     *      ↓
     * MySQL
     */


    /*
    private final UserMapper userMapper;

    public TestController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/user/db/{id}")
    public UserEntity getUserFromDb(@PathVariable Long id) {

        return userMapper.selectById(id);
    }
    */


    /*
     * 以前的数据库查询过程：
     *
     * GET /user/db/1
     *        ↓
     * @PathVariable
     *        ↓
     * id = 1
     *        ↓
     * userMapper.selectById(1)
     *        ↓
     * MyBatis-Plus
     *        ↓
     * MySQL
     *        ↓
     * 查询到数据
     *        ↓
     * UserEntity
     *        ↓
     * Controller return
     *        ↓
     * Spring Boot 自动序列化
     *        ↓
     * JSON
     *        ↓
     * 浏览器
     *
     *
     * 例如数据库：
     *
     * id = 1
     * name = 张三
     * age = 25
     *
     * MyBatis-Plus 查询后：
     *
     * UserEntity
     *
     * {
     *     id: 1,
     *     name: "张三",
     *     age: 25
     * }
     *
     * Spring Boot 再把 Java 对象转换成 JSON 返回给前端。
     */


    // ============================================================
    // ⑤ MyBatis-Plus：根据 ID 更新数据
    // ============================================================

    /*
     * 这个例子也暂时注释。
     *
     * 以前我们是在 Controller 中直接操作 Mapper：
     *
     * Controller
     *      ↓
     * UserMapper
     *      ↓
     * MySQL
     *
     *
     * 现在改成：
     *
     * Controller
     *      ↓
     * Service
     *      ↓
     * Mapper
     *      ↓
     * MySQL
     */


    /*
    @PutMapping("/user/{id}")
    public UserEntity updateUser(
            @PathVariable Long id,
            @RequestBody UserEntity user
    ) {

        // URL 中的 id 作为真正要修改的用户 ID
        user.setId(id);

        // 根据 user.id 更新数据库
        userMapper.updateById(user);

        // 更新完成以后，再查询一次最新数据
        return userMapper.selectById(id);
    }
    */


    /*
     * updateById(user) 要注意：
     *
     * 它不是：
     *
     * “把整个 Java 对象原封不动覆盖数据库”
     *
     * 而是：
     *
     * 根据 user 里的 ID 找到数据库记录，
     * 再根据字段更新策略生成 UPDATE SQL。
     *
     *
     * 例如：
     *
     * user.id = 1
     * user.name = "王五"
     * user.age = null
     *
     * 默认情况下：
     *
     * name 有值 → 更新 name
     * age 是 null → 默认不更新 age
     *
     * 类似：
     *
     * UPDATE user
     * SET name = '王五'
     * WHERE id = 1;
     */


    // ============================================================
    // ⑥ Service 层：Controller 不直接操作 Mapper
    // ============================================================

    /*
     * 现在进入新的结构：
     *
     * Controller
     *      ↓
     * Service
     *      ↓
     * Mapper
     *      ↓
     * MySQL
     *
     *
     * Controller：
     * 负责接收请求
     *
     * Service：
     * 负责业务逻辑
     *
     * Mapper：
     * 负责操作数据库
     */


    /**
     * UserService：
     *
     * Spring 会自动创建 UserService，
     * 然后把 UserService 注入到 TestController。
     *
     * 这叫：
     *
     * 依赖注入（Dependency Injection，DI）
     */
    private final UserService userService;  //Controller 需要 UserService


    /**
     * 构造方法。
     *
     * 不是我们自己调用。
     *
     * Spring 创建 TestController 的时候，
     * 发现它需要 UserService，
     * 就自动把 UserService 传进来。
     */
    public TestController(UserService userService) { //创建 Controller 时，需要有人把 UserService 传进来

        this.userService = userService; //把传进来的 UserService 保存起来
    }
    //

    /**
     * 测试 Service 层。
     *
     * 前端：
     *
     * POST /test/service
     *
     * Body：
     *
     * {
     *     "name": "赵六",
     *     "age": 30
     * }
     *
     *
     * @RequestBody：
     *
     * JSON
     *   ↓
     * UpdateUserQuest
     *
     *
     * 然后：
     *
     * Controller
     *      ↓
     * UserService
     *      ↓
     * UserMapper
     *      ↓
     * MySQL
     */
    @PostMapping("/test/service")
    public UserEntity testService(
            @RequestBody UpdateUserQuest request
    ) {

        return userService.updateUser(1L, request);
    }


}


/*
 * ============================================================
 *                         总复习
 * ============================================================
 *
 *
 * ① @RestController
 *
 * 告诉 Spring：
 *
 * “这个类是 Controller，
 * 负责接收 HTTP 请求。”
 *
 *
 *
 * ② @GetMapping
 *
 * 例如：
 *
 * @GetMapping("/hello")
 *
 * 表示：
 *
 * GET /hello
 *      ↓
 * 找到 hello()
 *      ↓
 * 执行方法
 *
 *
 *
 * ③ @PathVariable
 *
 * 用来获取 URL 中的参数。
 *
 * /user/100
 *      ↓
 * @PathVariable
 *      ↓
 * id = 100
 *
 *
 *
 * ④ @RequestBody
 *
 * 用来接收前端 JSON。
 *
 * JSON
 *   ↓
 * @RequestBody
 *   ↓
 * Java 对象
 *
 *
 *
 * ⑤ Entity
 *
 * UserEntity：
 *
 * 代表数据库中的数据。
 *
 * 可以理解成：
 *
 * MySQL 一条数据
 *      ↓
 * UserEntity Java 对象
 *
 *
 *
 * ⑥ Mapper
 *
 * UserMapper：
 *
 * 负责操作数据库。
 *
 * 例如：
 *
 * userMapper.selectById(id)
 *
 * userMapper.updateById(user)
 *
 *
 *
 * ⑦ Service
 *
 * UserService：
 *
 * 负责业务逻辑。
 *
 *
 *
 * ⑧ Controller → Service → Mapper → MySQL
 *
 * 这是现在最重要的一条线：
 *
 *
 *        前端
 *          ↓
 *      HTTP 请求
 *          ↓
 *      Controller
 *          ↓
 *       Service
 *          ↓
 *       Mapper
 *          ↓
 *        MySQL
 *
 *
 *
 * 返回的时候：
 *
 *        MySQL
 *          ↓
 *       Mapper
 *          ↓
 *       Service
 *          ↓
 *      Controller
 *          ↓
 *     Java 对象
 *          ↓
 *    Spring Boot 自动转 JSON
 *          ↓
 *        前端
 *
 *
 *
 * ============================================================
 * 最重要的记忆：
 *
 * Controller = 接请求
 *
 * Request = 接收前端数据
 *
 * Service = 处理业务
 *
 * Entity = 表示数据库数据
 *
 * Mapper = 操作数据库
 *
 * MySQL = 存数据
 *
 * ============================================================
 */


/*
 * ============================================================
 * 一个完整请求的例子
 * ============================================================
 *
 * 前端发送：
 *
 * POST /test/service
 *
 * {
 *     "name": "赵六",
 *     "age": 30
 * }
 *
 *             ↓
 *
 * Controller
 * @RequestBody
 *
 *             ↓
 *
 * UpdateUserQuest
 *
 *             ↓
 *
 * UserService
 *
 *             ↓
 *
 * UserEntity
 *
 *             ↓
 *
 * UserMapper
 *
 *             ↓
 *
 * MySQL
 *
 *             ↓
 *
 * 更新数据库
 *
 *             ↓
 *
 * selectById()
 *
 *             ↓
 *
 * UserEntity
 *
 *             ↓
 *
 * Controller
 *
 *             ↓
 *
 * Spring Boot 自动转 JSON
 *
 *             ↓
 *
 * 前端收到结果
 *
 *
 * 这就是我们现在已经学过的几个知识点串起来后的完整流程。
 */