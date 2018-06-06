package com.sdsy.push.spz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sdsy.push.spz.base.Config;
import com.sdsy.push.spz.base.ResolveJSONConfigFile;
import com.sdsy.push.spz.monitor.Monitor;
import com.sdsy.push.spz.websocket.Server;

/**
 * 启动服务器
 *
 */
public class App {
	
	private static Logger logger = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) {
    	
    	try {
        	// 读取配置文件 
        	Config config = new ResolveJSONConfigFile().readConfigFile();
        	// 初始化服务器
        	Server server = new Server(config);
        	// 初始化监控器
        	Monitor monitor = new Monitor(server,config.getMonitorConfig().getChannel(),config.getMonitorConfig().getPeriod());
        	// 启动服务
        	server.start();
        	// 启动监控
        	monitor.start();
    	} catch (Exception e) {
    		logger.error("catch a exception {}",e);
    	}
    }
}
