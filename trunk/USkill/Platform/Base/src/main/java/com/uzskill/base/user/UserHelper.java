package com.uzskill.base.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.light.app.ApplicationContext;
import com.light.utils.CookieUtils;
import com.light.utils.RequestUtils;
import com.light.utils.StringUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.user.bean.User;
import com.uzskill.base.user.impl.UserManagerImpl;

/**
 * 用户帮助类
 *
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-08-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserHelper {

	private static final String COOKIE_USER_CELL_PHONE = "freeMakeUserCellPhone";
	private static final String COOKIE_USER_PASSWORD = "freeMakeUserPassword";
	public static final String REGISTER_PAGE = "register";
	public static final String LOGOUT_PAGE = "logout";
	public static final String RESET_PAGE = "reset";
	private static UserManager userManager;
	
	/**
	 * 初始化一个在线用户。
	 * 该request是用来取得cookie，其session对象为空，不可以用来构建HttpUserSession。
	 * 如果从cookie取得信息有效，系统将用该信息做登录验证。
	 * 
	 * @param request
	 */
	public static void initializeOnlineUser(HttpServletRequest request, HttpSession session){
		User user = null;
		String ip = "";
		if(request != null) {
			String cookieCellPhone = CookieUtils.getCookie(request, COOKIE_USER_CELL_PHONE);
			ip = RequestUtils.getIpAddress(request);
			if(!StringUtils.isBlank(cookieCellPhone)){
				// 这个是已经加密过的密码
				String cookiePassword = CookieUtils.getCookie(request, COOKIE_USER_PASSWORD);
				User inUser = new User();
				// fromCookieB用来表示当前登录来自cookie，密码已经被加密过。
				inUser.setPhone(cookieCellPhone);
				inUser.setPassword(cookiePassword);
				inUser.setLoginIp(ip);
				// 根据cookie里用户信息，将用户登录。
				user = getUserManager().login(inUser);
			}
		}
		User onlineUser = getOnlineUser(user);
		
		// 初始化一个UserSession
//		UserSessionManager.getInstance().initializeUserSession(session, ip, onlineUser);
	}
	
	/**
	 * 保存登录用户信息到本地
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void saveLoginUserLocal(HttpServletResponse response, String cellPhone, String password) {
		CookieUtils.setCookie(response, COOKIE_USER_CELL_PHONE, cellPhone);
		CookieUtils.setCookie(response, COOKIE_USER_PASSWORD, password);
	}
	
	/**
	 * 保存登录用户信息到本地
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void removeCookieUser(HttpServletRequest request, HttpServletResponse response) {
		CookieUtils.removeCookie(request, response, COOKIE_USER_CELL_PHONE);
		CookieUtils.removeCookie(request, response, COOKIE_USER_PASSWORD);
	}
	
	/**
	 * 构建一个在线用户
	 * 
	 * @return User
	 */
	public static User getOnlineUser() {
		return getOnlineUser(null);
	}
	
	/**
	 * 从onlineUser中取得用户部分信息，不需要用户所有信息。
	 * 如果onlineUser为空则初始化一个匿名用户。
	 * 如果onlineUser不为空则选其部分属性，判断其user name是否存在，如果不存在则设其值为cell phone
	 * 
	 * @param onlineUser
	 * @return User
	 */
	public static User getOnlineUser(User onlineUser) {
		User user = new User();
		if(onlineUser == null) {
			user.setUserId(0);
			user.setUsername("GUEST");
			user.setActiveB(1);
			user.setPhone("0");
		}
		else {
			user = onlineUser;
		}
		if(StringUtils.isBlank(user.getPicture())) {
			user.setPicture(CommonFields.USER_DEFAULT_ICON);
		}
		return user;
	}
	
	/**
	 * 取得UserManager
	 * 
	 * @return
	 */
	public static UserManager getUserManager() {
		if(userManager == null) {
			synchronized (UserManager.class) {
				if (ApplicationContext.getInstance().hasBean("UserManager")) {
					userManager = (UserManager) ApplicationContext.getInstance().getBean("UserManager");
		        }
		        else{
		        	userManager = new UserManagerImpl();
		        }
			}
		}
		return userManager;
	}

}
