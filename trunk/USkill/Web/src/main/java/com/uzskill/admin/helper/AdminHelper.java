package com.uzskill.admin.helper;

import org.apache.log4j.Logger;

import com.uzskill.base.user.UserSession;
import com.uzskill.admin.user.AdminUser;

/**
 * 管理员帮助类
 *
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-10-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Admin 1.0
 */
public class AdminHelper {
	
	public static final String ONLINE_ADMIN = "onlineUZAdmin";
	
	/**
	 * 取得已登录管理员用户
	 * 
	 * @param UserSession
	 * @return AdminUser
	 */
    public static AdminUser getOnlineAdminUser(UserSession userSession) {
    	AdminUser adminUser = null;
    	if(userSession != null) {
    		adminUser = userSession.get(ONLINE_ADMIN);
    	}
    	if(adminUser == null) {
    		adminUser = new AdminUser();
    		adminUser.setAdminId(0);
    	}
    	return adminUser;
    }
}