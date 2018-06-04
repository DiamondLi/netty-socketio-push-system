package com.sdsy.push.spz.service.redis;

/**
 *  @version v2.0.0
 *  @author yang.deng
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.sdsy.push.spz.constant.Const;

public class RedisConfig {

	/**
	 * 最大连接数
	 */
	private int maxTotal = 10;
	/**
	 * 最大活动连接数
	 */
	private int maxIdle = 3;
	/**
	 * 获取连接时是否测试
	 */
	private boolean testOnBorrow = true;
	/**
	 * 释放连接时是否测试
	 */
	private boolean testOnReturn = false;
	/**
	 * 主机
	 */
	private String host = "localhost";
	/**
	 * 端口
	 */
	private int port = 6379;
	/**
	 * 超时时间
	 */
	private int timeout = 10000;
	/**
	 * 密码
	 */
	private String password;

	RedisConfig() {
		init(Const.REDIS_DEFAULT_CONFIG_NAME);
	}
	
	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void init(String configName) {
		Properties prop = new Properties();

		InputStream is = null;
		try {
			is = RedisConfig.class.getResourceAsStream("/" + configName);
			prop.load(is);

			String value = null;

			value = prop.getProperty(Const.REDIS_MAX_TOTAL_NAME);
			if (value != null) {
				maxTotal = Integer.valueOf(value);
			}

			value = prop.getProperty(Const.REDIS_MAX_IDLE_NAME);
			if (value != null) {
				maxIdle = Integer.valueOf(value);
			}

			value = prop.getProperty(Const.REDIS_TEST_ON_BORROW);
			if (value != null) {
				testOnBorrow = Boolean.valueOf(value);
			}

			value = prop.getProperty(Const.REDIS_TEST_ON_RETURN);
			if (value != null) {
				testOnReturn = Boolean.valueOf(value);
			}

			host = prop.getProperty(Const.REDIS_HOST_NAME);

			value = prop.getProperty(Const.REDIS_PORT_NAME);
			if (value != null) {
				port = Integer.valueOf(value);
			}

			value = prop.getProperty(Const.REDIS_TIMEOUT_NAME);
			if (value != null) {
				timeout = Integer.valueOf(value);
			}
			
			value = prop.getProperty(Const.REDIS_PASSWORD);
			if (value != null) {
				password = value;
			}
		} catch (FileNotFoundException e) {
			//log.error("配置文件：" + configName + "不存在!", e);
		} catch (IOException e) {
			//log.error("读取配置文件失败", e);
		} catch (Exception e) {
			//log.error("初始化配置失败，请检查配置文件!", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}