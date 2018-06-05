package com.sdsy.push.spz.base;

/**
 *  @version v2.0
 *  @since 2018.05.07
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import com.alibaba.fastjson.JSON;


public class ResolveJSONConfigFile implements ResolveConfigFile {
	
	private static final String DEFAULT_CONFIG_FILE = "config.json";
	
	public Config readConfigFile(String configName) {
		return readFile(configName);
	}

	public Config readConfigFile() {
		return readFile();
	}
	
	private Config readFile(String file) {
		try {
			InputStream inputStream = new FileInputStream(file);
			String context = IOUtils.toString(inputStream,"utf8");
			return JSON.parseObject(context,Config.class);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR : there is no such file " + file,e);
		} catch (IOException e) {
			throw new RuntimeException("ERROR : failed to load config file " + file,e);
		}
	}
	
	private Config readFile() {
		try {
			InputStream inputStream = ResolveJSONConfigFile.class.getResourceAsStream("/" + DEFAULT_CONFIG_FILE);
			String context = IOUtils.toString(inputStream,"utf8");
			return JSON.parseObject(context,Config.class);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("ERROR : there is no such file " + DEFAULT_CONFIG_FILE,e);
		} catch (IOException e) {
			throw new RuntimeException("ERROR : failed to load config file " + DEFAULT_CONFIG_FILE,e);
		}
	}
	
}
