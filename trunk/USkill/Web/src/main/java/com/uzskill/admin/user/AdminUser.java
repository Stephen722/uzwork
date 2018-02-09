package com.uzskill.admin.user;

/**
 * 管理员用户
 *
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-10-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Admin 1.0
 */
public class AdminUser {
	
	private int adminId;                              // 管理员Id
	private int activeB;                              // 是否激活（活跃状态）
	private String username;						  // 用户名
	private String password;                          // 密码
	private String loginTime;                         // 最新登录时间
	private String loginIp;                           // 最新登录的IP地址
	private int logins;                               // 总登录次数
	private int groupId;							  // 管理员组
	private int createdBy;							  // 创建者
	private String createdTime;                       // 创建时间
	
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getGroupId() {
		return groupId;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public int getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	
	public int getActiveB() {
		return activeB;
	}
	
	public void setActiveB(int activeB) {
		this.activeB = activeB;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	
	public String getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	
	public int getLogins() {
		return logins;
	}
	
	public void setLogins(int logins) {
		this.logins = logins;
	}
	
	public String getLoginIp() {
		return loginIp;
	}
	
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

}
