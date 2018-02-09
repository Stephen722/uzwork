package com.uzskill.admin.feedback;

import java.util.Map;

import com.light.app.ApplicationContext;
import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.uzskill.base.site.SiteManager;
import com.uzskill.base.user.UserManager;
import com.uzskill.admin.action.AdminBaseAction;

/**
 * 用户反馈相关操作Action
 * 
 * <p>(C) 2017 www.uzskill.com (UZWORK)</p>
 * Date:  2017-06-21
 * 
 * @author  Stephen Yang
 * @version UZWORK-Base 1.0
 */
public class FeedbackManagementAction extends AdminBaseAction {
	
	private UserManager userManager;
	private SiteManager siteManager;
	
	/**
	 * 默认执行方法，获取反馈列表
	 * 
	 * @return
	 */
	public String execute() {
		String forward = SUCCESS;
		try {
			Record inRecord = getInRecord();
			ResultList<Map> fbList = this.getSiteManager().getAllFeedback(inRecord);
			publishAttribute("feedbackList", fbList.getResults());
			publishAttribute("totalAmount", fbList.getTotal());
			inRecord.setValue(Constants.PAGE_RECORD_TOTAL, fbList.getTotal());
			publishPageInfo(inRecord);
			
		} catch (Exception e) {
			forward = handleError("Failed to access feedback management in admin center", e);
		}
		return forward;
	}
	
	/**
	 * 删除
	 */
	public void delete() {
		try {
			boolean success = false;
			String messageKey = "admin.feedback.delete.fail";
			Record inRecord = this.getInRecord();
			int feedbackId = inRecord.getIntegerValue("feedbackId", 0);
			if(feedbackId > 0) {
				int rows = this.getSiteManager().deleteFeedback(feedbackId);
				if(rows > 0) {
					success = true;
					messageKey = "admin.feedback.delete.success";
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to delete feedback", e);
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
}
