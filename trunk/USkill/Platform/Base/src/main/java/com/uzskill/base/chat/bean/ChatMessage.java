package com.uzskill.base.chat.bean;

/**
 * 在线聊天实体类
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-10-09
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ChatMessage {
	
	private int id;                 // 项目ID
	private int sendUserId;			// 发送消息用户ID
	private int receiveUserId;		// 接受消息用户ID
	private String sentTime;		// 发送消息时间
	private String content;			// 消息内容
	private int read;				// 是否被接收者阅读，0/1：未/已阅读

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(int sendUserId) {
		this.sendUserId = sendUserId;
	}
	public int getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(int receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getSentTime() {
		return sentTime;
	}
	public void setSentTime(String sentTime) {
		this.sentTime = sentTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
	
}