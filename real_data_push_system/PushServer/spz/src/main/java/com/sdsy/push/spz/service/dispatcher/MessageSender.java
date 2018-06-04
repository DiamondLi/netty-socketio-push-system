package com.sdsy.push.spz.service.dispatcher;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  @author yang.deng
 *  @version v2.0
 *  @since 2018/2/5
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.constant.Const;
import com.sdsy.push.spz.service.buffer.PublicMessageQueue;

public class MessageSender extends Thread {

	private static Logger logger = LoggerFactory.getLogger(MessageSender.class);

	private String eventName;
	
	// servname
	private String servName;
	
	/** 每一个消息分发器维护自己的客户列表  */
	private ConcurrentHashMap<UUID,SocketIOClient> clients;
		
	/** 每一个消息分发器维护自己的消息池 */
	private PublicMessageQueue messageQueue;
	
	public PublicMessageQueue getMessageQueue() {
		return messageQueue;
	}

	public void setMessageQueue(PublicMessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}

	public MessageSender() {
		clients = new ConcurrentHashMap<UUID,SocketIOClient>();
	}
	
	public void send(String message) {
		for(SocketIOClient client : clients.values()) {
			client.sendEvent(eventName,message);
		}
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
		eventName = servName + "_" + Const.PUBLIC_ATTR;
	}

	public void run() {
		while(true) {
			try {
				/** 从自己消息队列中取得String
				 *  通过 SocketIOClient发送出去
				 */
				send(messageQueue.take());
			} catch (Exception e) {
				logger.error("Failed to send message",e);
			}
		}
	}

	public void register(SocketIOClient client) {
		clients.put(client.getSessionId(),client);
	}
	
	public void remove(SocketIOClient client) {
		clients.remove(client.getSessionId());
	}
}
