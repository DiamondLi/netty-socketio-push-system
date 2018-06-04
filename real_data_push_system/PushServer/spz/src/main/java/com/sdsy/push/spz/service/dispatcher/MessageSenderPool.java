package com.sdsy.push.spz.service.dispatcher;

import java.util.ArrayList;

public class MessageSenderPool {

	private ArrayList<MessageSender> messageSenders;

	public MessageSenderPool() {
		messageSenders = new ArrayList<MessageSender>();
	}
	
	public MessageSenderPool(int size) {
		messageSenders = new ArrayList<MessageSender>(size);
	}
	
	public MessageSender getElement(int index) {
		return messageSenders.get(index);
	}
	
	public void sendAll(String message) {
		for(MessageSender ms :messageSenders) {
			ms.send(message);
		}
	}
	
	public void add(MessageSender messageSender) {
		messageSenders.add(messageSender);
	}
	
	public void send(String message,int index) {
		this.getElement(index).send(message);
	}
	
	public int size() {
		return messageSenders.size();
	}
}
