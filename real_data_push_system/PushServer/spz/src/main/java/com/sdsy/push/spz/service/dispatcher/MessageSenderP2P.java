package com.sdsy.push.spz.service.dispatcher;

import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.constant.Const;
import com.sdsy.push.spz.service.buffer.PrivateMessageQueue;

import io.netty.util.internal.StringUtil;

public class MessageSenderP2P extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(MessageSenderP2P.class);
	
	private String eventName;
	
	private String servName;
	
	private PrivateMessageQueue privateMessageQueue;

	/** client作key，push code作value **/
	private ConcurrentHashMap<SocketIOClient, String> clientAsKeyMap = new ConcurrentHashMap<SocketIOClient, String>();

	/** uuid作key，client作value **/
	private ConcurrentHashMap<String, SocketIOClient> uuidAsKeyMap = new ConcurrentHashMap<String, SocketIOClient>();
	
	public PrivateMessageQueue getPrivateMessageQueue() {
		return privateMessageQueue;
	}

	public void setPrivateMessageQueue(PrivateMessageQueue privateMessageQueue) {
		this.privateMessageQueue = privateMessageQueue;
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
			String uuid = null;
			try {
				String message = privateMessageQueue.take();
				JSONObject json = (JSONObject) JSON.parse(message);
				uuid = json.getString(Const.UUID);
				/**
				 * 如果在推送码map中存在该推送码，就向该推送码的客户发送消息
				 */
				if (uuidAsKeyMap.containsKey(uuid)) {
					uuidAsKeyMap.get(uuid).sendEvent(eventName, message);
				}
			} catch (Exception e) {
				logger.error("私人消息发送失败 uuid {} {}", uuid, e);
			} 
		}
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
		eventName = servName + "_" + Const.PRIVATE_ATTR;
	}
	
}
