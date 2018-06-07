package com.sdsy.push.spz.service;

/**
 *  @version v2.0
 *  @since 2018/2/6
 */

import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.base.ServConfig;
import com.sdsy.push.spz.constant.Const;
import com.sdsy.push.spz.service.buffer.ChatMessageQueue;
import com.sdsy.push.spz.service.buffer.PrivateMessageQueue;
import com.sdsy.push.spz.service.buffer.PublicMessageQueuePool;
import com.sdsy.push.spz.service.chat.ChatListener;
import com.sdsy.push.spz.service.dispatcher.Dispatcher;
import com.sdsy.push.spz.service.productor.BoardCastListener;
import com.sdsy.push.spz.service.productor.PersonalListener;

public class Service {
	
	/** 点对点消息生产者 */
	private Thread ppp = null;

	/** 广播消息生产者 */
	private Thread bpp = null;
	
	/** 聊天消息生产者 */
	private Thread cpp = null;
	
	/** 连接分发器 */
	private Dispatcher dispatcher;
	
	/**
	 *  点对点消息容器
	 */
	private PrivateMessageQueue privateMessageQueue;
	
	/**
	 *  广播消息容器池，一个线程对应一个消息容器
	 */
	private PublicMessageQueuePool publicMessageQueuePool;
	
	/** 
	 *  聊天消息容器
	 */
	private ChatMessageQueue chatMessageQueue;
	
	/**
	 *  服务配置
	 */
	public Service(ServConfig servConfig) {
		init(servConfig);
	}
	
	/**
	 *  启动服务
	 */
	public void start() {
		/** 启动点对点消息生产者 */
		if(ppp != null) {
			ppp.start();
		}
		/** 启动广播消息生产者 */
		if(bpp != null) {
			bpp.start();
		}
		if(cpp != null) {
			cpp.start();
		}
		/** 启动连接分发器 */
		dispatcher.start();
	}
	
	/**
	 * 销毁
	 */
	public void remove(SocketIOClient client) {
		dispatcher.remove(client);
	}
	
	/** 
	 * 注册
	 */
	public void register(SocketIOClient client,String uuid) {
		dispatcher.register(client, uuid);
	}
	
	/**
	 * 初始化服务
	 * @param servConfig
	 */
	private void init(ServConfig servConfig) {
		
		/** 初始化点对点消息容器和生产者 */
		if(servConfig.isPersonal()) {
			privateMessageQueue = new PrivateMessageQueue();
			PersonalListener pl = new PersonalListener(servConfig.getServName()+Const.PERSONAL_CHANNEL,privateMessageQueue);
			ppp = new Thread(pl);
			ppp.setName(servConfig.getServName() + "-personal-productor");
		}
		
		/** 初始化广播消息容器和生产者 */
		if(servConfig.isBoardcast()) {
			publicMessageQueuePool  = new PublicMessageQueuePool(servConfig.getWorkers());
			BoardCastListener bl = new BoardCastListener(servConfig.getServName()+Const.BOARDCAST_CHANNEL,publicMessageQueuePool);
			bpp = new Thread(bl);
			bpp.setName(servConfig.getServName()+"-boardcast-productor");
		}
		
		/**
		 *  初始化聊天容器
		 */
		if(servConfig.isChat()) {
			chatMessageQueue = new ChatMessageQueue();
			ChatListener cl = new ChatListener(servConfig.getServName()+Const.CHAT_CHANNEL,chatMessageQueue);
			cpp = new Thread(cl);
			cpp.setName(servConfig.getServName()+"-chat-productor");
		}
		
		/** 
		 * 初始化连接分发器 
		 */
		dispatcher = new Dispatcher();
		/** 需要点对点消息 */
		if(servConfig.isPersonal()) {
			dispatcher.setPrivateMessageQueue(privateMessageQueue);
		}
		/** 需要广播消息 */
		if(servConfig.isBoardcast()) {
			dispatcher.setPublicMessageQueuePool(publicMessageQueuePool);
		}
		/** 需要聊天 */
		if(servConfig.isChat()) {
			dispatcher.setChatMessageQueue(chatMessageQueue);
		}
		dispatcher.setServName(servConfig.getServName());
		dispatcher.initialize();
	}
	

}
