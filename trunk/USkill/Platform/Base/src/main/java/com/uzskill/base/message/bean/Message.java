package com.uzskill.base.message.bean;

/**
 * 站内消息通知
 *
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-05-23
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class Message {
	
	private int id;
	private int userId;
	private int sourceId;
	private String title;
	private String message;
	private int readB;
	private int type;		// 0: 系统消息  1: 管理员发送的消息
	private int deleteB;
	private String createdTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getReadB() {
		return readB;
	}
	public void setReadB(int readB) {
		this.readB = readB;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDeleteB() {
		return deleteB;
	}
	public void setDeleteB(int deleteB) {
		this.deleteB = deleteB;
	}

}