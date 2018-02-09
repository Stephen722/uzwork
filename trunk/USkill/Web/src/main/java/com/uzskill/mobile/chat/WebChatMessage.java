package com.uzskill.mobile.chat;
/**
 * Web chat message
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-10-06
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WebChatMessage {
	
	private int id;								  // chat message ID
	private String messageId;					  // message string ID
	private String message;						  // message content
	private String sendUserId;                     // send user ID (encrypt)
	private String receiveUserId;                  // receive user ID (encrypt)
	private String sendUsername;				  	  // send user name
	private String receiveUsername;				  // receive user name
	private String sendTime;					   	  // sent message time
	private int read;					 	      // 0/1: unread/read
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getSendUsername() {
		return sendUsername;
	}
	public void setSendUsername(String sendUsername) {
		this.sendUsername = sendUsername;
	}
	public String getReceiveUsername() {
		return receiveUsername;
	}
	public void setReceiveUsername(String receiveUsername) {
		this.receiveUsername = receiveUsername;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getRead() {
		return read;
	}
	public void setRead(int read) {
		this.read = read;
	}
}
