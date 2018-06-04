package com.sdsy.push.spz.service.productor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sdsy.push.spz.service.buffer.BufferPool;
import com.sdsy.push.spz.service.redis.RedisListener;
import com.sdsy.push.spz.service.redis.RedisUtils;

public class PersonalListener extends Thread {

	private static Logger logger = LoggerFactory.getLogger(PersonalListener.class);
	
	private String channel;
	
	private RedisListener redisListener;
	
	private BufferPool bufferPool;
	
	public PersonalListener(String channel,BufferPool bufferPool) {
		this.channel = channel;
		this.bufferPool = bufferPool;
		intialize();
	}
	
	private void intialize() {
		redisListener = new RedisListener(channel,bufferPool);
	}
	
	public void run() {
		while(true) {
			try {
				RedisUtils.getRedisUtils().subscribe(redisListener, channel);
			} catch (Exception e) {
				logger.error("监听 {}广播消息失败 : {}",channel,e);
			}
		}
	}
	
}
