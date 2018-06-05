package com.sdsy.push.spz.monitor.cpu;

/**
 *  Author : yang.deng
 *  Date   : 2017.09.11
 */
import java.text.DecimalFormat;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class CpuInfo {
	
	private Sigar sigar;
	
	private DecimalFormat df; 
	public CpuInfo(Sigar sigar) {
		this.sigar = sigar;
		df = new DecimalFormat("0.00");
	}
	
	/** 
	 * 用户态cpu使用率
	 */
	public String getUserTime() throws SigarException {
		return CpuPerc.format(sigar.getCpuPerc().getUser());
	}
	/** 
	 * 系统态cpu使用率 
	 */
	public String getSysTime() throws SigarException {
		return CpuPerc.format(sigar.getCpuPerc().getSys());
	}
	/** 
	 * cpu空闲时间 
	 */
	public String getIdleTime() throws SigarException {
		return CpuPerc.format(sigar.getCpuPerc().getIdle());
	}
	/** 
	 * cpu等待时间 
	 */
	public String getWaitTime() throws SigarException {
		return CpuPerc.format(sigar.getCpuPerc().getWait());
	}
	/** 
	 * cpu总使用率  
	 */
	public String getCombinedTime() throws SigarException {
		return df.format(100 * sigar.getCpuPerc().getCombined());
	}
	/**
	 * cpu个数
	 */
	public int getCpus() throws SigarException {
		return sigar.getCpuInfoList().length;
	}

}
