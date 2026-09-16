package com.example.backenddemo.response;

public record UserProfileResponse(Long id,String name,Integer age) { //专门规定“个人信息接口要返回什么”

}
//UserEntity 通常对应数据库表，包含数据库中的多个字段，有些字段不应该暴露给前端，所以一般使用 Response DTO，只返回前端需要的数据。

//Entity → 数据库
//DTO → 前端