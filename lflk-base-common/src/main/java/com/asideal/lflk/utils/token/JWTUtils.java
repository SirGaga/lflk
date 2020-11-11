package com.asideal.lflk.utils.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;
import java.util.function.ToDoubleBiFunction;

/**
 * JWT工具类
 *
 * @author ZhangJie
 * @since 2020-11-12
 */
public class JWTUtils {

    public static final String TOKEN = "tokenASIDEAL9090";

    public static final int EXPIRE = 7;

    /**
     * 生成token
     * @param map 前端传递过来的非关键数据信息比如用户id和用户名
     * @return 返回验证问题
     * @throws IllegalArgumentException 参数异常
     * @throws JWTCreationException JWT创建异常
     */
    public static String getToke(Map<String,String> map) throws IllegalArgumentException, JWTCreationException {
        JWTCreator.Builder builder = JWT.create();
        map.forEach((key,value) -> {
            builder.withClaim(key,value);
        });
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,EXPIRE);
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(TOKEN));
    }

    /**
     * 验证token
     * @param token 前端传送过来的token
     * @return 返回验证后返回的结果
     * @see com.auth0.jwt.interfaces.DecodedJWT 可以参考这个类的数据实体结果拿到想要的token，header，payload，算法签名
     */
    public static DecodedJWT verify(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
