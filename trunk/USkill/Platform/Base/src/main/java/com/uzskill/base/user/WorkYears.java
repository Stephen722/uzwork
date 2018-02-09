package com.uzskill.base.user;

/**
 * 用户工作年限枚举类型定义
 *	Y1(0, "1年以内", "	Y1"), 
 *	Y3(1, "1-3年", "Y3"), 
 *	Y5(2, "3-5年", "Y5"), 
 *	Y10(3, "5-10年", "Y10"), 
 *	M10(4, "10年以上", "M10");
 *	
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-04-11
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public enum WorkYears {
	Y1(0, "1年以内", "Y1"), 
	Y3(1, "1-3年", "Y3"), 
	Y5(2, "3-5年", "Y5"), 
	Y10(3, "5-10年", "Y10"), 
	M10(4, "10年以上", "M10");
	
	private int id;
	private String name;
	private String code;
	
	private WorkYears(int id, String name, String code){
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	/**
	 * 根据用户工作年限类型属性Id取得用户工作年限类型
	 * 
	 * @param id
	 * @return 用户工作年限枚举类
	 */
	public static WorkYears get(int id) {
		WorkYears userType = null;
			switch(id) {
				case 0: userType = Y1; break;
				case 1: userType = Y3; break;
				case 2: userType = Y5; break;
				case 3: userType = Y10; break;
				case 4: userType = M10; break;
				default: break;
			}
		return userType;
	}
	
	/**
	 * 根据工作年限类型属性code取得工作年限类型
	 * 
	 * @param code
	 * @return 工作年限类型枚举类
	 */
	public static WorkYears get(String code) {
		WorkYears rtnType = null;
		for(WorkYears workYear : WorkYears.values()) {
			if(workYear.code.equalsIgnoreCase(code)) {
				rtnType = workYear;
				break;
			}
		}
		return rtnType;
	}
	
	/**
	 * 取得用户工作年限属性Id
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * 取得用户工作年限属性名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 取得用户工作年限属性code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}
}