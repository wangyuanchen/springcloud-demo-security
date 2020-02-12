package com.zhao.cloud.gateway.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author zhaoliang
 */
public class JwtTokenUtil {
    /**
     * 过期时间(毫秒)：5天
     */
    private static final long EXPIRATION_TIME = 5 * 24 * 60 * 60 * 1000;
    /**
     * JWT密码
     */
    private static final String SECRET = "U2xkVWMyVmpjbVYw";

    /**
     * 签发JWT
     */
    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(16);
        claims.put(Claims.SUBJECT, userDetails.getUsername());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(Instant.now().toEpochMilli() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 验证JWT
     */
    public static Boolean validateToken(String token) {
        //TODO 此处的验证只是样例，具体逻辑需要业务侧决定
        return (StringUtils.isNotBlank(getUsernameFromToken(token)) && !isTokenExpired(token));
    }

    /**
     * 获取token是否过期
     */
    private static Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 根据token获取username
     */
    private static String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * 获取token的过期时间
     */
    private static Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * 解析JWT
     */
    private static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}