package com.sdsy.push.spz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version v2.0
 * @since 2018/2/6
 */

import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.base.Config;
import com.sdsy.push.spz.base.ServConfig;

public class ServCentral {

	private static Logger logger = LoggerFactory.getLogger(ServCentral.class);
	
	/**
	 *  服务池
	 */
	private ServPool servPool = null;
	
	/**
	 *  配置信息
	 */
	private Config config;
	
	/**
	 *  聊天容器
	 */
	//private ChatMessageQueue chatMessageQueue = null;
	
	/**
	 *  聊天事件处理器
	 *  聊天事件必须尽早返回，因为聊天的并发度要比注册大，注册
	 *  事件具体在某个服务中处理，但是聊天不行
	 */
	//private ChatEventHandle chatEventHandle = null;
	
	/**
	 *  聊天线程
	 */
	//private Thread chatThread = null;
	
	public ServCentral(Config config) {
		this.config = config;
		initialize();
	}
	
	private void initialize() {
		
//		/**
//		 *  初始化聊天容器和第一步解耦线程
//		 */
//		chatMessageQueue = new ChatMessageQueue();
//		chatEventHandle = new ChatEventHandle();
//		chatEventHandle.setBufferPool(chatMessageQueue);
//		chatThread = new Thread(chatEventHandle);
	
		/**
		 *  服务池初始化
		 */
		servPool = new ServPool();
		for(ServConfig servConfig : config.getServConfigs()) {
			servPool.put(servConfig.getServName(), new Service(servConfig));
		}
	}

	/**
	 *  注册服务
	 */
	public void register(String data,SocketIOClient client) {
		servPool.register(data, client);
	}
	
	/**
	 *  销毁
	 */
	public void remove(SocketIOClient client) {
		servPool.remove(client);
	}
	
	/**
	 * 启动
	 */
	public void start() {
		// 服务池启动
		servPool.start();
		// 聊天事件处理器启动
		//chatThread.start();
	}
	
	/**
	 *  异步处理聊天,减轻并发负担,并且把聊天集群化
	 */
//	public void chat(String data) {
//		try {
//			chatMessageQueue.put(data);
//		} catch (Exception e) {
//			logger.error("聊天总线程处理失败 : {}",e);
//		}
//	}
}
