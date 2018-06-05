package com.sdsy.push.spz.monitor;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class BasicInfo {

	private Sigar sigar;
	
	public BasicInfo(Sigar sigar) {
		this.sigar = sigar;
	}
	
	/**
	 * @return 主机名称
	 * @throws SigarException
	 */
	public String getHostName() throws SigarException {
		return sigar.getNetInfo().getHostName();
	}
	
	/** 
	 * @return IP地址,该函数返回值不对，用getFDQN()函数
	 * @throws SigarException
	 * @throws UnknownHostException 
	 */
	public String getInetAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	/**
	 * @return 正式域名
	 * @throws SigarException 
	 */
	public String getFDQN() throws SigarException {
		return sigar.getFQDN();
	}
	
}
