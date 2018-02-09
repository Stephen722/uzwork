package com.uzskill.admin.user;

import com.light.record.Record;
import com.light.record.ResultList;

/**
 * 管理员用户管理类，包括所有对管理员用户的操作。
 *
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-11-20
 * 
 * @author  Chuantao Yang
 * @version UZWork-Admin 1.0
 */
public interface AdminUserManager {
	
	/**
	 * 根据管理员用户ID，取得用户信息。
	 * 
	 * @param adminId 用户Id
	 * @return User
	 */
	public AdminUser getAdminUser(int adminId);
	
	/**
	 * 根据管理员用户名称，取得管理员用户信息。
	 * 
	 * @param username 用户名称
	 * @return User
	 */
	public AdminUser getAdminUser(String username);
	
	/**
	 * 根据指定页码，返回所有管理员用户。
	 * 
	 * @param page 页码
	 * @return 管理员用户列表
	 */
	public ResultList<AdminUser> getAllAdminUser(Record record);
	
	/**
	 * 根据管理员用户名和密码判断是否存在该管理员用户，如果存在该用户，则直接更新该用户相关信息，并返回被更新条数。
	 * 如果用户不存在，说明系统没有该管理员用户或者用户名和密码中一个有错。
	 * 
	 * @param record
	 * @return 当前管理员用户
	 */
	public AdminUser loginAdminUser(Record record);
	
	/**
	 * 插入一个新管理员用户。
	 * 
	 * @param record
	 * @return 新管理员用户Id
	 */
	public int insertAdminUser(Record record);
	
	/**
	 * 更新管理员用户。
	 * 
	 * @param user
	 * @return 更新条数
	 */
	public int updateAdminUser(Record record);
	
	/**
	 * 根据管理员用户信息修改密码。
	 * 
	 * @param user
	 * @return 更新条数
	 */
	public int changePassword(AdminUser user);
	
	/**
	 * 根据用户Id，删除管理员用户
	 * 
	 * @param adminId
	 * @return 影响条数
	 */
	public int deleteAdminUser(int adminId);
	
	/**
	 * 根据管理员用户Id和是否激活用户，来激活或者锁定管理员用户。
	 * 
	 * @param adminId
	 * @param activeB 1:激活  0:锁定 
	 * @return 影响条数
	 */
	public int activateAdminUser(int adminId, int activeB);

}
