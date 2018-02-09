package com.uzskill.base.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.utils.StringUtils;
import com.uzskill.base.user.bean.User;
import com.uzskill.base.user.impl.UserSessionImpl;

/**
 * 在线用户的管理类。
 *
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserSessionManager {
	
	private static final Logger logger = Logger.getLogger(UserSessionManager.class);
	private static final String BEAN_NAME = "UserSessionManager";
	
	private static int userThreshold;
	private static int timeoutInMinutes;
	private static long sessionTimeoutInMillis;
	private static final String ONLINE_USER_REFRESH_THRESHOLD = "online.user.refresh.threshold";
	private static final String ONLINE_USER_SESSION_TIMEOUT = "online.user.session.timeout";
	
	// 必须保证线程安全
	private Map<String, UserSession> userSessionMap = new ConcurrentHashMap<String, UserSession>();

	private static UserSessionManager instance;
	
	/**
	 * 构造方法
	 */
	private UserSessionManager() {
		
	}
	
	/**
	 * 返回一个UserSessionManager实例
	 * 
	 * @return UserSessionManager
	 */
	public static final UserSessionManager getInstance() {
		if (instance == null) {
			synchronized(UserSessionManager.class) {
				if (instance == null) {
					if (ApplicationContext.getInstance().hasBean(BEAN_NAME)) {
						instance = (UserSessionManager) ApplicationContext.getInstance().getBean(BEAN_NAME);
					}
					else {
						instance = new UserSessionManager();
					}
					logger.info("Initialized an instance of UserSessionManager.");
					initializeConfiguration();
				}
			}
		}
		return instance;
	}
	
	private static void initializeConfiguration() {
		userThreshold = StringUtils.getInt(ApplicationContext.getInstance().getProperty(ONLINE_USER_REFRESH_THRESHOLD, "10"));
		timeoutInMinutes = StringUtils.getInt(ApplicationContext.getInstance().getProperty(ONLINE_USER_SESSION_TIMEOUT, "30"));
		sessionTimeoutInMillis = timeoutInMinutes * 60000;
	}
	
	/**
	 * 初始化用户信息。
	 * 
	 * @param ipAddress 当前的ip地址
	 * @param session 当前HttpSession
	 * @param user 当前用户
	 */
	public void initializeUserSession(HttpSession session, String ipAddress, User user){
		UserSession userSession = new UserSessionImpl(session, ipAddress, user);
		setUserSession(userSession);
		logger.info("Initialized a new user session id(user id): " + session.getId() + "(" + user.getUserId() + "), the number of online user is: " + userSessionMap.size());
	}
	
	/**
	 * 设置UserSession。如果存在已经过期的用户则删除之；如果当前登录用户已经存在则给出错误信息；
	 * 可以配置是否删除已经登录的用户，使当前登录用户有效，但是这样容易导致先前登录的用户正在操作的数据丢失。
	 * 
	 * @param userSession
	 */
	private void setUserSession(UserSession userSession) {
		String httpSessionId = userSession.getSessionId();
		// 判断所有当前用户和Guest是否在线
		// 当前在线用户数量达到某一个值userCount的倍数的时候（不是每个用户登录），系统会处理所有在线用户来判断是否有已经过期用户，有则删除。
		if(userSessionMap.size() % userThreshold == 0){
			Iterator<Entry<String, UserSession>> userIt = userSessionMap.entrySet().iterator();
			while (userIt.hasNext()) {
				Entry<String, UserSession> userEntry = userIt.next();
				UserSession us = userEntry.getValue();
				// 如果用户（包含Guest）已过期则删除之
				if (us.getLastAccessedTime() < System.currentTimeMillis() - sessionTimeoutInMillis) {
					removeUserSession(us.getHttpSession());	
				}
				else {
					// 此处在取值的时候不能更新该对象的最好访问时间
					int userId = us.getUserId(false);
					String sessionId = us.getSessionId(false);
					// 如果当前用户(排除Guest)已经存在系统中，并且处于不同session中则删除前一个session信息（后续操作将会把当前用户保存到缓存）。
					if(userId > 0 && userId == userSession.getUserId() && !sessionId.equals(userSession.getSessionId())){
						removeUserSession(us.getHttpSession());
					}
				}
			}
		}
		// 将当前用户保存到缓存
		userSessionMap.put(httpSessionId, userSession);
	}
	
	/**
	 * 直接从该缓存中取得UserSession。如果没有缓存该UserSession或者用户已经过期，则返回null。
	 * 
	 * @param session
	 * @return UserSession
	 */
	public UserSession getUserSession(HttpSession session) {
		UserSession userSession = null;
		String httpSessionId = session.getId();
		if (userSessionMap.containsKey(httpSessionId)) {
			userSession = userSessionMap.get(httpSessionId);
			// 判断用户是否已经过期，如果过期则删除之。
			if (userSession.getLastAccessedTime() < System.currentTimeMillis() - sessionTimeoutInMillis) {
				userSessionMap.remove(httpSessionId);
				userSession = null;
			}
			else {
				// 更新访问时间
				userSession.setLastAccessedTime();
			}
		}
		return userSession;
	}
	
	/**
	 * 取得所有当前在线用户信息，同时将检查所有在线用户，删除过期用户。
	 * 
	 * @param type 0:所有在线用户（包括登录用户和Guest） 1:登录用户  2:Guest
	 * @return List 在线用户
	 */
	private List<UserSession> getAllOnlineUserSession(int type) {
		List<UserSession> allUserSession = new ArrayList<UserSession>();
		Iterator<Entry<String, UserSession>> userIt = userSessionMap.entrySet().iterator();
		while (userIt.hasNext()) {
			Entry<String, UserSession> userEntry = userIt.next();
			UserSession us = userEntry.getValue();
			// 删除过期用户
			if (us.getLastAccessedTime() < System.currentTimeMillis() - sessionTimeoutInMillis) {
				removeUserSession(us.getHttpSession());	
			}
			else {
				// 此处取得UserId做判断时候，不能更新UserSession的最后访问时间，否则所有的UserSession的最后访问时间将会被更新为同一时间
				switch (type) {
					case 0:
						allUserSession.add(us);
						break;
					case 1: 
						if(us.getUserId(false) > 0){
							allUserSession.add(us);
						}
						break;
					case 2: 
						if(us.getUserId(false) == 0){
							allUserSession.add(us);
						}
						break;
					default: 
						allUserSession.add(us);
						break;
				}
			}
		}
		return allUserSession;
	}

	/**
	 * 取得所有当前在线用户信息，包括登录的和Guest。
	 * 
	 * @return List 所有在线用户
	 */
	public List<UserSession> getAllUserSession(){
		return getAllOnlineUserSession(0);
	}
	
	/**
	 * 取得所有当前已经登录用户信息。
	 * 
	 * @return List 所有登录用户
	 */
	public List<UserSession> getAllLoginUser(){
		return getAllOnlineUserSession(1);
	}
	
	/**
	 * 取得所有当前在线Guest信息。
	 * 
	 * @return List 所有Guest
	 */
	public List<UserSession> getAllOnlineGuest(){
		return getAllOnlineUserSession(2);
	}
	
	/**
	 * 从当前缓存中删除UserSession。如果UserSession没有被缓存则返回null。
	 * 
	 * @param session
	 * @return Object
	 */
	public Object removeUserSession(HttpSession session) {
		String sessionId = session.getId();
		Object rtn = null;
		if(userSessionMap.containsKey(sessionId)){
			int userId = userSessionMap.get(sessionId).getUserId();
			rtn = userSessionMap.remove(sessionId);
			logger.info("Removed a user session with session id(user id): " + sessionId+"(" + userId + "), the number of online user is: " + userSessionMap.size());
		}
		return rtn;
	}
}
