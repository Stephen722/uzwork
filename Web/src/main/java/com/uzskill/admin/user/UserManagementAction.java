package com.uzskill.admin.user;

import com.light.app.ApplicationContext;
import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.StringUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.message.MessageManager;
import com.uzskill.base.message.bean.Message;
import com.uzskill.base.user.UserManager;
import com.uzskill.base.user.UserQuery;
import com.uzskill.base.user.bean.User;
import com.uzskill.base.user.bean.UserStatus;
import com.uzskill.admin.action.AdminBaseAction;

/**
 * 会员管理相关操作Action
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-11-12
 * 
 * @author  Chuantao Yang
 * @version UZWork-Base 1.0
 */
public class UserManagementAction extends AdminBaseAction {
	
	private UserManager userManager;
	private MessageManager messageManager;
	private AdminUserManager adminUserManager;
	
	/**
	 * 默认执行方法，获取相关用户列表
	 * 
	 * @return
	 */
	public String execute() {
		String forward = SUCCESS;
		try {
			Record inputRecord = getInRecord();
			String keyWords = inputRecord.getStringValue("keywords");
			if (!StringUtils.isBlank(keyWords)) {
				inputRecord.setValue("searchStartDate", null);
				inputRecord.setValue("searchEndDate", null);
			}
			if("all".equalsIgnoreCase(inputRecord.getStringValue("statusId"))) {
				inputRecord.remove("statusId");
			}
			if("all".equalsIgnoreCase(inputRecord.getStringValue("activeB"))) {
				inputRecord.remove("activeB");
			}
//			ResultList<User> userList = getUserManager().getAllUser(inputRecord, true);
//			publishAttribute("userList", userList.getResults());
//			publishAttribute("totalAmount", userList.getTotal());
//			inputRecord.setValue(Constants.PAGE_RECORD_TOTAL, userList.getTotal());
//			publishOutRecord(inputRecord);
//			publishPageInfo(inputRecord);
			
		} catch (Exception e) {
			forward = handleError("Failed to access user management in admin center", e);
		}
		return forward;
	}
	
	/**
	 * 加载提交审核的用户
	 * 
	 * @return
	 */
	public String review_list() {
		String forward = "review-list";
//		try {
//			Record inputRecord = getInRecord();
//			String keyWords = inputRecord.getStringValue("keywords");
//			if (!StringUtils.isBlank(keyWords)) {
//				inputRecord.setValue("searchStartDate", null);
//				inputRecord.setValue("searchEndDate", null);
//			}
//			ResultList<User> reviewList = getUserManager().getAllReviewUser(inputRecord);
//			publishAttribute("reviewList", reviewList.getResults());
//			publishAttribute("totalAmount", reviewList.getTotal());
//			inputRecord.setValue(ShareFields.PAGE_RECORD_TOTAL, reviewList.getTotal());
//			publishOutRecord(inputRecord);
//			publishPageInfo(inputRecord);
//		} catch (Exception e) {
//			forward = handleError("Failed to access user review page in admin center", e);
//		}
		return forward;
	}
	
	/**
	 * 加载用户个人信息，用于用户审核
	 * @return
	 */
	public String review() {
		String forward = "review";
//		try {
//			int userId = getInRecord().getIntegerValue(UserQuery.USER_ID, 0);
//			User user = getUserManager().getReviewUserById(userId);
//			publishAttribute("user", user);
//		} catch (Exception e) {
//			forward = handleError("Failed to access user info detail in admin center", e);
//		}
		return forward;
	}
	
	/**
	 * 插入用户审核记录
	 * 
	 * @return
	 */
	public void review_post() {
		try {
			boolean success = true;
			String messageKey = "admin.user.review.post.success";
			Record inRecord = this.getInRecord();
			int status = inRecord.getIntegerValue(CommonFields.STATUS, 0);
			int curUserId = inRecord.getIntegerValue("curUserId", 0);
			int curStatusId = inRecord.getIntegerValue("curStatusId", 0);
			int curReviewId = inRecord.getIntegerValue("curReviewId", 0);
			String comment = inRecord.getStringValue("comment");
			String reason = inRecord.getStringValue("reason");
			int toStatus = 0;
			// 只有待认证、未通过认证状态才可以修改审核状态
			if(curStatusId == UserStatus.CHECKING.getId() || curStatusId == UserStatus.UNQUALIFIED.getId()) {
				switch (status) {
					case 0: toStatus = curStatusId; break;
					case 1: toStatus = UserStatus.QUALIFIED.getId(); break;
					case 2: toStatus = UserStatus.UNQUALIFIED.getId(); break;
					default: 
						success = false;
						messageKey = "admin.user.review.post.status.invalid";
					break;
				}
			}
			else {
				success = false;
				messageKey = "admin.user.review.post.curStatus.invalid";
			}
			// validate user
			if(success) {
				if(curUserId <= 0) {
					success = false;
					messageKey = "admin.user.review.post.curUser.invalid";
				}
			}
			
			// validate review id
			if(success) {
				if(curReviewId <= 0) {
					success = false;
					messageKey = "admin.user.review.post.curReview.invalid";
				}
			}
			
			// validate comment
			if(success) {
				if(!StringUtils.isBlank(comment) && comment.length() > 200) {
					success = false;
					messageKey = "admin.user.review.post.comment.invalid";
				}
			}
			
			// validate reason
			if(success) {
				if(status == 2 && (StringUtils.isBlank(reason) || reason.length() > 200)) {
					success = false;
					messageKey = "admin.user.review.post.reason.invalid";
				}
			}
			
			// insert a new review history
			if(success) {
				int adminId = this.getAdminUserId();
				inRecord.setValue("adminId", adminId);
				inRecord.setValue("userId", curUserId);
				inRecord.setValue("reviewId", curReviewId);
				inRecord.setValue("fromStatus", curStatusId);
				inRecord.setValue("toStatus", toStatus);
//				int rows = this.getUserManager().insertUserReviewHistory(inRecord);
//				if(rows <= 0) {
//					success = false;
//					messageKey = "admin.user.review.post.fail";
//				}
//				else {
//					// 更新项目状态为toStatus
//					if(toStatus > 0) {
//						int update = this.getUserManager().updateReviewUserStatus(curUserId, curReviewId, UserStatus.get(toStatus));
//						if(update <= 0) {
//							success = false;
//							messageKey = "admin.user.review.post.fail";
//						}
//						else {
//							// 发送消息
//							String messageString = "您所提交的用户信息审核未通过，原因：" + reason;
//							if(UserStatus.get(toStatus).equals(UserStatus.QUALIFIED)) {
//								messageString = "恭喜您，您所提交的用户信息已通过审核。";
//							}
//							
//							Message message = new Message();
//							message.setUserId(curUserId);
//							message.setTitle("用户信息审核结果");
//							message.setMessage(messageString);
//							message.setType(0);
//							message.setSourceId(0);
//							this.getMessageManager().insertMessage(message);
//						}
//					}
//				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to insert a new user review history", e);
		}
	}
	
	/**
	 * 加载审核纪录
	 * 
	 * @return
	 */
	public String review_history() {
		String forward = "review-history";
		try {
			Record inputRecord = getInRecord();
			String keyWords = inputRecord.getStringValue("keywords");
			if (!StringUtils.isBlank(keyWords)) {
				inputRecord.setValue("searchStartDate", null);
				inputRecord.setValue("searchEndDate", null);
			}
//			ResultList hisList = getUserManager().getUserReviewHistory(inputRecord);
//			publishAttribute("historyList", hisList.getResults());
//			publishAttribute("totalAmount", hisList.getTotal());
//			inputRecord.setValue(ShareFields.PAGE_RECORD_TOTAL, hisList.getTotal());
//			publishOutRecord(inputRecord);
//			publishPageInfo(inputRecord);
		} catch (Exception e) {
			forward = handleError("Failed to access user review histroy in admin center", e);
		}
		return forward;
	}

	/**
	 * 修改用户状态
	 */
	public void activate() {
		try {
			boolean success = false;
			String messageKey = "admin.user.activate.fail";
			Record inRecord = this.getInRecord();
			int userId = inRecord.getIntegerValue(UserQuery.USER_ID, 0);
			int activeB = inRecord.getIntegerValue(UserQuery.ACTIVE_B, 0);
			if(userId > 0) {
				int rows = this.getUserManager().activateUser(userId, activeB);
				if(rows > 0) {
					success = true;
					messageKey = "admin.user.activate.success";
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to activate/deactivate user", e);
		}
	}
	
	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public AdminUserManager getAdminUserManager() {
		return adminUserManager;
	}

	public void setAdminUserManager(AdminUserManager adminUserManager) {
		this.adminUserManager = adminUserManager;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}
	
}
