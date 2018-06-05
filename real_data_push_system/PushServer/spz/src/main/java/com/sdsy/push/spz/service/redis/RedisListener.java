package com.sdsy.push.spz.service.redis;

import com.sdsy.push.spz.service.buffer.BufferPool;

import redis.clients.jedis.JedisPubSub;

public class RedisListener extends JedisPubSub {

	private String channel;
	
	private BufferPool bufferPool;
	
	public RedisListener() {}
	
	public RedisListener(String channel,BufferPool bufferPool) {
		this.channel = channel;
		this.bufferPool = bufferPool;
	}
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Override
	public void onMessage(String channel, String message) {
		System.out.println("channel is " + channel + " and message is : " + message);
		// 如果频道不对,不做任何事
		if(!channel.equals(this.channel)) {
			return;
		}
		// 拿到消息放到相应的缓存池中去
		try {
			bufferPool.put(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
