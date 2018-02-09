package com.uzskill.mobile.init;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.record.Record;
import com.light.utils.RequestUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.message.MessageManager;
import com.uzskill.base.site.SiteManager;
import com.uzskill.base.user.UserManager;
import com.uzskill.base.user.UserQuery;
import com.uzskill.base.user.bean.User;
import com.uzskill.mobile.action.MobileBaseAction;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * Initialization action for all clients.
 * 
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class InitializationAction extends MobileBaseAction {

	private SiteManager siteManager;
	private UserManager userManager;
	private MessageManager messageManager;
	private static final Logger logger = LogManager.getLogger(InitializationAction.class);
	
	/**
	 * Initialization for all clients.
	 * 
	 * If the user is inactive, system regenerates a new access token, the clients must replace the previous access token by the new one.
	 * 
	 * Support parameters:
	 * - versionId, the current version ID of APP (not for browser), it is used to check the latest version.
	 */
	public void execute() {
		try{
			Record inRecord = getInRecord();
			Record initRecord = new Record();
			// check version only for APP
			if(inRecord.hasStringValue(CommonFields.VERSION_ID)) {
				logger.debug("Initialization for APP from IP:" + RequestUtils.getIpAddress(request));
				Record versionRecord = getSiteManager().getLatestVersion(inRecord);
				initRecord.setRecord(versionRecord);
			}
			else {
				logger.debug("Initialization for browser from IP:" + RequestUtils.getIpAddress(request));
			}
			
			// get the latest user information, only for active user.
			int userId = getAccessUserId();
			if(userId > 0) {
				// it'd better retrieve user firstly to make sure the user is valid
				User user = getUserManager().getUserById(userId);
				if(user != null && user.getUserId() > 0) {
					if(user.getActiveB() > 0) {
						initRecord.setValue("user", user);
						// synchronize system message(s)
						int messages = getMessageManager().synchronizeMessage(userId);
						if(messages > 0) {
							// update unread message number
							Record record = new Record();
							record.setValue(UserQuery.USER_ID, userId);
							record.setValue(UserQuery.MESSAGES, messages);
//							getUserManager().updateUserUnReadMessageNumber(record);
							
							int unReadMsg = user.getMessages();
							user.setMessages(unReadMsg + messages);
						}
						logger.info("User login: {}", user.getPhone());
					}
					else {
						// the previous access token need to be updated since the user's status (inactive) is different
						AccessToken newToken = AccessTokenProcessor.getInstance().initializeToken(0, request);
						initRecord.setValue("newToken", newToken.getId());
						
						logger.error("The inactive user({}) tries to start APP, please make sure it has been kicked out from APP", user.getPhone());
					}
				}
			}
			writeJSONResponse(initRecord.getRecordMap());
		}
		catch(Exception e) {
			handleAjaxError("Failed to initialize APP", e);
		}
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public SiteManager getSiteManager() {
		return siteManager;
	}

	public void setSiteManager(SiteManager siteManager) {
		this.siteManager = siteManager;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

}