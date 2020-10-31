package com.yjf.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * spring整合缓存技术：
 * 1.spring data redis整合redis作为缓存
 * 2.spring cache 操作缓存
 * a.声明缓存管理器对象CacheManager -创建缓存对象，自动管理缓存对象
 * b.开启缓存注解支持
 * c.需要放入缓存的对象类型，需要实现序列化接口
 * d.在需要管理缓存的服务层逻辑方法上，添加缓存注解
 */
@Configuration
@EnableCaching
public class SpringCacheConfig {



    //声明bean的名字为redisTemplate
    @Bean("redisTemplate")
    public RedisTemplate<Object,Object> getRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);//设置连接工厂对象
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);//设置key的序列化器，使用String序列化器，默认以UTF-8对key进行编码和解码
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        //设置value的序列化器，该序列化器会自动将对象类型作为其中转换的一个key和value存入json中
        redisTemplate.setValueSerializer(redisSerializer);

        //设置hash类型
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);

        return redisTemplate;
    }


    //创建spring缓存管理器

    /**
     * 1.传入redis链接工厂对象，获取RedisCacheManagerBuilder
     * 2.调用cacheDefaults传入RedisCacheConfiguration对象设置缓存管理器的配置信息
     * @param redisTemplate
     * @return
     */
    @Bean
    public RedisCacheManager getCacheManager(RedisTemplate<Object,Object> redisTemplate){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.
                defaultCacheConfig().
                serializeKeysWith(
                        RedisSerializationContext.
                                SerializationPair.
                                fromSerializer(redisTemplate.getStringSerializer()))//设置key序列化规则
                .serializeValuesWith(RedisSerializationContext.
                        SerializationPair.fromSerializer(redisTemplate.getValueSerializer()))//设置value的序列化规则
                .entryTtl(Duration.ofMinutes(10));//设置生存时间
        RedisCacheManager cacheManager = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                .cacheDefaults(configuration).build();
        return  cacheManager;
    }


}
