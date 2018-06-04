package com.sdsy.push.spz.service.buffer;

import java.util.concurrent.LinkedBlockingQueue;

public class ChatMessageQueue implements BufferPool {

	private LinkedBlockingQueue<String> lbq;
	
	public ChatMessageQueue() {
		lbq = new LinkedBlockingQueue<String>();
	}
	
	@Override
	public String take() throws InterruptedException {
		return lbq.take();
	}

	@Override
	public void put(String message) throws InterruptedException {
		lbq.put(message);
	}

}
