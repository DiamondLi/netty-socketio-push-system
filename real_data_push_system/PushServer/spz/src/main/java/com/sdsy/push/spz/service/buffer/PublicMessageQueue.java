package com.sdsy.push.spz.service.buffer;

/**
 *  @author yang.deng
 *  @version v2.0
 *  @since 2018/2/5
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.alibaba.fastjson.JSONArray;

public class PublicMessageQueue implements BufferPool {

	private JSONArray jsonArray = null;
	
	private Lock lock = new ReentrantLock();

	private Condition notEmpty = lock.newCondition();

	public PublicMessageQueue() {
		jsonArray = new JSONArray();
	}
	
	@Override
	public void put(String message) throws InterruptedException {
		lock.lock();
		try {
			jsonArray.add(message);
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public String take() throws InterruptedException {
		lock.lock();
		String result = null;
		try {
			/** 
			 * 必须用while，防止虚假唤醒 
			 */
			while (jsonArray.isEmpty()) {
				notEmpty.await();
			}
//			JSONArray result = (JSONArray) jsonArray.clone();
			result = jsonArray.toJSONString();
			/** 
			 * 一次性清空JSONArray 
			 */
			jsonArray.clear();
		} finally {
			lock.unlock();
		}
		return result;
	}
}
