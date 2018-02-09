package com.uzskill.mobile.token;

import com.uzskill.mobile.token.AccessToken;

/**
 * Access token
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class AccessToken {
	
	private String id;
	private int userId;
	private String ipAddress;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
