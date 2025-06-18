package com.examsystem.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类，负责生成、验证和解析JSON Web Token
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // 从配置文件中注入JWT签名密钥
    @Value("${jwt.secret}")
    private String secret;

    // 从配置文件中注入JWT过期时间（秒）
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 生成JWT令牌
     * 
     * @param userDetails 用户详情
     * @return JWT字符串
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * 实际执行生成JWT的方法
     * 
     * @param claims  声明信息
     * @param subject 主题（通常是用户名）
     * @return JWT字符串
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims) // 设置自定义声明
                .subject(subject) // 设置主题
                .issuedAt(new Date()) // 设置签发时间
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 设置过期时间
                .signWith(getSigningKey()) // 设置签名密钥
                .compact(); // 生成并返回JWT
    }

    /**
     * 获取HMAC-SHA密钥
     * 
     * @return 密钥对象
     */
    private SecretKey getSigningKey() {
        // 将字符串密钥转换为HMAC-SHA密钥
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 验证JWT令牌有效性
     * 
     * @param token       JWT字符串
     * @return 令牌是否有效
     */
    public Boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException ex) {
            logger.warn("JWT令牌已过期: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("不支持的JWT格式: {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("无效的JWT格式: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT声明为空: {}", ex.getMessage());
        }
        return false;
    }

    /**
     * 验证JWT令牌有效性并检查用户信息
     * 
     * @param token       JWT字符串
     * @param userDetails 用户详情
     * @return 令牌是否有效
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && validateToken(token));
    }

    /**
     * 从JWT中获取用户名
     * 
     * @param token JWT字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 解析JWT获取所有声明信息
     * 
     * @param token JWT字符串
     * @return 声明集合
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // 设置验证密钥
                .build() // 构建解析器
                .parseSignedClaims(token) // 解析签名声明
                .getPayload(); // 获取载荷部分
    }

    /**
     * 检查令牌是否已过期
     * 
     * @param token JWT字符串
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从JWT中获取过期时间
     * 
     * @param token JWT字符串
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }
}
