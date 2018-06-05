package com.sdsy.push.spz.websocket;

/**
 * @version v2.0
 * @since 2018/2/6
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.sdsy.push.spz.base.Config;
import com.sdsy.push.spz.service.ServCentral;

public class Server {

	private static final String REGISTER = "register";
	
	private static final String CHAT = "chat";
	
	private static Logger logger = LoggerFactory.getLogger(Server.class);
	
	private Config config;
	
	/** websocket 服务器 */
	private SocketIOServer server = null;
	
	/** 服务中心 */
	private ServCentral servCentral = null;
	
	public Server(Config config) {
		this.config = config;
		initialize();
	}
	
	private void initialize() {
		/**
		 *  基本配置
		 */
		setup();
		/**
		 *  服务中心配置
		 */
		initServCentral();
	}
	
	private void setup() {
		/** 
		 * 对服务器的配置
		 */
		Configuration configuration = new Configuration();
		configuration.setHostname(config.getWsHostname());
		configuration.setPort(config.getWsPort());
		
		/** 
		 * 如果是linux系统，并且确定使用epoll，就启用Epoll 
		 */
		if(System.getProperty("os.name").toLowerCase().startsWith("linux")
			&& config.isUseEpoll()) {
			configuration.setUseLinuxNativeEpoll(true);
		}
		
		SocketConfig socketConfig = new SocketConfig();
		/**
		 * TCP keep alive
		 */
		socketConfig.setTcpKeepAlive(config.isKeepAlive());
		/**
		 * 禁用Nagle算法
		 */
		socketConfig.setTcpNoDelay(config.isTcpNoDelay());
		/**
		 * 端口可以重用
		 */
	    socketConfig.setReuseAddress(config.isReuseIpAddr());      
	    configuration.setSocketConfig(socketConfig);
	    
		server = new SocketIOServer(configuration);
	}
	
	public void start() {
		
		/**
		 *  添加连接事件监听器
		 */
		server.addConnectListener(new ConnectListener() {
			public void onConnect(SocketIOClient client) {
				logger.info("USER LOGIN : {}" ,client.getRemoteAddress());
			}
		});
		
		/**
		 *  添加断开连接事件监听器
		 */
		server.addDisconnectListener(new DisconnectListener() {
			public void onDisconnect(SocketIOClient client) {
				logger.info("USER LOGOUT : {}",client.getRemoteAddress());
				servCentral.remove(client);
			}
		});
		
		/**
		 *  添加注册事件监听器
		 */
		server.addEventListener(REGISTER, String.class, new DataListener<String>() {
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				logger.info("USER REGISTER : {}",data);
				servCentral.register(data, client);
			}
		});
		
		/**
		 *  添加聊天事件监听器
		 */
		server.addEventListener(CHAT, String.class, new DataListener<String>() {
			public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {
				logger.info("USER CHAT : {}",data);
				servCentral.chat(data);
			}
		});
		
		/** 
		 *  服务中心启动
		 */
		servCentral.start();
		
		/** 
		 *  websocket服务器启动
		 */
		server.start();
	}

	private void initServCentral() {
		servCentral = new ServCentral(config);
	}
	
	public int size() {
		return server.getAllClients().size();
	}
}
