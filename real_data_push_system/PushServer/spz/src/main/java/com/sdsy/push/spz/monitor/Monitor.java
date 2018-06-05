package com.sdsy.push.spz.monitor;

import com.sdsy.push.spz.websocket.Server;

public class Monitor  {

	// 监控频道
	private String channel;
	
	// 监控频率  单位ms
	private long period;
	
	private MonitorInfo info;
	
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
						
					}
				}
			}
		}.start();
	}
}
