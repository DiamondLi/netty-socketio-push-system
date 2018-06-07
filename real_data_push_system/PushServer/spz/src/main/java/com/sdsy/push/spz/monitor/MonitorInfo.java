package com.sdsy.push.spz.monitor;

import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdsy.push.spz.monitor.cpu.CpuInfo;
import com.sdsy.push.spz.monitor.memory.MemoryInfo;
import com.sdsy.push.spz.monitor.net.NetInfo;
import com.sdsy.push.spz.service.redis.RedisUtils;
import com.sdsy.push.spz.websocket.Server;

public class MonitorInfo {

	private JSONObject info = new JSONObject();

	// 获取整个系统的连接数
	private Server server;
	
	private String channel;
	
	// 第一次调用网络接包和发包都是0
	private long lastReceiveKBytes = 0;
	
	private long lastSendKBytes = 0;
	
//	private long lastTime;
//	
//	private long currentTime;
	
	private CpuInfo cpuInfo;
	
	private MemoryInfo memoryInfo;
	
	private BasicInfo basicInfo;
	
	private NetInfo netInfo;
	
	private Sigar sigar;
	
	private boolean first = true;
	
	private String hostName;
	
	public MonitorInfo(Server server,String channel) {
		sigar = new Sigar();
		cpuInfo = new CpuInfo(sigar);
		memoryInfo = new MemoryInfo(sigar);
		basicInfo = new BasicInfo(sigar);
		netInfo = new NetInfo(sigar);
		this.server = server;
		this.channel = channel;
	}
	
	// 保存基本信息
	private void saveBasicInfo() throws Exception {
		JSONObject basicInfoMap = new JSONObject();
		basicInfoMap.put("hostName", basicInfo.getHostName());
		basicInfoMap.put("ipAddr", basicInfo.getInetAddress());
		basicInfoMap.put("fdqn", basicInfo.getFDQN());
		hostName =  basicInfo.getFDQN();
		info.put("basicInfo", basicInfoMap);
	}
	
	// 保存cpu信息  Only once
	private void saveCpuInfo() throws Exception {
		JSONObject cpuInfoMap = new JSONObject();
		cpuInfoMap.put("usage", cpuInfo.getCombinedTime()+"%");
		cpuInfoMap.put("cpus", cpuInfo.getCpus());
		info.put("cpuInfo", cpuInfoMap);
	}
	
	// 保存 内存使用情况
	private void saveMemoryUsageInfo() throws Exception {
		JSONObject memoryInfoMap = new JSONObject();
		memoryInfoMap.put("sysUsed", memoryInfo.getUsed()+"(MB)");
		memoryInfoMap.put("sysFree", memoryInfo.getFree()+"(MB)");
		memoryInfoMap.put("jvmUsed", memoryInfo.getJvmTotalMemory()+"(MB)");
		memoryInfoMap.put("jvmFree", memoryInfo.getJvmFreeMemory()+"(MB)");
		info.put("memoryInfo", memoryInfoMap);
	}
	
	private void saveNetUsageInfo() throws Exception {
		JSONObject netInfoMap = new JSONObject();
		netInfoMap.put("connections", server.size());
//		currentTime = System.currentTimeMillis();
//		long gap = first ? 0 : currentTime - lastTime;
//		lastTime = currentTime;
		String[] list = netInfo.getNetInterfaceList();
		JSONObject netInterface = new JSONObject();
		for(int i = 0; i < list.length;i++) {
			NetInterfaceConfig config = netInfo.getNetInterfaceConfig(list[i]);
			if(!config.getAddress().equals(hostName)){
				continue;
			}
			netInterface.put("name", config.getName());
			NetInterfaceStat stat = netInfo.getNetInterfaceStat(list[i]);
			if(first) {
				netInterface.put("receiveKBytes", "0(KB)");
				netInterface.put("sendKBytes", "0(KB)");
			} else {
				netInterface.put("receiveKBytes", String.valueOf((lastReceiveKBytes > 0 ? netInfo.getReceiveBytes(stat) - lastReceiveKBytes : 0))+"KB");
				netInterface.put("sendKBytes", String.valueOf(lastSendKBytes > 0 ? netInfo.getSendBytes(stat) - lastSendKBytes : 0) + "KB");	
			}
			lastReceiveKBytes = netInfo.getReceiveBytes(stat);
			lastSendKBytes = netInfo.getSendBytes(stat);
		}
		netInfoMap.put("netInterface", netInterface);
		info.put("netInfo", netInfoMap);
	}
	
	public void publish() throws Exception {
		saveNetUsageInfo();
		saveCpuInfo();
		saveMemoryUsageInfo();
		if(first) {
			saveBasicInfo();
			first = false;
		}
		RedisUtils.getRedisUtils().publish(channel, JSON.toJSONString(info));
	}
	
}
