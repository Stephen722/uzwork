package com.uzskill.admin.login;

import org.apache.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.identifying.ImageCodeProcessor;
import com.light.identifying.bean.IdentifyingCode;
import com.light.record.Record;
import com.light.struts.action.TokenProcessor;
import com.light.utils.StringUtils;
import com.light.utils.RequestUtils;
import com.uzskill.admin.action.AdminBaseAction;
import com.uzskill.admin.helper.AdminHelper;
import com.uzskill.admin.user.AdminUser;
import com.uzskill.admin.user.AdminUserManager;
import com.uzskill.base.user.UserQuery;

/**
 * 管理员登录Action类
 *
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-10-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class AdminLoginAction extends AdminBaseAction {
	
	private AdminUserManager adminUserManager;
	private static final String IMAGE_CODE = "adminLoginImageCode";
	private static final Logger logger = Logger.getLogger(AdminLoginAction.class);
	
	/**
	 * 加载登录页
	 * 
	 * @return
	 */
	public String execute() {
		String forward = INPUT;
		try {
			AdminUser onlineAdmin = getUserSession().get(AdminHelper.ONLINE_ADMIN);
			if(onlineAdmin != null && onlineAdmin.getAdminId() > 0) {
				forward = SUCCESS;
			}
			else {
				// 生成Token
				TokenProcessor.getInstance().initializeToken(session);
			}
		}
		catch(Exception e) {
			forward = handleError("Failed to access login page", e);
		}
		return forward;
	}
	
	/**
	 * 登录
	 * 
	 * @return
	 */
	public String login() {
		String forward = SUCCESS;
		try {
			Record inRecord = getInRecord();
			String validateMessageKey = "";
			// 验证Token是否有效。如果登录信息验证不通过，需要继续使用该Token，所以暂时不删除Token，待登录成功后才删除Token
			if(TokenProcessor.getInstance().isTokenValid(request)) {
				validateMessageKey = validate(inRecord);
				if(StringUtils.isBlank(validateMessageKey)) {
					String ip = RequestUtils.getIpAddress(request);
					String username = inRecord.getStringValue(UserQuery.USERNAME);
					String md5PWD = StringUtils.getMd5String(inRecord.getStringValue(UserQuery.PASSWORD));
					inRecord.setValue(UserQuery.PASSWORD,md5PWD);
					inRecord.setValue(UserQuery.LOGIN_IP, ip);
					AdminUser adminUser = getAdminUserManager().loginAdminUser(inRecord);
					if(adminUser != null && adminUser.getAdminId() > 0) {
						// 判断用户是否被锁定
						if(adminUser.getActiveB() > 0) {
							logger.info("Login successfully with username: " + username);
							// 当用户登录成功，更新其在线统计信息。系统将会自动覆盖原来未登录的所有信息
							getUserSession().set(AdminHelper.ONLINE_ADMIN, adminUser);
							// 删除Token
							TokenProcessor.getInstance().removeToken(session);
							getUserSession().remove(IMAGE_CODE);
						}
						else {
							logger.warn("Failed to login with inactive username: " + username);
							forward = INPUT;
							validateMessageKey = "admin.login.inactive";
						}
					}
					else {
						logger.warn("Failed to login with username: " + username);
						forward = INPUT;
						validateMessageKey = "admin.login.fail";
					}
					
				}
				else {
					logger.warn("Login information is invalid");
					forward = INPUT;
				}
			}
			else {
				logger.warn("Login Token is invalid");
				forward = INPUT;
				validateMessageKey = "token.invalid";
			}
			
			if (INPUT.equals(forward)) {
				publishMessage(ApplicationContext.getInstance().getMessage(validateMessageKey));
				publishAttribute(UserQuery.USERNAME, inRecord.getStringValue(UserQuery.USERNAME, ""));
				publishAttribute("UZWorkToken", inRecord.getStringValue("UZWorkToken"));
			}
		}
		catch(Exception e) {
			forward = handleError("Failed to login admin center", e);
		}
		return forward;
	}
	
	/**
	 * 用户注销登录的执行方法
	 * 系统只会注销已经登录的用户。注销该登录用户后，重新创建一个新的未登录访客。
	 * 对未登录的用户所执行的注销行为，系统直接跳转到首页。
	 * 
	 * @return String
	 */
	public String logout() {
		String forward = INPUT;
		try {
			// 系统只会注销已经登录的用户。注销该登录用户后，重新创建一个新的未登录访客。
			session.removeAttribute(AdminHelper.ONLINE_ADMIN);
//			UserSession userSession = getUserSession();
//			if(userSession != null && userSession.getUserId() > 0) {
//				 UserSessionManager.getInstance().removeUserSession(session);
//				 User guest = UserHelper.getOnlineUser();
//				 UserSessionManager.getInstance().initializeUserSession(session, RequestUtils.getIpAddress(request), guest);
//				 // 主动注销的用户，必须从Cookie中删除
//				 UserHelper.removeCookieUser(request, response);
//			}
		}
		catch(Exception e) {
			forward = handleError("Failed to logout", e);
		}
		return forward;
	}
	
	/**
	 * 此处用来生成图片验证码
	 */
	public void generateImageCode() {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			// 生成随机字串
			IdentifyingCode imageCode = ImageCodeProcessor.generateImageCode(4);
			getUserSession().set(IMAGE_CODE, imageCode.getCode());
			// 输出图片
			ImageCodeProcessor.sendOutImageCode(response.getOutputStream(), imageCode.getCode());
			
		} catch (Exception e) {
			handleAjaxError("Failed to generated image code", e);
		}
	}
	
	/**
	 * 登录验证
	 * 
	 * @param record
	 * @return 验证未通过对应的消息
	 */
	private String validate(Record record) {
		String validateMessageKey = "";
		String username = record.getStringValue(UserQuery.USERNAME, "");
		String password = record.getStringValue(UserQuery.PASSWORD, "");
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			validateMessageKey = "admin.login.username.password.empty";
		}
		else {
			String inputImageCode = record.getStringValue("imageVerifyCode");
			String usImageCode = getUserSession().get(IMAGE_CODE);
			if(StringUtils.isBlank(inputImageCode) || StringUtils.isBlank(usImageCode) || !inputImageCode.equalsIgnoreCase(usImageCode)) {
				validateMessageKey = "admin.login.imageCode.invalid";
			}
		} 
		return validateMessageKey;
	}

	public AdminUserManager getAdminUserManager() {
		return adminUserManager;
	}

	public void setAdminUserManager(AdminUserManager adminUserManager) {
		this.adminUserManager = adminUserManager;
	}
}
