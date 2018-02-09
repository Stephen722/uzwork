package com.uzskill.mobile.chat;

import javax.websocket.Session;

/**
 * Web chat session
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-10-06
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WebChatSession {
	
	private String id;						  // web socket session ID
	private Session session;					  // web socket session
	private String userId;				  	  // session created user ID
	private String username;					  // session created user name
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
		this.id = session.getId();
	}
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
}