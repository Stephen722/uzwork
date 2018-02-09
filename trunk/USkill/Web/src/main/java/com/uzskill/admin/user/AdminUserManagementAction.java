package com.uzskill.admin.user;

import com.light.app.ApplicationContext;
import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.StringUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.user.UserQuery;
import com.uzskill.admin.action.AdminBaseAction;

/**
 * 管理员相关操作Action
 * 
 * <p>(C) 2017 www.uzskill.com (UZWORK)</p>
 * Date:  2017-06-21
 * 
 * @author  Stephen Yang
 * @version UZWORK-Base 1.0
 */
public class AdminUserManagementAction extends AdminBaseAction {
	
	private AdminUserManager adminUserManager;
	
	/**
	 * 默认执行方法，获取管理员列表
	 * 
	 * @return
	 */
	public String execute() {
		String forward = SUCCESS;
		
		try {
			Record inputRecord = getInRecord();
			ResultList<AdminUser> userList = getAdminUserManager().getAllAdminUser(inputRecord);
			publishAttribute("adminList", userList.getResults());
			publishAttribute("totalAmount", userList.getTotal());
			publishOutRecord(inputRecord);
			inputRecord.setValue(Constants.PAGE_RECORD_TOTAL, userList.getTotal());
			publishPageInfo(inputRecord);
			
		} catch (Exception e) {
			forward = handleError("Failed to access admin user management in admin center", e);
		}
		return forward;
	}
	
	/**
	 * 修改管理员状态
	 */
	public void activate() {
		try {
			boolean success = false;
			String messageKey = "admin.adminUser.activate.fail";
			Record inRecord = this.getInRecord();
			int adminId = inRecord.getIntegerValue(CommonFields.ADMIN_ID, 0);
			int activeB = inRecord.getIntegerValue(UserQuery.ACTIVE_B, 0);
			if(adminId > 0) {
				int adminUserId = getAdminUserId();
				if(adminId == adminUserId) {
					success = false;
					messageKey = "admin.adminUser.activate.self";
				}
				else {
					int rows = this.getAdminUserManager().activateAdminUser(adminId, activeB);
					if(rows > 0) {
						success = true;
						messageKey = "admin.adminUser.activate.success";
					}
				}
				
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to activate admin user", e);
		}
	}
	
	/**
	 * 删除管理员
	 */
	public void deleteAdminUser() {
		try {
			boolean success = false;
			String messageKey = "admin.adminUser.delete.fail";
			Record inRecord = this.getInRecord();
			int adminId = inRecord.getIntegerValue(CommonFields.ADMIN_ID, 0);
			if(adminId > 0) {
				int adminUserId = getAdminUserId();
				if(adminUserId > 0) {
					if(adminId == adminUserId) {
						success = false;
						messageKey = "admin.adminUser.delete.self";
					}
					else {
						int rows = this.getAdminUserManager().deleteAdminUser(adminId);
						if(rows > 0) {
							success = true;
							messageKey = "admin.adminUser.delete.success";
						}
					}
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to delete admin user", e);
		}
	}
	
	public String add() {
		return "add";
	}
	
	public String password() {
		return "password";
	}
	
	/**
	 * 新增管理员
	 */
	public void insertAdminUser() {
		try {
			boolean success = true;
			String messageKey = "admin.adminUser.add.success";
			Record inRecord = this.getInRecord();
			String username = inRecord.getStringValue(UserQuery.USERNAME, "");
			String password = inRecord.getStringValue(UserQuery.PASSWORD, "");
			String confirmPassword = inRecord.getStringValue(UserQuery.CONFIRMED_PASSWORD, "");
			if(StringUtils.isBlank(username) || username.length() < 3) {
				success = false;
				messageKey = "admin.adminUser.add.username.invalid";
			}
			else if(StringUtils.isBlank(password) || password.length() < 6) {
				success = false;
				messageKey = "admin.adminUser.add.password.invalid";
			}
			else if(!password.equals(confirmPassword)) {
				success = false;
				messageKey = "admin.adminUser.add.password.inconsistent";
			}
			else {
				AdminUser admin = this.getAdminUserManager().getAdminUser(username);
				if(admin == null || admin.getAdminId() <= 0) {
					int createdBy = getAdminUserId();
					inRecord.setValue(UserQuery.CREATED_BY, createdBy);
					
					// 密码加密
					String pwd = StringUtils.getMd5String(password);
					inRecord.setValue(UserQuery.PASSWORD, pwd);
					
					int rows = this.getAdminUserManager().insertAdminUser(inRecord);
					if(rows <= 0) {
						success = false;
						messageKey = "admin.adminUser.add.fail";
					}
				}
				else {
					success = false;
					messageKey = "admin.adminUser.add.username.exist";
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to insert a new admin user", e);
		}
	}
	
	/**
	 * 修改密码
	 */
	public void changePassword() {
		try {
			boolean success = true;
			String messageKey = "admin.adminUser.change.password.success";
			Record inRecord = this.getInRecord();
			String oldPassword = inRecord.getStringValue("oldPassword", "");
			String password = inRecord.getStringValue(UserQuery.PASSWORD, "");
			String confirmPassword = inRecord.getStringValue(UserQuery.CONFIRMED_PASSWORD, "");
			if(StringUtils.isBlank(oldPassword) || oldPassword.length() < 6) {
				success = false;
				messageKey = "admin.adminUser.change.oldPassword.invalid";
			}
			else if(StringUtils.isBlank(password) || password.length() < 6) {
				success = false;
				messageKey = "admin.adminUser.change.password.invalid";
			}
			else if(!password.equals(confirmPassword)) {
				success = false;
				messageKey = "admin.adminUser.change.password.inconsistent";
			}
			else {
				// admin user id
				AdminUser admin = this.getAdminUser();
				if(admin.getAdminId() > 0) {
					// 验证原始密码
					String oldPwd = StringUtils.getMd5String(oldPassword);
					String username = admin.getUsername();
					Record record = new Record();
					record.setValue(UserQuery.USERNAME, username);
					record.setValue(UserQuery.PASSWORD, oldPwd);
					AdminUser adminUser = this.getAdminUserManager().loginAdminUser(record);
					
					if(adminUser == null || adminUser.getAdminId() <= 0) {
						success = false;
						messageKey = "admin.adminUser.change.oldPassword.invalid";
					}
					else {
						// 密码加密
						String pwd = StringUtils.getMd5String(password);
						AdminUser adUser = new AdminUser();
						adUser.setAdminId(admin.getAdminId());
						adUser.setPassword(pwd);
						int rows = this.getAdminUserManager().changePassword(adUser);
						if(rows <= 0) {
							success = false;
							messageKey = "admin.adminUser.change.password.fail";
						}
					}
				}
				else {
					success = false;
					messageKey = "admin.adminUser.change.password.fail";
				}
			}
			
			writeJSONResponse(success, ApplicationContext.getInstance().getMessage(messageKey));
		}
		catch(Exception e) {
			handleAjaxError("Failed to change admin user password", e);
		}
	}

	public AdminUserManager getAdminUserManager() {
		return adminUserManager;
	}

	public void setAdminUserManager(AdminUserManager adminUserManager) {
		this.adminUserManager = adminUserManager;
	}
	
}
