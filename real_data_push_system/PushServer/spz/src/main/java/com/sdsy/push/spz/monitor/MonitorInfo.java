package com.sdsy.push.spz.monitor;

import java.util.HashMap;
import java.util.Map;
import org.hyperic.sigar.Sigar;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdsy.push.spz.monitor.cpu.CpuInfo;
import com.sdsy.push.spz.monitor.memory.MemoryInfo;
import com.sdsy.push.spz.service.redis.RedisUtils;
import com.sdsy.push.spz.websocket.Server;

public class MonitorInfo {

	private JSONObject info = new JSONObject();

	// 获取整个系统的连接数
	private Server server;
	
	private String channel;
	
	// 第一次调用网络接包和发包都是0
	private long recePackets = 0;
	
	private long sendPackets = 0;
	
	private long lastTime;
	
	private long currentTime;
	
	private CpuInfo cpuInfo;
	
	private MemoryInfo memoryInfo;
	
	private BasicInfo basicInfo;
	
	private Sigar sigar;
	
	private boolean first = true;
	
	public MonitorInfo(Server server,String channel) {
		sigar = new Sigar();
		cpuInfo = new CpuInfo(sigar);
		memoryInfo = new MemoryInfo(sigar);
		basicInfo = new BasicInfo(sigar);
		this.server = server;
		this.channel = channel;
	}
	
	// 保存基本信息
	private void saveBasicInfo() throws Exception {
		JSONObject basicInfoMap = new JSONObject();
		basicInfoMap.put("hostName", basicInfo.getHostName());
		basicInfoMap.put("ipAddr", basicInfo.getInetAddress());
		basicInfoMap.put("fdqn", basicInfo.getFDQN());
		info.put("basicInfo", basicInfoMap);
	}
	
	// 保存cpu信息  Only once
	private void saveCpuInfo() throws Exception {
		JSONObject cpuInfoMap = new JSONObject();
		cpuInfoMap.put("usage", cpuInfo.getCombinedTime());
		cpuInfoMap.put("cpus", cpuInfo.getCpus());
		info.put("cpuInfo", cpuInfoMap);
	}
	
	// 保存 内存使用情况
	private void saveMemoryUsageInfo() throws Exception {
		JSONObject memoryInfoMap = new JSONObject();
		memoryInfoMap.put("sysUsed", memoryInfo.getUsed());
		memoryInfoMap.put("sysFree", memoryInfo.getFree());
		memoryInfoMap.put("jvmUsed", memoryInfo.getJvmTotalMemory());
		memoryInfoMap.put("jvmFree", memoryInfo.getJvmFreeMemory());
		info.put("memoryInfo", memoryInfoMap);
	}
	
	private void saveNetUsageInfo() {
		JSONObject netInfoMap = new JSONObject();
		netInfoMap.put("connections", server.size());
		currentTime = System.currentTimeMillis();
		if(first) {
			lastTime = currentTime;
		}
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
