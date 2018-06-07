package com.sdsy.push.spz.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdsy.push.spz.websocket.Server;

public class Monitor  {

	private static Logger logger = LoggerFactory.getLogger(Monitor.class);
	
	// 监控频道
	private String channel;
	
	// 监控频率  单位ms
	private long period;
	
	private MonitorInfo info;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Monitor(Server server,String channel,long period) {
		info = new MonitorInfo(server,channel);
		this.period = period;
	}
	
	public void start() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						info.publish();
						Thread.sleep(period);
					} catch (Exception e) {
						logger.error("监控出现异常 ： {}",e);
					}
				}
			}
		}.start();
	}
}
