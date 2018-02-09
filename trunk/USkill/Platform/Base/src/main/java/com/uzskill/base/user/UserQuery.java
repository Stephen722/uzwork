package com.uzskill.base.user;

/**
 * Constants for User field\Redis key\DB query
 * 
 * <p>(C) 2018 www.uzwork.com (UZWork)</p>
 * Date:  2018-01-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserQuery {
	
	/*	
	 * User has the following Redis objects:
	 * 
	 * user:id - string which for auto increase id.
	 * user:map - hash which contains the users, key = user id, value = user jSon string.
	 * user:insert - list which contains all the newly added users.
	 * user:update - list which contains all the updated users.
	 * uf:{userId} - list which contains all the friends' id of specified user.
	 * 
	 * */
	public static final String REDIS_USER = "user";
	public static final String REDIS_USER_ID = "user:id";
	public static final String REDIS_USER_MAP = "user:map";
	public static final String REDIS_USER_INSERT = "user:insert";
	public static final String REDIS_USER_UPDATE = "user:update";
	public static final String REDIS_USER_FRIEND = "user:f:";
	
	// user fields
	public static final String NICKNAME = "nickname";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String CONFIRMED_PASSWORD = "confirmedPassword";
	public static final String PHONE = "phone";
	public static final String USER_ID = "userId";
	public static final String USER_ID_STR = "userIdStr";
	public static final String ACTIVE_B = "activeB";
	public static final String LOGIN_IP = "loginIp";
	public static final String CREATED_IP = "createdIp";
	public static final String CREATED_BY = "createdBy";
	public static final String CREATED_TIME = "createdTime";
	public static final String PICTURE = "picture";
	public static final String USER_BRIEF = "brief";
	public static final String CITY = "city";
	public static final String SEX = "sex";
	public static final String MESSAGES = "messages";
	public static final String FRIENDS = "friends";
	public static final String FAVORITES = "favorites";
	public static final String LOGIN_CODE = "loginSMSCode";
	
	// DB queries
	public static final String GET_USER_BY_ID = "SK_User.getUserById";
	public static final String GET_USER_BY_PHONE = "SK_User.getUserByPhone";
	public static final String LOGIN_USER = "SK_User.loginUser";
	public static final String LOGIN_USER_UPDATE_FUNCTION = "loginUserUpdateFunction";
	public static final String INSERT_USER = "SK_User.insertUser";
	public static final String DELETE_USER = "SK_User.deleteUser";
	public static final String ACTIVATE_USER = "SK_User.activateUser";
}
