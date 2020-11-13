package com.asideal.lflk.token;

import com.asideal.lflk.login.vo.UserVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtTokenUtils implements InitializingBean {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORITIES_KEY = "auth";

    private static final String SECRET = "QXNpZGVhbDkwOTA=";
    private static final String ISS = "asideal";

    private static Key key;

    @Override
    public void afterPropertiesSet() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 604800L;


    // 创建token
    public static String createToken(String username) {
        return Jwts.builder()
                //.signWith(SignatureAlgorithm.HS512, SECRET)
                .signWith(key,SignatureAlgorithm.HS512)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        HashMap map =(HashMap) claims.get("auth");

        UserVo principal = new UserVo(map.get("user").toString(), map.get("password").toString(), authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public static boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
            // TODO: 改造这里变成统一返回数据
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 是否已过期
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

