package com.sdsy.push.spz.service.buffer;

import java.util.ArrayList;

public class PublicMessageQueuePool {

	private ArrayList<PublicMessageQueue> messageQueues;
	
	private int size = 0;
	
	public PublicMessageQueuePool(int size) {
		this.size = size;
		messageQueues = new ArrayList<PublicMessageQueue>(size);
		for(int i = 0; i < size; i++) {
			messageQueues.add(new PublicMessageQueue());
		}
	}
	
	public PublicMessageQueue getElement(int index) {
		return messageQueues.get(index);
	}
	
	public ArrayList<PublicMessageQueue> getElements() {
		return messageQueues;
	}
	
	public int size() {
		return size;
	}
}
