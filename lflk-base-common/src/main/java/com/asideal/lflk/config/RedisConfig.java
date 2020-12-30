package com.asideal.lflk.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 引入redis配置，用于字典缓存和常用查询中的二级缓存
 * @author ZhangJie
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory, Jackson2JsonRedisSerializer<Object> redisJsonSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //redis连接工厂
        template.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //redis.key序列化器
        template.setKeySerializer(stringRedisSerializer);
        //redis.value序列化器
        template.setValueSerializer(redisJsonSerializer);
        //redis.hash.key序列化器
        template.setHashKeySerializer(stringRedisSerializer);
        //redis.hash.value序列化器
        template.setHashValueSerializer(redisJsonSerializer);
        //调用其他初始化逻辑
        template.afterPropertiesSet();
        //这里设置redis事务一致
        template.setEnableTransactionSupport(true);
        return template;
    }

    @Bean
    public Jackson2JsonRedisSerializer<Object> redisJsonSerializer() {
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();

        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }
}
