package com.sdsy.push.spz.base;

/**
 * @author yang.deng
 * @version v2.0.0
 */

public class ChatInfo {

	/** 服务名  */
	private String servName;
	
	/** 消息 */
	private String message;
	
	/** 消息来源UID */
	private String from;
	
	/** 消息目的地UID */
	private String to;
	
	/** 消息来源者姓名 */
	private String fromName;
	
	/** 消息目的地姓名 */
	private String toName;

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}
	
}
