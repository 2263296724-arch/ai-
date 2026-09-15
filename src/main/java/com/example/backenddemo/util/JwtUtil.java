package com.example.backenddemo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class JwtUtil {
//    秘钥
    private static final String SECRET =
        "backend-demo-jwt-secret-key-1234567890";
//    生成JWT
    public static String createToken(Long userId){  //意思是 传入用户 ID，生成一个 Token。
        SecretKey key = Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder()
                .subject(String.valueOf(userId))  //把用户 ID 放进 JWT 里面。
                .signWith(key)  //用密钥给 JWT 签名，防止 Token 被随便篡改。
                .compact();
    }


    public static String getUser(String token){ //传入 Token，解析出里面的 userId。
        SecretKey key = Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
        return Jwts.parser() //验证 JWT，然后读取里面的数据。
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();


    }
}
