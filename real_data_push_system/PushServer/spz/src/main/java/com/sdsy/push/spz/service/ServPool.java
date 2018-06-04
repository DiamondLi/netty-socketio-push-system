package com.sdsy.push.spz.service;

/**
 * @version v2.0
 * @date 2018.05.07
 */

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.SocketIOClient;
import com.sdsy.push.spz.base.RegInfo;

public class ServPool {

	private static Logger logger = LoggerFactory.getLogger(ServPool.class);
	
	/**
	 *  服务域
	 */
	private Map<String,Service> map = null;
	
	public ServPool() {
		map = new HashMap<String,Service>();
	}
	
	public Service getOneElement(String servName) {
		return map.get(servName);
	}
	
	public void put(String servName,Service serv) {
		map.put(servName, serv);
	}
	
	/**
	 *  逐次启动每个服务
	 */
	public void start() {
		for(Service service : map.values()) {
			service.start();
		}
	}
	
	/** 销毁 */
	public void remove(SocketIOClient client) {
		for(Service service : map.values()) {
			service.remove(client);
		}
	}
	
	/** 注册 */
	public void register(String data,SocketIOClient client) {
		RegInfo regInfo = null;
		try {
			// 解析失败就直接返回，不让注册
			regInfo = JSON.parseObject(data, RegInfo.class);
		} catch(Exception e) {
			logger.error("解析注册数据结构 {} 异常 {}",data,e);
			return;
		}
		/**  解析UUID*/
		String uuid = regInfo.getUuid();
		for(String servName : regInfo.getServNames()) {
			Service service = getOneElement(servName);
			if(service != null) {
				service.register(client,uuid);
			}
		}
	}
}
