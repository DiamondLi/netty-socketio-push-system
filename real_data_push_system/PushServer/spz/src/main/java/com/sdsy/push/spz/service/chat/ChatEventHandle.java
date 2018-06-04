package com.sdsy.push.spz.service.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.sdsy.push.spz.base.ChatInfo;
import com.sdsy.push.spz.service.buffer.BufferPool;
import com.sdsy.push.spz.service.redis.RedisUtils;

public class ChatEventHandle extends Thread {

	private static Logger logger = LoggerFactory.getLogger(ChatEventHandle.class);
	
	private BufferPool bufferPool;
	
	public BufferPool getBufferPool() {
		return bufferPool;
	}

	public void setBufferPool(BufferPool bufferPool) {
		this.bufferPool = bufferPool;
	}
	
	public void run() {
		while (true) {
			try {
				String data = bufferPool.take();
				ChatInfo chat =JSON.parseObject(data, ChatInfo.class);
				RedisUtils.getRedisUtils().publish(chat.getServName(), data);
			} catch (Exception e) {
				logger.error("处理消息失败 ： {}",e);
			}
		}
	}
	
}
