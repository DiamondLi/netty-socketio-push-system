package com.sdsy.push.spz.service.dispatcher;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.service.buffer.ChatMessageQueue;
import com.sdsy.push.spz.service.buffer.PrivateMessageQueue;
import com.sdsy.push.spz.service.buffer.PublicMessageQueuePool;

public class Dispatcher {

	/** 维护客户map的索引 */
	private ConcurrentHashMap<UUID,MessageSender> indexMap = new ConcurrentHashMap<UUID,MessageSender>();
		
	private Long index = 0L;
	
	private MessageSenderPool messageSenderPool = null;
	
	private MessageSenderP2P msgSendP2P= null;
	
	private MessageSenderChat msgSendChat = null;
		
	private ArrayList<Thread> threads = null;
	
	private Thread threadP2P = null;
	
	private Thread threadChat = null;
	
	private PublicMessageQueuePool publicMessageQueuePool = null;
	
	private PrivateMessageQueue privateMessageQueue = null;
	
	private ChatMessageQueue chatMessageQueue = null;
	
	private int threadNumber;
	
	private String servName;
	
	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
	}

	public PublicMessageQueuePool getPublicMessageQueuePool() {
		return publicMessageQueuePool;
	}

	public void setPublicMessageQueuePool(PublicMessageQueuePool publicMessageQueuePool) {
		this.publicMessageQueuePool = publicMessageQueuePool;
	}

	public PrivateMessageQueue getPrivateMessageQueue() {
		return privateMessageQueue;
	}

	public void setPrivateMessageQueue(PrivateMessageQueue privateMessageQueue) {
		this.privateMessageQueue = privateMessageQueue;
	}

	public ChatMessageQueue getChatMessageQueue() {
		return chatMessageQueue;
	}

	public void setChatMessageQueue(ChatMessageQueue chatMessageQueue) {
		this.chatMessageQueue = chatMessageQueue;
	}

	public void initialize() {
		/** 需要点对点消息发送器 */
		if(privateMessageQueue != null) {
			msgSendP2P = new MessageSenderP2P();
			msgSendP2P.setPrivateMessageQueue(privateMessageQueue);
			msgSendP2P.setServName(servName);
			threadP2P = new Thread(msgSendP2P);
		}
		/** 需要广播消息发送器 */
		if(publicMessageQueuePool != null) {
			threadNumber = publicMessageQueuePool.size();
			messageSenderPool = new MessageSenderPool(); 
			threads = new ArrayList<Thread>(threadNumber);
			for(int i=0;i<threadNumber;++i) {
				MessageSender ms = new MessageSender();
				ms.setMessageQueue(publicMessageQueuePool.getElement(i));
				ms.setServName(servName);
				messageSenderPool.add(ms);
				threads.add(new Thread(ms));
			}
		}
		/** 需要聊天消息发送器 */
		if(chatMessageQueue != null) {
			msgSendChat = new MessageSenderChat();
			msgSendChat.setChatMessageQueue(chatMessageQueue);
			msgSendChat.setServName(servName);
			threadChat = new Thread(msgSendChat);
		}
	}
	
	public void register(SocketIOClient client,String uuid) {	
		/** 
		 *  直接采用round-robin插入
		 */
		if(publicMessageQueuePool != null) {
			long idx = 0;
			synchronized (index) {
				idx = (index++ % threadNumber);
			}
			indexMap.put(client.getSessionId(), messageSenderPool.getElement((int) idx));
			messageSenderPool.getElement((int)idx).register(client);
		}
		if(privateMessageQueue != null) {
			msgSendP2P.register(client,uuid);
		}
		if(chatMessageQueue != null) {
			msgSendChat.register(client, uuid);
		}
	}
	
	public void remove(SocketIOClient client) {
		/** 
		 * 获得client在哪个MessageSend中 
		 */
		if(publicMessageQueuePool != null) {
			MessageSender messageSender = indexMap.get(client.getSessionId());
			if(messageSender != null) {
				messageSender.remove(client);
				indexMap.remove(client.getSessionId());
			}
		}
		/**
		 * 删除点对点通信中的词条
		 */
		if(privateMessageQueue != null) {
			msgSendP2P.remove(client);
		}
		/**
		 * 删除聊天中的client
		 */
		if(chatMessageQueue != null) {
			msgSendChat.remove(client);
		}
	}
		
	public void start() {
		/**  
		 * 依次启动广播线程 
		 */
		if(threads != null && threads.size() != 0) {
			for(Thread thread : threads) {
				thread.start();
			}
		}
		/**
		 * 启动点对点消息线程
		 */
		if(threadP2P != null) {
			threadP2P.start();
		}
		/**
		 * 启动聊天消息线程
		 */
		if(threadChat != null) {
			threadChat.start();
		}
	}
	
}
