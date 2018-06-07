package com.sdsy.push.spz.service.dispatcher;

/**
 *  @author yang.deng
 *  @version v2.0.0
 */

import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.base.ChatInfo;
import com.sdsy.push.spz.constant.Const;
import com.sdsy.push.spz.service.buffer.ChatMessageQueue;
import io.netty.util.internal.StringUtil;

public class MessageSenderChat extends Thread {

	private static Logger logger = LoggerFactory.getLogger(MessageSenderChat.class);
	
	private String eventName;
	
	private String servName;
	
	private ChatMessageQueue chatMessageQueue;

	/** client作key，push code作value **/
	private ConcurrentHashMap<SocketIOClient, String> clientAsKeyMap = new ConcurrentHashMap<SocketIOClient, String>();

	/** uuid作key，client作value **/
	private ConcurrentHashMap<String, SocketIOClient> uuidAsKeyMap = new ConcurrentHashMap<String, SocketIOClient>();
	
	public ChatMessageQueue getChatMessageQueue() {
		return chatMessageQueue;
	}

	public void setChatMessageQueue(ChatMessageQueue chatMessageQueue) {
		this.chatMessageQueue = chatMessageQueue;
	}

	/**
	 * register和 remove操作是同步的，所以只在register和remove中使用的clientAsKeyMap原生
	 * 就是线程安全的（即使不使用ConcurrentHashMap）
	 */
	public void register(SocketIOClient client,String uuid) {
		uuidAsKeyMap.put(uuid, client);
		clientAsKeyMap.put(client, uuid);
	}

	public void remove(SocketIOClient client) {
		String uuid = clientAsKeyMap.get(client);
		if (StringUtil.isNullOrEmpty(uuid))
			return;
		clientAsKeyMap.remove(client);
		uuidAsKeyMap.remove(uuid);
	}

	public void run() {
		while (true) {
			try {
				String message = chatMessageQueue.take();
				ChatInfo chat = JSON.parseObject(message,ChatInfo.class);
				String to = chat.getTo();
				/**
				 * 如果在推送码map中存在该推送码，就向该推送码的客户发送消息
				 */
				if (uuidAsKeyMap.containsKey(to)) {
					uuidAsKeyMap.get(to).sendEvent(eventName, message);
				}
			} catch (Exception e) {
				logger.error("聊天通信失败  {}", e);
			} 
		}
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
		eventName = servName + Const.CHAT_CHANNEL;
	}
}
