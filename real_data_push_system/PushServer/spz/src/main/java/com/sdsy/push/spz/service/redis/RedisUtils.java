package com.sdsy.push.spz.service.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisUtils {

	private static RedisUtils redisUtils = null;
	
	private static JedisPool pool = null;
	
	static {
		
		RedisConfig config = new RedisConfig();
		JedisPoolConfig jpc = new JedisPoolConfig();
		jpc.setMaxTotal(config.getMaxTotal());
		jpc.setMaxIdle(config.getMaxIdle());
		jpc.setTestOnBorrow(config.isTestOnBorrow());
		jpc.setTestOnReturn(config.isTestOnReturn());
		if(StringUtils.isBlank(config.getPassword())) {
			pool = new JedisPool(jpc,config.getHost(),config.getPort(),config.getTimeout());
		} else {
			pool = new JedisPool(jpc,config.getHost(),config.getPort(),config.getTimeout(),config.getPassword());
		}
		redisUtils = new RedisUtils();
		
	}
	
	public static RedisUtils getRedisUtils() {
		return redisUtils;
	}
	
	private static Jedis getRedisConnection() {
		return pool.getResource();
	}
	
	public void publish(String channel,String message) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getRedisConnection();
			jedis.publish(channel, message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void subscribe(RedisListener redisListener,String channel) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getRedisConnection();
			jedis.subscribe(redisListener, channel);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	public void subscribe(RedisBoardCastListener redisListener,String channel) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getRedisConnection();
			jedis.subscribe(redisListener, channel);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	
}
