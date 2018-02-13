package com.uzskill.base.user.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.record.ResultList;
import com.light.utils.DateUtils;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.user.UserManager;
import com.uzskill.base.user.UserQuery;
import com.uzskill.base.user.bean.User;
/**
 * 用户管理类，包括所有对用户的操作。
 *
 * <p>(C) 2018 www.uzwork.com (UZWork)</p>
 * Date:  2018-01-25
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserManagerImpl extends BaseManager implements UserManager {
	
	private static final Logger logger = LogManager.getLogger(UserManagerImpl.class);
	
	@Override
	public User getUserById(int userId) {
		User user = getRedis().hGet(UserQuery.REDIS_USER_MAP, String.valueOf(userId), User.class);
		if(user == null) {
			user = select(UserQuery.GET_USER_BY_ID, userId);
			if(user != null && user.getUserId() > 0) {
				getRedis().hSet(UserQuery.REDIS_USER_MAP, String.valueOf(userId), user);
			}
		}
		return user;
	}

	@Override
	public User getUserByPhone(String phone) {
		User user = select(UserQuery.GET_USER_BY_PHONE, phone);
		if(user != null && user.getUserId() > 0) {
			getRedis().hSet(UserQuery.REDIS_USER_MAP, String.valueOf(user.getUserId()), user);
		}
		return user;
	}

	@Override
	public ResultList<User> getAllFriends(int userId) {
		String key = UserQuery.REDIS_USER_FRIEND + userId;
		List<String> friendIdList = getRedis().lGet(key);
		String[] friendIdArray = (String[]) friendIdList.toArray();
		List<User> userList = getRedis().hmGet(UserQuery.REDIS_USER_MAP, User.class, friendIdArray);
		
		ResultList<User> rsList = new ResultList<User>();
		rsList.setResults(userList);
		rsList.setTotal(userList.size());
		return rsList;
	}
	
	@Override
	public User login(User inUser) {
		String phone = inUser.getPhone();
		User user = getUserByPhone(phone);
		if(user == null || user.getUserId() <= 0) {
			String createdIp = inUser.getLoginIp();
			long currentTime = System.currentTimeMillis(); 
			String createdTime = DateUtils.getDate(currentTime);
			String loginTime = DateUtils.getDate(currentTime);
			
			inUser.setLoginTime(loginTime);
			inUser.setCreatedIp(createdIp);
			inUser.setCreatedTime(createdTime);
			
			// insert new user
			int userId = insertUser(inUser);
			user.setUserId(userId);
		}
		return user;
	}

	@Override
	public int insertUser(User user) {
//		int newUserId = (int) getRedis().increase(UserQuery.REDIS_USER_ID);
//		user.setUserId(newUserId);
//		return (int) redisInsert(UserQuery.REDIS_USER, String.valueOf(newUserId), user, false);
		return this.insert(UserQuery.INSERT_USER, user);
	}
	@Override
	public int insertUserList(List<User> ulist) {
		return this.insertBatch(UserQuery.INSERT_USER, ulist);
	}
	
	@Override
	public int updateUser(User user) {
		return (int) redisUpdate(UserQuery.REDIS_USER, String.valueOf(user.getUserId()), user, false);
	}

	@Override
	public int deleteUser(int userId) {
		return (int) redisDelete(UserQuery.REDIS_USER, String.valueOf(userId), false);
	}

	@Override
	public int activateUser(int userId, int activeB) {
		User user = getUserById(userId);
		user.setActiveB(activeB);
		
		return updateUser(user);
	}

	@Override
	public int makeFriends(int fromUserId, int toUserId) {
		return 0;
	}

	@Override
	public int acceptFriends(int fromUserId, int toUserId) {
		return 0;
	}

}
