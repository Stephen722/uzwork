package com.uzskill.base.user;

import com.light.record.ResultList;
import com.uzskill.base.user.bean.User;

/**
 * 用户管理类，包括所有对用户的操作。
 *
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface UserManager {
	
	/**
	 * 根据用户ID，取得用户所有信息
	 * 
	 * @param userId 用户Id
	 * @return User
	 */
	public User getUserById(int userId);
	
	/**
	 * 根据用户电话号码，取得用户所有信息。
	 * 
	 * @param phone 用户电话号码
	 * @return User
	 */
	public User getUserByPhone(String phone);
	
	/**
	 * 取得用户好友列表。
	 * 
	 * @param userId 
	 * @return 用户好友列表
	 */
	public ResultList<User> getAllFriends(int userId);
	
	/**
	 * 根据用户名和密码判断是否存在该用户，如果存在该用户，并且未被锁定，则直接更新该用户相关信息，并返回被更新条数。如果用户不存在，说明系统没有该用户或者用户名和密码中一个有错。
	 * 登录成功后，系统需要同步用户消息。
	 * 
	 * @param user
	 * @return 当前用户
	 */
	public User login(User user);
	
	/**
	 * 插入一个新用户
	 * 系统自动触发创建一个待提交审核记录
	 * 
	 * @param user
	 * @return 新用户Id
	 */
	public int insertUser(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(User user);
	
	/**
	 * 根据用户Id，删除用户
	 * 
	 * @param userId
	 * @return 影响条数
	 */
	public int deleteUser(int userId);
	
	/**
	 * 根据用户Id和是否激活用户，来激活或者锁定用户。
	 * 
	 * @param userId
	 * @param activeB 1:激活  0:锁定 
	 * @return 影响条数
	 */
	public int activateUser(int userId, int activeB);
	
	/**
	 * 添加好友
	 * 
	 * @return
	 */
	public int makeFriends(int fromUserId, int toUserId);
	
	/**
	 * 同意添加好友
	 * 
	 * @param fromUserId
	 * @param toUserId
	 * @return
	 */
	public int acceptFriends(int fromUserId, int toUserId);
}
