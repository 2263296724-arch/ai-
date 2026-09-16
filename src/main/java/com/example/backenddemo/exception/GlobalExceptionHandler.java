package com.example.backenddemo.exception;


import com.example.backenddemo.Result;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  //@RestControllerAdvice 专门负责处理 Controller 中发生的异常。
public class GlobalExceptionHandler {
//    专门处理@Valid参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ExceptionHandler(MethodArgumentNotValidException.class) 意思是 如果出现“参数校验异常”，就交给这个方法处理。
    public Result<Void> handleValidationException(MethodArgumentNotValidException e){
        String message= e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();  //getDefaultMessage() 可以拿到@Size(min = 6, message = "密码长度不能少于6位")中的密码长度不能少于6位
        return new Result<>( //统一返回 Result<T> 是为了让项目中不同接口保持统一的返回格式，前端可以按照统一的方式处理成功和失败结果。
                400,
                message,
                null

        );
    }/*
    前端传 JSON
        ↓
    @RequestBody
        ↓
    RegisterRequest
        ↓
    @Valid
        ↓
    @NotBlank / @Size
        ↓
    校验失败
        ↓
    MethodArgumentNotValidException
        ↓
    @RestControllerAdvice
        ↓
    @ExceptionHandler
        ↓
    Result<T>
        ↓
    统一 JSON 返回
*/

    @ExceptionHandler(JwtException.class)
    public Result<Void> handleJwtException(JwtException e){
        return new Result<>(
                401,
                "登录已失效，请重新登录",
                null
        );
    }
    // 处理普通 RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public Result<Void> handleRuntimeException(RuntimeException e) {

        return new Result<>(
                401,
                e.getMessage(),
                null
        );
    }

}
//@RestControllerAdvice 用来统一处理 Controller 层的异常，让接口返回统一、友好的错误信息。
