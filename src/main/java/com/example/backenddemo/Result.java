package com.example.backenddemo;

public record Result<T>(  //Result 就是一个“统一返回结果的盒子”。 T 它表示 data 里面可以放不同类型的数据。
        Integer code,
        String message,
        T data){
}

/*
    统一响应结果
        以后你在公司项目里看到：
        Result<T>
        ApiResponse<T>
        CommonResult<T>
        大概率都是在干类似的事情：
        把后端接口的返回格式统一起来。
*/

/*
    这里的 T 可以简单理解成：
     data 里面到底装什么，由使用它的时候决定。
     Result<User> T=User 所以data里面装的是User
     以后如果是商品：Result<Product> 那么T=Product
     如果是订单 Result<Order> 那么T=Order
     可以把它理解成：
     Result<T>
       ↓
    统一外壳
       ↓
    T = 具体数据类型
    这就是 Java 里的泛型。
*/
