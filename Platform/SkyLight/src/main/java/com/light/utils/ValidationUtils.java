package com.light.utils;

/**
 * Validation Utils.
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-06-27
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ValidationUtils {
	private static final String PHONE_REG = "^((13[0-9])|(147)|(17[7|8])|(15[0-9])|(18[0-9]))\\d{8}$";
	private static final String EMAIL_REG = "^[0-9a-zA-Z-_.]{2,}@(([0-9a-zA-Z]{2,})[.])+[a-z]{2,4}$";
	private static final String IDCARD_REG = "^\\d{18}|\\d{17}[Xx]$";
	private static final String USERNAME_REG = "^[a-zA-Z0-9_]{2,16}$";
	private static final String REAL_NAME_REG = "^[\u4e00-\u9fa5]{2,6}$";
	private static final String ORGANIZATION_NAME_REG = "^[\u4E00-\u9FA5\\(\\)\\（\\）]{4,30}$";

	/**
	 * 判断手机号是否有效
	 * 
	 * @param phone
	 * @return true/false
	 */
	public static boolean isPhone(String phone) {
		return phone.matches(PHONE_REG);
	}
	/**
	 * 判断身份证号是否有效
	 * 
	 * @param idCard
	 * @return true/false
	 */
	public static boolean isIdCard(String idCard) {
		boolean valid = false;
		String upIdCard = idCard.toUpperCase();
		if(upIdCard.matches(IDCARD_REG)) {
			int[] code = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
			char[] check = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
			int result = 0;
			for(int i=0; i< 17; i++) {
				result += (upIdCard.charAt(i) - '0')*code[i];
			}
			result %= 11;
			if(check[result] ==  upIdCard.charAt(17)) {
				valid = true;
			}else {
				valid = false;
			}
		}
		
		return valid;
	}
	/**
	 * 判断Email格式是否正确
	 * 
	 * @param email
	 * @return true/false
	 */
	public static boolean isEmail(String email) {
		return email.matches(EMAIL_REG);
	}
	/**
	 * 判断Username是否符合2-20位数字、字母和下划线组成的字符串
	 * 
	 * @param username
	 * @return true/false
	 */
	public static boolean isUsername(String username) {
		return username.matches(USERNAME_REG);
	}
	/**
	 * 判断是否为真实中文姓名，2到6位汉字
	 * 
	 * @param realName
	 * @return true/false
	 */
	public static boolean isRealName(String realName) {
		return realName.matches(REAL_NAME_REG);
	}
	/**
	 * 判断是否为真实中文企业名称，4到30位汉字和中英文括号组成的字符串
	 * 
	 * @param organizationName
	 * @return true/false
	 */
	public static boolean isOrganizationName(String organizationName) {
		return organizationName.matches(ORGANIZATION_NAME_REG);
	}
	
	/**
	 * 根据文件名判断是否是图片文件
	 * 
	 * @param filename
	 * @return true/false
	 */
	public static boolean isImageExtension(String filename) {
		boolean rtn = false;
		if(!StringUtils.isBlank(filename) && filename.lastIndexOf(".") > -1) {
			String extension = filename.substring(filename.lastIndexOf(".") + 1);
			if("jpg".equalsIgnoreCase(extension) || "jpeg".equalsIgnoreCase(extension) || "png".equalsIgnoreCase(extension) || "gif".equalsIgnoreCase(extension)) {
				rtn = true;
			}
		}
		return rtn;
	}
}
