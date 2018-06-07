package com.sdsy.push.spz.monitor.net;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class NetInfo {

	private Sigar sigar;
	
	private static final int KB = 1024;
		
	public NetInfo(Sigar sigar) {
		this.sigar = sigar;
	}
	
	public String[] getNetInterfaceList() throws Exception {
		return sigar.getNetInterfaceList();
	}
	
	public NetInterfaceConfig getNetInterfaceConfig(String netInterface) throws Exception {
		return sigar.getNetInterfaceConfig(netInterface);
	}
	
	public NetInterfaceStat getNetInterfaceStat(String netInterface) throws SigarException {
		return sigar.getNetInterfaceStat(netInterface);
	}
	
	// unit : kb 
	public long getReceiveBytes(NetInterfaceStat stat) {
		return stat.getRxBytes() / KB ;
	}
	
	// unit : kb
	public long getSendBytes(NetInterfaceStat stat) {
		return stat.getTxBytes() / KB;
	}
}
