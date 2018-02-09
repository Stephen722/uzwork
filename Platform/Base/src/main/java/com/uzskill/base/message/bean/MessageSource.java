package com.uzskill.base.message.bean;

/**
 * 站内消息接口
 * 后台发送的消息接口，不包含通知，因为通知已经被直接发送到用户站内消息box
 *
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-28
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class MessageSource {
	private int id;
	private int adminId;
	private String adminUsername;
	private String title;
	private String message;
	private String cellPhone;
	private int destination;
	private String createdTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getAdminId() {
		return adminId;
	}
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	
}