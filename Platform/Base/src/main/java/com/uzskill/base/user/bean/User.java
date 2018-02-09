package com.uzskill.base.user.bean;

import com.light.utils.DESUtils;

/**
 * 用户实体类
 *
 * <p>(C) 2018 www.uzwork.com (UZWork)</p>
 * Date:  2018-01-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class User {
	private int userId;                               // 用户Id
	private String userIdStr;                         // 加密后的UserID
	private String nickname;						  // 昵称
	private String username;						  // 用户名
	private String password;                          // 密码
	private String phone;                         	  // 手机号码
	private String picture;                           // 用户头像路径
	private int sex;							   	  // 性别
	private String brief;                             // 简介
	private String city;                              // 城市
	private String createdTime;                       // 注册时间
	private String createdIp;                         // 注册时IP地址
	private String loginTime;                         // 最新登录时间
	private String loginIp;                           // 最新登录的IP地址
	private int logins;                               // 登录总次数
	private int messages;                             // 未读消息总数
	private int activeB;                              // 是否激活（活跃状态）
	private int friends;							  // 好友总数
	private int favorites;							  // 收藏总数
	
	/**
	 * 取得用户状态值
	 * 
	 * @return
	 */
	public int getActiveB() {
		return activeB;
	}
	/**
	 * 设置用户状态值
	 * 
	 * @param activeB
	 */
	public void setActiveB(int activeB) {
		this.activeB = activeB;
	}
	/**
	 * 取得用户ID
	 * 
	 * @return 用户ID
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * 设置用户ID，同时设置用户ID字符串
	 * 
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
		setUserIdStr(DESUtils.encrypt(String.valueOf(userId)));
	}
	/**
	 * 取得用户名称
	 * 
	 * @return 用户名称
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置用户名称
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 取得用户密码
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置用户密码
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 取得用户手机号码
	 * 
	 * @return 手机号码
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置用户手机号码
	 * 
	 * @param phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 取得注册时间
	 * 
	 * @return 注册时间
	 */
	public String getCreatedTime() {
		return createdTime;
	}
	/**
	 * 设置注册时间
	 * 
	 * @param createdTime
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	/**
	 * 取得登录时间
	 * 
	 * @return 登录时间
	 */
	public String getLoginTime() {
		return loginTime;
	}
	/**
	 * 设置登录时间
	 * 
	 * @param loginTime
	 */
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	/**
	 * 取得登录次数
	 * 
	 * @return
	 */
	public int getLogins() {
		return logins;
	}
	/**
	 * 设置登录次数
	 * 
	 * @param logins
	 */
	public void setLogins(int logins) {
		this.logins = logins;
	}
	/**
	 * 取得注册IP
	 * 
	 * @return
	 */
	public String getCreatedIp() {
		return createdIp;
	}
	/**
	 * 设置注册IP
	 * 
	 * @param createdIp
	 */
	public void setCreatedIp(String createdIp) {
		this.createdIp = createdIp;
	}
	/**
	 * 取得最新登录IP
	 * 
	 * @return
	 */
	public String getLoginIp() {
		return loginIp;
	}
	/**
	 * 设置最新登录IP
	 * 
	 * @param loginIp
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}


	/**
	 * 取得个人简介
	 * 
	 * @return
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * 设置个人简介
	 * 
	 * @param brief
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * 取得用户所在城市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置用户所在城市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * 取得加密后的User ID
	 * 
	 * @return
	 */
	public String getUserIdStr() {
		return userIdStr;
	}
	/**
	 * 设置加密的User ID
	 * 
	 * @param userIdStr
	 */
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	
	/**
	 * 取得昵称
	 * 
	 * @return
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * 设置昵称
	 * 
	 * @param nickname
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	/**
	 * 取得头像
	 * 
	 * @return
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * 设置头像
	 * 
	 * @param picture
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * 取得性别值
	 * 
	 * @return 0：女 1：男
	 */
	public int getSex() {
		return sex;
	}
	/**
	 * 设置性别，0：女 1：男
	 * 
	 * @param sex
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getMessages() {
		return messages;
	}
	public void setMessages(int messages) {
		this.messages = messages;
	}
	public int getFriends() {
		return friends;
	}
	public void setFriends(int friends) {
		this.friends = friends;
	}
	public int getFavorites() {
		return favorites;
	}
	public void setFavorites(int favorites) {
		this.favorites = favorites;
	}

}