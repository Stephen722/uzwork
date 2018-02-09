package com.uzskill.admin.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.uzskill.base.user.UserSession;
import com.uzskill.base.user.UserSessionManager;
import com.uzskill.admin.helper.AdminHelper;
import com.uzskill.admin.user.AdminUser;

/**
 * 管理中心拦截器
 *
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-10-22
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class AdminCenterInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AdminCenterInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
    		HttpServletRequest request = ServletActionContext.getRequest();
		UserSession userSession = UserSessionManager.getInstance().getUserSession(request.getSession());
		AdminUser onlineAdmin = null;
		if(userSession != null) {
			onlineAdmin = userSession.get(AdminHelper.ONLINE_ADMIN);
		}
		
		String actionFullName = invocation.getAction().getClass().getName();
		// 如果是排除的Action或者当前用户已经登录，那么继续执行，否则跳转到登录页面。
		if(actionFullName.endsWith("AdminLoginAction")) {
			return invocation.invoke();
		}
		else if(onlineAdmin != null && onlineAdmin.getAdminId() > 0) {
//			int adminType = onlineAdmin.getType();
//			if(0 == adminType && (actionFullName.endsWith("AdminUserManagementAction") || actionFullName.endsWith("UserManagementAction"))) {
//				return "forbidden";
//			}
//			else {
//				return invocation.invoke();
//			}
			return invocation.invoke();
		}
		else {
			logger.warn("A user is trying to access uzwork admin center without login");
			return "admin-login";	
		}
	}
}
