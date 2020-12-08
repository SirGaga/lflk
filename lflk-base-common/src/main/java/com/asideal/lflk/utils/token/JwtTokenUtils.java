package com.asideal.lflk.utils.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 * @author ZhangJie
 * @since 2020-11-14
 */
@Component
@Log4j2
public class JwtTokenUtils implements InitializingBean {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES_KEY = "auth";

    private static final String SECRET = "123456789ZAQXSWCDEVFRBGTNHYMJUKILOzaqxswcdevfrbgtnhymjukiop";
    private static final String ISS = "asideal";

    private static Key key;

    @Override
    public void afterPropertiesSet() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 604800L;

    /**
     * 创建token
     * @param username 用户名
     * @param claims token中载荷的信息
     * @return 返回jwt形式的token字符串
     */
    public static String createToken(String username, Map<String,Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                //.signWith(SignatureAlgorithm.HS512, SECRET)
                .signWith(key,SignatureAlgorithm.HS256)
                .setIssuer(ISS)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    /**
     * 获取token自定义属性
     * @param token 前端请求header中携带的token
     * @return 返回JWT中自定义的一些属性
     */
    public static Map<String,Object> getClaims(String token){
        Map<String,Object> claims = null;
        try {
            claims = getTokenBody(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    /**
     * 从token中获取用户名
     * @param token 前端请求header中携带的token
     * @return 返回subject，再通过相关方法进行获取更详细的信息
     */
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    /**
     * 验证token是否已经过期
     * @param token 前端请求header中携带的token
     * @return 返回布尔值，true代表已经过期，false代表未过期
     */
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token){
        return Jwts.parserBuilder()
                //.setSigningKey(SECRET)
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}

