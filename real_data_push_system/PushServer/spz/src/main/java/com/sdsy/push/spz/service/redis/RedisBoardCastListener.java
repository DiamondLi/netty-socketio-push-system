package com.sdsy.push.spz.service.redis;

import com.sdsy.push.spz.service.buffer.PublicMessageQueue;
import com.sdsy.push.spz.service.buffer.PublicMessageQueuePool;
import redis.clients.jedis.JedisPubSub;

public class RedisBoardCastListener extends JedisPubSub {

	private String channel;
	
	private PublicMessageQueuePool pool;
	
	public RedisBoardCastListener(String channel,PublicMessageQueuePool pool) {
		this.channel = channel;
		this.pool = pool;
	}
	
	@Override
	public void onMessage(String channel,String message) {
		System.out.println("channel is " + channel + " and message is : " + message);
		// 如果频道不对,不做任何事
		if(!channel.equals(this.channel)) {
			return;
		}
		// 拿到消息往广播消息池里面放
		try {
			for(PublicMessageQueue mq : pool.getElements()) {
				mq.put(message);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
