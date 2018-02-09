package com.uzskill.base.user.bean;

/**
 * 用户认证状态枚举类型定义
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-10-28
 *	
 *	UNSUBMIT(0, "未认证", "UNSUBMIT"), 
 *	CHECKING(1, "待认证", "CHECKING"), 
 *	QUALIFIED(2, "已认证", "QUALIFIED"), 
 *	UNQUALIFIED(3, "未通过认证", "UNQUALIFIED");
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public enum UserStatus {
	
	UNSUBMIT(0, "未认证", "UNSUBMIT"), 
	CHECKING(1, "待认证", "CHECKING"), 
	QUALIFIED(2, "已认证", "QUALIFIED"), 
	UNQUALIFIED(3, "未通过认证", "UNQUALIFIED");
	
	private int id;
	private String name;
	private String code;
	
	private UserStatus(int id, String name, String code){
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	/**
	 * 根据用户认证状态属性Id取得用户认证状态
	 * 
	 * @param id
	 * @return 用户认证状态枚举类
	 */
	public static UserStatus get(int id) {
		UserStatus status = null;
			switch(id) {
				case 0: status = UNSUBMIT; break;
				case 1: status = CHECKING; break;
				case 2: status = QUALIFIED; break;
				case 3: status = UNQUALIFIED; break;
				default: break;
			}
		return status;
	}
	
	/**
	 * 根据用户认证状态属性code取得用户认证状态
	 * 
	 * @param code
	 * @return 用户认证状态枚举类
	 */
	public static UserStatus get(String code) {
		UserStatus urStatus = null;
		for(UserStatus status : UserStatus.values()) {
			if(status.code.equalsIgnoreCase(code)) {
				urStatus = status;
				break;
			}
		}
		return urStatus;
	}
	
	/**
	 * 取得用户认证状态属性Id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * 取得用户认证状态属性名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 取得用户认证状态属性code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}
}