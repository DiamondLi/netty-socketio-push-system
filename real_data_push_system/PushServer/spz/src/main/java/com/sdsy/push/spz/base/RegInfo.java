package com.sdsy.push.spz.base;

import java.util.ArrayList;

/**
 * APP 注册体
 */
public class RegInfo {

	private ArrayList<String> servNames;
	
	private String uuid;

	public ArrayList<String> getServNames() {
		return servNames;
	}

	public void setServNames(ArrayList<String> servNames) {
		this.servNames = servNames;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
