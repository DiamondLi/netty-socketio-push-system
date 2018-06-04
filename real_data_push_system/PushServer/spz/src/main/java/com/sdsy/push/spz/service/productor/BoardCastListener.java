package com.sdsy.push.spz.service.productor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sdsy.push.spz.service.buffer.PublicMessageQueuePool;
import com.sdsy.push.spz.service.redis.RedisBoardCastListener;
import com.sdsy.push.spz.service.redis.RedisUtils;

public class BoardCastListener extends Thread {

	private static Logger logger = LoggerFactory.getLogger(BoardCastListener.class);
	
	private String channel;
	
	private RedisBoardCastListener redisBoardCastListener;
	
	private PublicMessageQueuePool pool;
	
	public BoardCastListener(String channel,PublicMessageQueuePool pool) {
		this.channel = channel;
		this.pool = pool;
		intialize();
	}
	
	private void intialize() {
		redisBoardCastListener = new RedisBoardCastListener(channel,pool);
	}
	
	public void run() {
		while(true) {
			try {
				RedisUtils.getRedisUtils().subscribe(redisBoardCastListener, channel);
			} catch (Exception e) {
				logger.error("监听 {}广播消息失败 : {}",channel,e);
			}
		}
	}
	
}
