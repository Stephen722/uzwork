package com.uzskill.mobile.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.DESUtils;
import com.light.utils.RequestUtils;
import com.light.utils.StringUtils;
import com.light.utils.ValidationUtils;
import com.uzskill.base.message.MessageManager;
import com.uzskill.base.user.UserManager;
import com.uzskill.base.user.UserQuery;
import com.uzskill.base.user.UserUniqueAttribute;
import com.uzskill.base.user.bean.User;
import com.uzskill.mobile.action.MobileBaseAction;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * User action
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-14
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserAction extends MobileBaseAction {
	
	private UserManager userManager;
	private MessageManager messageManager;
	
	private static final Logger logger = LogManager.getLogger(UserAction.class);
	
	/**
	 * Retrieve user by ID (encrypt).
	 * 
	 * Support parameters:
	 * - userId, the encrypt user id.
	 */
	public void get() {
		try{
			User user = null;
			// get the encrypt user ID
			String userIdStr = getInRecord().getStringValue(UserQuery.USER_ID, "");
			int	userId = DESUtils.getIntDecrypt(userIdStr);
			
			if(userId > 0) {
				user = getUserManager().getUserById(userId);
				if(user == null || user.getUserId() <=0) {
					logger.error("Retrieving empty user with ID: " + userId);
				}
			}
			else {
				logger.error("Retrieving user information with invalid ID: " + userId);
			}
			
			writeJSONResponse(user);
		}
		catch(Exception e) {
			handleAjaxError("Failed to retrieve user", e);
		}
	}
	
	/**
	 * Retrieves user friend list.
	 * 
	 */
	public void friends() {
		try{
			int userId = getAccessUserId();
			ResultList<User> userRS = getUserManager().getAllFriends(userId);
			
			writeJSONResponse(userRS);
		}
		catch(Exception e) {
			handleAjaxError("Failed to retrieve user friend list", e);
		}
	}

	/**
	 * Make friends
	 */
	public void invite() {
		try {
			int code = 0;
			String message = "mobile.user.invite.valid";
			int userId = getAccessUserId();
			if(userId <= 0) {
				message = "mobile.user.invite.no.login";
			}
			else {
				Record inRecord = this.getInRecord();
				String userIdStr = inRecord.getStringValue(UserQuery.USER_ID_STR, "");
				int	toUserId = DESUtils.getIntDecrypt(userIdStr);
				if(toUserId > 0) {
					code = getUserManager().makeFriends(userId, toUserId);
					message = "mobile.user.invite.no.login";
				}
			}
			
			writeJSONResponse(code, message);
		}
		catch(Exception e) {
			handleAjaxError("Failed to check user online", e);
		}
	}

	/**
	 * Accept friends
	 */
	public void accept() {
		try {
			int code = 0;
			String message = "mobile.user.invite.valid";
			int userId = getAccessUserId();
			if(userId <= 0) {
				message = "mobile.user.invite.no.login";
			}
			else {
				Record inRecord = this.getInRecord();
				String userIdStr = inRecord.getStringValue(UserQuery.USER_ID_STR, "");
				int	fromUserId = DESUtils.getIntDecrypt(userIdStr);
				if(fromUserId > 0) {
					code = getUserManager().acceptFriends(fromUserId, userId);
					message = "mobile.user.invite.no.login";
				}
			}
			
			writeJSONResponse(code, message);
		}
		catch(Exception e) {
			handleAjaxError("Failed to check user online", e);
		}
	}
	
	/**
	 * Check the access user is online or not
	 */
	public void online() {
		try {
			boolean success = false;
			String message = "mobile.user.not.online";
			int userId = getAccessUserId();
			if(userId > 0) {
				success = true;
				message = "mobile.user.online";
			}
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message));
		}
		catch(Exception e) {
			handleAjaxError("Failed to check user online", e);
		}
	}
	
	/**
	 * Check the phone number has registered or not.
	 * 
	 * true: this number has registered already.
	 * false: means this number is invalid or doesn't exist.
	 */
	public void phone() {
		try {
			boolean success = false;
			String message = "mobile.user.cellPhone.invalid";
			Record inRecord = getInRecord();
			// verify the image code firstly
//			Record imageRecord = IdentifyingCodeHelper.verifyImageCode(getAccessTokenId(), inRecord);
//			success = imageRecord.getBooleanValue(SUCCESS);
//			message = imageRecord.getStringValue(MESSAGE);
//			if(success) {
//				
//			}
			String cellPhone = inRecord.getStringValue(UserQuery.PHONE);
			if(!StringUtils.isBlank(cellPhone) && ValidationUtils.isPhone(cellPhone)) {
//				int userId = getUserManager().getUserId(UserUniqueAttribute.CELLPHONE, cellPhone);
//				if(userId > 0) {
//					success = true;
//					message = "mobile.user.cellPhone.reg";
//				}
//				else {
//					success = false;
//					message = "mobile.user.cellPhone.not.reg";
//				}
			}
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message));
		}
		catch(Exception e) {
			handleAjaxError("Failed to check phone", e);
		}
	}
	
	/**
	 * User login
	 */
	public void login() {
		try {
			int failedTimes = 1;
			User user = null;
			boolean success = false;
			String message = "mobile.user.login.fail";
			Record inRecord = getInRecord();
			String tokenId = getAccessTokenId();
			String validateMessageKey = UserActionHelper.loginValidation(inRecord, tokenId);
			if(StringUtils.isBlank(validateMessageKey)) {
				String ip = RequestUtils.getIpAddress(request);
				String phone = inRecord.getStringValue(UserQuery.PHONE);
//				String password = inRecord.getStringValue(ShareFields.PASSWORD); // it has been encrypted in client
//				// md5 + salt: 
//				String passwordSalt = "{"+cellPhone+"}" + password;
//				String md5PWD = StringUtils.getMd5String(passwordSalt);
				User inUser = new User();
				inUser.setPhone(phone);
				inUser.setPassword("");
				inUser.setLoginIp(ip);
				user = getUserManager().login(inUser);
				if(user != null && user.getUserId() > 0) {
					// only active user can login
					if(user.getActiveB() > 0) {
						success = true;
						message = "mobile.user.login.success";
						logger.debug("The user ({}) logins successfully", phone);
					}
					else {
						success = false;
						message = "mobile.user.login.inactive";
						logger.warn("The inactive user ({}) tries to login", phone);
					}
				}
				else {
					success = false;
					message = "mobile.user.login.fail";
					logger.warn("Failed to login {}", phone);
				}
			}
			else {
				success = false;
				message = validateMessageKey;
				logger.warn("Login information is invalid");
			}
			
			// output user if login successfully
			if(success) {
				// only output token id rather than other attributes, because token contains sensitive and important user ID.
				AccessToken newToken = AccessTokenProcessor.getInstance().initializeToken(user.getUserId(), request);
				String newTokenId = newToken.getId();
				// output user and newToken, the access token must be updated after login successfully.
				Record outRecord = new Record();
				outRecord.setValue("user", user);
				outRecord.setValue("newToken", newTokenId);
				writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message), outRecord.getRecordMap());
			}
			else {
				writeJSONResponse(success, ApplicationContext.getInstance().getMessage(message), failedTimes);
			}
		}
		catch(Exception e) {
			handleAjaxError("Failed to login", e);
		}
	}
	
	/**
	 * If the login user tries to logout, system deletes all the token data and generates a new guest token.
	 * If a guest tries to logout, system does not do anything.
	 * 
	 * @return String
	 */
	public void logout() {
		try {
			boolean success = false;
			String message = "failed to logout";
			String newTokenId = "";
			AccessToken token = getAccessToken();
			if(token.getUserId() > 0) {
				newTokenId = AccessTokenProcessor.getInstance().initializeToken(0, request).getId();
				success = true;
				message = "logout successfully";
			}
			writeJSONResponse(success, message, newTokenId);
		}
		catch(Exception e) {
			handleAjaxError("Failed to logout", e);
		}
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	
	
	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}
}
