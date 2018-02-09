package com.uzskill.base.user;

/**
 * 用户类型枚举类型定义
 * 	FREE(0, "免费", "FREE"), 
 *	JUNIOR(1, "初级", "JUNIOR"), 
 *	MIDDLE(2, "中级", "MIDDLE"), 
 *	SENIOR(3, "高级", "SENIOR"), 
 *	SUPER(4, "特级", "SUPER"),
 *	TOP(5, "终极", "TOP");
 *	
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-28
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public enum UserType {
	FREE(0, "免费", "FREE"), 
	JUNIOR(1, "初级", "JUNIOR"), 
	MIDDLE(2, "中级", "MIDDLE"), 
	SENIOR(3, "高级", "SENIOR"), 
	SUPER(4, "特级", "SUPER"),
	TOP(5, "终极", "TOP");
	
	private int id;
	private String name;
	private String code;
	
	private UserType(int id, String name, String code){
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	/**
	 * 根据用户类型属性Id取得用户类型
	 * 
	 * @param id
	 * @return 用户类型枚举类
	 */
	public static UserType get(int id) {
		UserType userType = null;
			switch(id) {
				case 0: userType = FREE; break;
				case 1: userType = JUNIOR; break;
				case 2: userType = MIDDLE; break;
				case 3: userType = SENIOR; break;
				case 4: userType = SUPER; break;
				case 5: userType = TOP; break;
				default: break;
			}
		return userType;
	}
	
	/**
	 * 根据用户类型属性code取得用户类型
	 * 
	 * @param code
	 * @return 用户类型枚举类
	 */
	public static UserType get(String code) {
		UserType rtnType = null;
		for(UserType userType : UserType.values()) {
			if(userType.code.equalsIgnoreCase(code)) {
				rtnType = userType;
				break;
			}
		}
		return rtnType;
	}
	
	/**
	 * 取得用户类型属性Id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * 取得用户类型属性名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 取得用户类型属性code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}
}