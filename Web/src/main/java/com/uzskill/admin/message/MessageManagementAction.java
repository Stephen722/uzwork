package com.uzskill.admin.message;

import com.light.app.ApplicationContext;
import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.StringUtils;
import com.light.utils.ValidationUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.message.MessageManager;
import com.uzskill.base.message.bean.MessageSource;
import com.uzskill.base.user.UserManager;
import com.uzskill.base.user.UserQuery;
import com.uzskill.admin.action.AdminBaseAction;

/**
 * 通知相关操作Action
 * 
 * <p>(C) 2016 www.uzskill.com (UZWORK)</p>
 * Date:  2016-11-12
 * 
 * @author  Stephen Yang
 * @version UZWORK-Base 1.0
 */
public class MessageManagementAction extends AdminBaseAction {
	
	private UserManager userManager;
	private MessageManager messageManager;
	
	/**
	 * 取得通知列表
	 * 
	 * @return
	 */
	public String execute() {
		String forward = SUCCESS;
		try {
			Record inRecord = getInRecord();
			ResultList<MessageSource> msgList = this.getMessageManager().getAllMessageSource(inRecord);
			publishAttribute("messageList", msgList.getResults());
			publishAttribute("totalAmount", msgList.getTotal());
			publishOutRecord(inRecord);
			inRecord.setValue(Constants.PAGE_RECORD_TOTAL, msgList.getTotal());
			publishPageInfo(inRecord);
			
		} catch (Exception e) {
			forward = handleError("Failed to access message management in admin center", e);
		}
		return forward;
	}

	public String add() {
		return "add";
	}
	
	/**
	 * 发布新通知
	 */
	public void sendMsg() {
		try {
			boolean success = true;
			String messageKey = "admin.message.send.success";
			Record inRecord = this.getInRecord();
			String title = inRecord.getStringValue(CommonFields.MESSAGE_TITLE);
			String message = inRecord.getStringValue(CommonFields.MESSAGE);
			int destination = inRecord.getIntegerValue(CommonFields.MESSAGE_DESTINATION, 0);
			String cellPhones = inRecord.getStringValue(UserQuery.PHONE);
			String cellPhoneString = "";
			if(StringUtils.isBlank(title) || (title.length() < 3 || title.length() > 60)) {
				success = false;
				messageKey = "admin.message.send.title.invalid";
			}
			else if(StringUtils.isBlank(message) || (message.length() < 10 || message.length() > 1000)) {
				success = false;
				messageKey = "admin.message.send.message.invalid";
			}
			else if(destination == 1) {
				if(StringUtils.isBlank(cellPhones)) {
					success = false;
					messageKey = "admin.message.send.cellPhone.invalid";
				}
				else {
					if(cellPhones.indexOf("\n") > 0) {
						String[] cellPhoneArray = cellPhones.split("\n");
						if(cellPhoneArray.length > 20) {
							success = false;
							messageKey = "admin.message.send.cellPhone.too.long";
						}
						else {
							for(int i = 0; i < cellPhoneArray.length; i++) {
								String cellPhone = cellPhoneArray[i].trim();
								if(!ValidationUtils.isPhone(cellPhone)) {
									success = false;
									messageKey = "admin.message.send.cellPhone.invalid";
									break;
								}
								else {
									cellPhoneString += cellPhone + ",";
								}
							}
							if(cellPhoneString.endsWith(",")) {
								cellPhoneString = cellPhoneString.substring(0, cellPhoneString.lastIndexOf(","));
							}
						}
					}
					else if(ValidationUtils.isPhone(cellPhones)) {
						cellPhoneString = cellPhones;
					}
					else {
						success = false;
						messageKey = "admin.message.send.cellPhone.invalid";
					}
				}
			}
			if(success) {
				MessageSource messageSource = new MessageSource();
				messageSource.setAdminId(getAdminUserId());
				messageSource.setTitle(title);
				messageSource.setMessage(message);
				messageSource.setCellPhone(cellPhoneString);
				messageSource.setDestination(destination);
				int rows = this.getMessageManager().insertMessageSource(messageSource);
				if(rows <= 0) {
					success = false;
					messageKey = "admin.message.send.fail";
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to send message", e);
		}
	}
	
	/**
	 * 删除通知
	 */
	public void deleteMessage() {
		try {
			boolean success = false;
			String messageKey = "admin.message.delete.fail";
			Record inRecord = this.getInRecord();
			int msgId = inRecord.getIntegerValue(CommonFields.MESSAGE_ID, 0);
			if(msgId > 0) {
				int rows = this.getMessageManager().deleteMessageSource(msgId);
				if(rows > 0) {
					success = true;
					messageKey = "admin.message.delete.success";
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to delete message source", e);
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
