package com.sdsy.push.spz.constant;

public class Const {

	public final static String ERROR_ARGUMENTS = "";
	public final static String REGISTER = "register";
	public final static String CHAT = "chat";
	public final static String UUID = "uuid";

	// 广播消息通道
	public static final String BOARDCAST_CHANNEL = "_bChannel";
	// 私人消息通道
	public static final String PERSONAL_CHANNEL = "_pChannel";
	// 聊天消息通道
	public static final String CHAT_CHANNEL = "_cChannel";
	
	// redis相关
	public static final String REDIS_DEFAULT_CONFIG_NAME = "redis.properties";
	public static final String REDIS_PASSWORD = "redis.pass";
	public static final String REDIS_MAX_TOTAL_NAME = "redis.pool.maxActive";
	public static final String REDIS_MAX_IDLE_NAME = "redis.pool.maxIdle";
	public static final String REDIS_TEST_ON_BORROW = "redis.pool.testOnBorrow";
	public static final String REDIS_TEST_ON_RETURN = "redis.pool.testOnReturn";
	public static final String REDIS_HOST_NAME = "redis.host";
	public static final String REDIS_PORT_NAME = "redis.port";
	public static final String REDIS_TIMEOUT_NAME = "redis.timeout";
	
}
