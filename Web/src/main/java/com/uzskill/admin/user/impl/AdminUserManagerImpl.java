package com.uzskill.admin.user.impl;

import java.util.List;

import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.user.UserQuery;
import com.uzskill.admin.user.AdminUser;
import com.uzskill.admin.user.AdminUserManager;
import com.uzskill.admin.user.AdminUserQuery;
/**
 * 管理员用户管理类，包括所有对管理员用户的操作。
 *
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-11-20
 * 
 * @author  Chuantao Yang
 * @version UZWork-Admin 1.0
 */
public class AdminUserManagerImpl extends BaseManager implements AdminUserManager {

	@Override
	public AdminUser getAdminUser(int userId) {
		return select(AdminUserQuery.GET_ADMIN_USER_BY_ID, userId);
	}

	@Override
	public AdminUser getAdminUser(String username) {
		return select(AdminUserQuery.GET_ADMIN_USER_BY_NAME, username);
	}

	@Override
	public ResultList<AdminUser> getAllAdminUser(Record record) {
		ResultList<AdminUser> rsList = new ResultList<AdminUser>();
		// 分页信息
		int page = record.getIntegerValue(Constants.PAGE, 0);
		int pageSize = record.getIntegerValue(Constants.PAGE_SIZE, 10);
		int total = select(AdminUserQuery.GET_ALL_ADMIN_USER_TOTAL, record);
		List<AdminUser> list = selectPageList(AdminUserQuery.GET_ALL_ADMIN_USER, record, page, pageSize);
		
		rsList.setTotal(total);
		rsList.setResults(list);
		return rsList;
	}

	@Override
	public AdminUser loginAdminUser(Record record) {
		// 关于用户名和密码的有效性已经验证过，这里不重复验证
		AdminUser adminUser = select(AdminUserQuery.LOGIN_ADMIN_USER, record);
				
		// 如果系统返回一个有效管理员用户，则更新其相关登录信息
		if(adminUser != null && adminUser.getAdminId() > 0) {
			adminUser.setLoginIp(record.getStringValue(UserQuery.LOGIN_IP));
			update(AdminUserQuery.UPDATE_LOGIN_INFORMATION, adminUser);
		}
		return adminUser;
	}

	@Override
	public int insertAdminUser(Record record) {
		AdminUser admin = new AdminUser();
		admin.setUsername(record.getStringValue(UserQuery.USERNAME));
		admin.setPassword(record.getStringValue(UserQuery.PASSWORD));
		admin.setActiveB(1);
		admin.setCreatedBy(record.getIntegerValue(UserQuery.CREATED_BY));
		admin.setGroupId(record.getIntegerValue("groupId"));
		return insert(AdminUserQuery.INSERT_ADMIN_USER, admin);
	}

	@Override
	public int updateAdminUser(Record record) {
		return update(AdminUserQuery.UPDATE_ADMIN_USER, record);
	}

	@Override
	public int changePassword(AdminUser user) {
		return update(AdminUserQuery.CHANGE_PASSWORD, user);
	}

	@Override
	public int deleteAdminUser(int adminId) {
		return delete(AdminUserQuery.DELETE_ADMIN_USER, adminId);
	}

	@Override
	public int activateAdminUser(int adminId, int activeB) {
		Record record = new Record();
		record.setValue(CommonFields.ADMIN_ID, adminId);
		record.setValue(UserQuery.ACTIVE_B, activeB);
		return update(AdminUserQuery.UPDATE_ADMIN_USER, record);
	}

}
