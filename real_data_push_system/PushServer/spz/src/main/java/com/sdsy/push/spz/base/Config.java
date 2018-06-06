package com.sdsy.push.spz.base;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

/**
 * @author yang.deng
 * @version v2.0
 * @since 2018/2/5
 */

public class Config {	
	/**
	 * websocket监听端口
	 */
	private int wsPort;
	/**
	 * websocket主机地址
	 */
	private String wsHostname;
	/**
	 * 是否重用IP地址
	 */
	private boolean isReuseIpAddr;
	/**
	 * 是否启用EPOLL
	 */
	private boolean isUseEpoll;
	/**
	 * 是否禁用Nagle算法
	 */
	private boolean isTcpNoDelay;
	/**
	 * 是否保活
	 */
	private boolean isKeepAlive;
	/**
	 * 服务配置
	 */
	private ArrayList<ServConfig> servConfigs;
	/**
	 * 监控配置
	 */
	private MonitorConfig monitorConfig;
	
	public int getWsPort() {
		return wsPort;
	}
	public void setWsPort(int wsPort) {
		this.wsPort = wsPort;
	}
	public String getWsHostname() {
		return wsHostname;
	}
	public void setWsHostname(String wsHostname) {
		this.wsHostname = wsHostname;
	}
	public boolean isReuseIpAddr() {
		return isReuseIpAddr;
	}
	public void setReuseIpAddr(boolean isReuseIpAddr) {
		this.isReuseIpAddr = isReuseIpAddr;
	}
	public boolean isUseEpoll() {
		return isUseEpoll;
	}
	public void setUseEpoll(boolean isUseEpoll) {
		this.isUseEpoll = isUseEpoll;
	}
	public boolean isTcpNoDelay() {
		return isTcpNoDelay;
	}
	public void setTcpNoDelay(boolean isTcpNoDelay) {
		this.isTcpNoDelay = isTcpNoDelay;
	}
	public boolean isKeepAlive() {
		return isKeepAlive;
	}
	public void setKeepAlive(boolean isKeepAlive) {
		this.isKeepAlive = isKeepAlive;
	}
	public ArrayList<ServConfig> getServConfigs() {
		return servConfigs;
	}
	public void setServConfigs(ArrayList<ServConfig> servConfigs) {
		this.servConfigs = servConfigs;
	}
	
	public MonitorConfig getMonitorConfig() {
		return monitorConfig;
	}
	public void setMonitorConfig(MonitorConfig monitorConfig) {
		this.monitorConfig = monitorConfig;
	}
	
	@Override
	public String toString() {
		return "Config [wsPort=" + wsPort + ", wsHostname=" + wsHostname + ", isReuseIpAddr=" + isReuseIpAddr
				+ ", isUseEpoll=" + isUseEpoll + ", isTcpNoDelay=" + isTcpNoDelay + ", isKeepAlive=" + isKeepAlive
				+ ", servConfigs=" + JSON.toJSONString(servConfigs) + ", monitorConfig=" + JSON.toJSONString(monitorConfig)+"]";
	}
	
	
	
}
