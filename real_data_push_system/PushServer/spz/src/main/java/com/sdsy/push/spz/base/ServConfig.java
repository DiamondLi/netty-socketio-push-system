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
	 *  广播频道
	 */
	private String bChannel;
	
	/**
	 *  私人频道
	 */
	private String pChannel;
	
	/**
	 *  聊天频道
	 */
	private String cChannel;
	
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
	
	public String getbChannel() {
		return bChannel;
	}

	public void setbChannel(String bChannel) {
		this.bChannel = bChannel;
	}

	public String getpChannel() {
		return pChannel;
	}

	public void setpChannel(String pChannel) {
		this.pChannel = pChannel;
	}

	public String getcChannel() {
		return cChannel;
	}

	public void setcChannel(String cChannel) {
		this.cChannel = cChannel;
	}

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
