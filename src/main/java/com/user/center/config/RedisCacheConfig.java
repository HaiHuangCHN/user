package com.user.center.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {


    @Value("${cache.default.expire-time}")
    private int defaultExpireTime;

//	/**
//	 * bean definition of key generation strategypackage name + class name + method name + all arguments
//	 */
//	@Bean(name = "keyGenerator")
//	@Override
//	public KeyGenerator keyGenerator() {
//		return new KeyGenerator() {
//			@Override
//			public Object generate(Object target, Method method, Object... params) {
//				StringBuilder sb = new StringBuilder();
//				sb.append(target.getClass().getName());
//				sb.append(method.getName());
//				for (Object obj : params) {
//					sb.append(obj.toString());
//				}
//				return sb.toString();
//			}
//		};
//	}
//

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // Set default expire time
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofSeconds(defaultExpireTime))
                // If cache's value is NULL, will not be accepted and will return as
                // exception etc.
                .disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(defaultCacheConfig).build();
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        stringRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // Enable transaction
        stringRedisTemplate.setEnableTransactionSupport(true);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Use Jackson2JsonRedisSerializer to serialize & deserialize value
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerializer.setObjectMapper(om);
        redisTemplate.setValueSerializer(jacksonSerializer);
        // Enable transaction
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}