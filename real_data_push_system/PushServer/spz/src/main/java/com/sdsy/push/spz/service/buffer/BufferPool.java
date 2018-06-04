package com.sdsy.push.spz.service.buffer;

public interface BufferPool {

	/**
	 *  take a string from the pool
	 */
	String take() throws InterruptedException;
	
	/**
	 *  put a string into the pool 
	 *  @param message
	 */
	void put(String message) throws InterruptedException;
	
}
