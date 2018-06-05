package com.sdsy.push.spz.monitor.memory;

/**
 *  Author : yang.deng
 *  Date   : 2017.09.11
 */

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class MemoryInfo {

	private Sigar sigar;
	
	static final int KB = 1024;
	
	static final int MB = 1024;
	
	public MemoryInfo(Sigar sigar) {
		this.sigar = sigar;
	}
	
	/** 
	 * 内存总量     
	 */
	public String getTotal() throws SigarException {
		return Long.toString(sigar.getMem().getTotal() / KB / MB);
	}
	/** 
	 * JVM已使用的内存 
	 */
	public String getJvmTotalMemory() {
		return Long.toString(Runtime.getRuntime().totalMemory() / KB / MB);
	}
	/** 
	 * JVM剩余的内存    
	 */
	public String getJvmFreeMemory() {
		return Long.toString(Runtime.getRuntime().freeMemory() / KB / MB);
	}
	/** 
	 * 已使用内存 
	 */
	public String getUsed() throws SigarException {
		return Long.toString(sigar.getMem().getUsed() / KB / MB);
	}
	/** 
	 * 当前剩余内存
	 */
	public String getFree() throws SigarException {
		return Long.toString(sigar.getMem().getFree() / KB / MB);
	}
	/** 
	 * 当前使用内存百分比 
	 */
	public String getUsedPercent() throws SigarException {
		return Double.toString(sigar.getMem().getUsedPercent());
	}
	/** 
	 * 当前剩余内存百分比 
	 */
	public String getFreePercent() throws SigarException {
		return Double.toString(sigar.getMem().getFreePercent());
	}
}
