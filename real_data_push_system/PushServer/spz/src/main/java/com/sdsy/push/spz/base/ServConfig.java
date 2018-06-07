package com.sdsy.push.spz.base;

/**
 *  @version v2.0
 *  @since 2018/2/6
 */

public class ServConfig {
	/**
	 *  服务名
	 */
	private String servName;
	
	/**
	 *  工作线程个数
	 */
	private int workers = 1;
	
	/**
	 *  点对点通信
	 */
	private boolean personal;
	/**
	 *  广播通信
	 */
	private boolean boardcast;
	
	/**
	 *  聊天通信
	 */
	private boolean chat;
	
	public boolean isChat() {
		return chat;
	}

	public void setChat(boolean chat) {
		this.chat = chat;
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
	}

	public int getWorkers() {
		return workers;
	}

	public void setWorkers(int workers) {
		this.workers = workers;
	}

	public boolean isPersonal() {
		return personal;
	}

	public void setPersonal(boolean personal) {
		this.personal = personal;
	}

	public boolean isBoardcast() {
		return boardcast;
	}

	public void setBoardcast(boolean boardcast) {
		this.boardcast = boardcast;
	}

}
