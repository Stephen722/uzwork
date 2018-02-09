package com.uzskill.admin.action;

import com.uzskill.admin.action.AdminBaseAction;

/**
 * Admin Center Main相关操作Action
 * 
 * <p>(C) 2017 www.uzskill.com (WARNER)</p>
 * Date:  2017-09-21
 * 
 * @author  Stephen Yang
 * @version UZWORK-Base 1.0
 */
public class AdminMainAction extends AdminBaseAction {
	
	public String execute() {
		String forward = SUCCESS;
		try {
			
		} catch (Exception e) {
			forward = handleError("Failed to access admin center main page", e);
		}
		return forward;
	}

}
