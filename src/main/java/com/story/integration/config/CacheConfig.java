package com.story.integration.config;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(10,
				TimeUnit.MINUTES);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}

	/*
	 * Manual redis configuration
	 */
//	@Bean
//	   public  LettuceConnectionFactory redisConnectionFactory() {
//		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();  
//	    return new LettuceConnectionFactory(redisConf);
//	   }
//	@Bean
//	public RedisCacheManager rediscacheManager() {
//		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory()).cacheDefaults(cacheConfiguration()).build();
//		return rcm;
//	}
//	@Bean
//	public RedisCacheConfiguration cacheConfiguration() {
//		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//		  .entryTtl(Duration.ofSeconds(600))
//		  .disableCachingNullValues();	
//		return cacheConfig;
//	   }


}