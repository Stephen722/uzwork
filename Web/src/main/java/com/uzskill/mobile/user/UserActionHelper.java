package com.uzskill.mobile.user;

import com.light.common.Constants;
import com.light.record.Record;
import com.light.utils.StringUtils;
import com.light.utils.ValidationUtils;
import com.uzskill.base.user.UserQuery;
import com.uzskill.mobile.code.IdentifyingCodeHelper;


/**
 * User action helper
 * 
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-15
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserActionHelper {
	
	/**
	 * login validation
	 * 
	 * @param record
	 * @return error message
	 */
	public static String loginValidation(Record record, String tokenId) {
		boolean valid = true;
		String validateMessageKey = "";
		// Validate phone and sms code
		String phone = record.getStringValue(UserQuery.PHONE, "");
		String smsCode = record.getStringValue(UserQuery.LOGIN_CODE, "");
		if(StringUtils.isBlank(phone)) {
			valid = false;
			validateMessageKey = "mobile.user.login.phone.empty";
		}
		else if(!ValidationUtils.isPhone(phone)){
			valid = false;
			validateMessageKey = "mobile.user.login.phone.invalid";
		}
		else if(StringUtils.isBlank(smsCode) || smsCode.length() < 4  || smsCode.length() > 6){
			valid = false;
			validateMessageKey = "mobile.user.login.smscode.invalid";
		}
		else {
			Record smsRecord = IdentifyingCodeHelper.verifySMSCode(tokenId, record);
			valid = smsRecord.getBooleanValue(Constants.SUCCESS);
			if(valid) {
				validateMessageKey = "";
			}
			else {
				validateMessageKey = smsRecord.getStringValue(Constants.MESSAGE);
			}
		}
		
		return validateMessageKey;
	}	
	
	/**
	 * Register validation
	 * 
	 * @param record
	 * @return error message
	 */
//	public static String registerValidation(Record record, String tokenId, UserManager userManager) {
//		boolean valid = true;
//		String validateMessageKey = "";
//		// validate cell phone
//		String phone = record.getStringValue(ShareFields.CELL_PHONE, "");
//		if(StringUtils.isBlank(phone) || !ValidationUtils.isCellPhone(phone)){
//			valid = false;
//			validateMessageKey = "mobile.user.reg.phone.invalid";
//		}
//		else {
//			int userId = userManager.getUserId(UserUniqueAttribute.CELLPHONE, phone);
//			if(userId > 0) {
//				valid = false;
//				validateMessageKey = "mobile.user.reg.phone.exist";
//			}
//		}
//		// validate password
//		String password = record.getStringValue(ShareFields.PASSWORD, "");
//		if(valid) {
//			if(StringUtils.isBlank(password)) {
//				valid = false;
//				validateMessageKey = "mobile.user.reg.password.empty";
//			}
//			else if(password.length() < 6) {
//				valid = false;
//				validateMessageKey = "mobile.user.reg.password.length.invalid";
//			}
//		}
//		
//		record.setValue(ShareFields.CODE_TYPE, IdentifyingCodeType.REGISTER.toString());
		// Validate image code
//		if(valid) {
//			Record imgRecord = IdentifyingCodeHelper.verifyImageCode(getAccessTokenId(), record);
//			valid = imgRecord.getBooleanValue(SUCCESS);
//			if(valid) {
//				validateMessageKey = "";
//			}
//			else {
//				validateMessageKey = imgRecord.getStringValue(MESSAGE);
//			}
//		}
		// validate SMS code, no need to validate image code
//		if(valid) {
//			Record smsRecord = IdentifyingCodeHelper.verifySMSCode(tokenId, record);
//			valid = smsRecord.getBooleanValue(SUCCESS);
//			if(valid) {
//				validateMessageKey = "";
//			}
//			else {
//				validateMessageKey = smsRecord.getStringValue(MESSAGE);
//			}
//		}
		
//		return validateMessageKey;
//	}
	
	
	/**
	 * Forget validation
	 * 
	 * @param record
	 * @return error message
	 */
//	public static String forgetValidation(Record record, String tokenId, UserManager userManager) {
//		boolean valid = true;
//		String validateMessageKey = "";
//		// Validate cell phone
//		String phone = record.getStringValue(ShareFields.CELL_PHONE, "");
//		if(StringUtils.isBlank(phone) || !ValidationUtils.isCellPhone(phone)){
//			valid = false;
//			validateMessageKey = "mobile.user.forget.password.phone.invalid";
//		}
//		else {
//			User user = userManager.getUserByPhone(phone);
//			if(user == null || user.getUserId() <= 0) {
//				valid = false;
//				validateMessageKey = "mobile.user.forget.password.phone.not.reg";
//			}
//			else {
//				// 将UserId放入inputRecord
//				record.setValue(ShareFields.USER_ID, user.getUserId());
//			}
//		}
//		// Validate password
//		String password = record.getStringValue(ShareFields.PASSWORD, "");
//		if(valid) {
//			if(StringUtils.isBlank(password)) {
//				valid = false;
//				validateMessageKey = "mobile.user.forget.password.password.empty";
//			}
//			else if(password.length() < 6) {
//				valid = false;
//				validateMessageKey = "mobile.user.forget.password.password.length.invalid";
//			}
//			else {
//				String confirmedPassword = record.getStringValue(ShareFields.CONFIRMED_PASSWORD, "");
//				if(!password.endsWith(confirmedPassword)) {
//					valid = false;
//					validateMessageKey = "mobile.user.forget.password.password.inconsistent";
//				}
//			}
//		}
//
//		// Validate SMS code
////		if(valid) {
////			record.setValue(ShareFields.CODE_TYPE, IdentifyingCodeType.FORGET_PASSWORD.toString());
////			Record imgRecord = IdentifyingCodeHelper.verifySMSCode(tokenId, record);
////			valid = imgRecord.getBooleanValue(SUCCESS);
////			if(!valid) {
////				validateMessageKey = imgRecord.getStringValue(MESSAGE);
////			}
////		}
//		
////		if(valid) {
////			int userId = record.getIntegerValue(ShareFields.USER_ID);
////			// 检查今天是否超过最大修改密码次数
////			if(userManager.canChangePasswordToday(userId)) {
////				valid = true;
////				validateMessageKey = "mobile.user.forget.password.phone.valid";
////			}
////			else {
////				valid = false;
////				validateMessageKey = "mobile.user.forget.password.cannot.change.today";
////			}
////		}
//		return validateMessageKey;
//	}
}
