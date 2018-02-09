package com.uzskill.base.user.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.uzskill.base.user.UserSession;
import com.uzskill.base.user.bean.User;
/**
 * 在线用户实体类。
 *
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class UserSessionImpl implements UserSession {
	
	private static final Logger logger = Logger.getLogger(UserSessionImpl.class);
	
    private String sessionId;
    private String ipAddress;
    private HttpSession httpSession;
    private long creationTime = System.currentTimeMillis();
    private long lastAccessedTime = System.currentTimeMillis();
    private static final String USER = "onlineUser";
    
    protected UserSessionImpl() {
    }
    
    /**
     * 创建一个HttpUserSession。
     * 
     * @param httpSession
     * @param user
     */
    public UserSessionImpl(HttpSession httpSession, String ipAddress, User user) {
        this.httpSession = httpSession;
        this.ipAddress = ipAddress;
        setSessionId(httpSession.getId());
        setUser(user);
    }
    
	@Override
	public String getSessionId() {
        return getSessionId(true);
	}
	
	@Override
	public String getSessionId(boolean updateTime) {
		if(updateTime) {
			setLastAccessedTime();
		}
		return sessionId;
	}
	
    /**
     * 设置SessionId。
     * 该方法为受保护的方法，不允许外部修改Session Id.
     * 
     * @param sessionId
     */
    protected void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

	@Override
	public User getUser() {
		User user = get(USER);
        return user;
	}
	@Override
	public void setUser(User user) {
        set(USER, user);
	}

	@Override
	public int getUserId() {
        return getUserId(true);
	}
	
	@Override
	public int getUserId(boolean updateTime) {
		int userId = 0;
		User user = null;
		if(updateTime) {
			user = getUser();
		}
		else {
			user =  (User) httpSession.getAttribute(USER);
		}
		if(user != null) {
			userId = user.getUserId();
		}
		return userId;
	}
    
	@Override
	public String getUserName() {
		String username = "";
		User user = getUser();
		if(user != null) {
			username = user.getUsername();
		}
        return username;
	}

	@Override
	public boolean has(String key) {
		setLastAccessedTime();
        return httpSession.getAttribute(key) != null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(String key) {
		setLastAccessedTime();
        V value = (V) httpSession.getAttribute(key);
        if (value == null) {
            logger.error("The UserSession for userId '"+getUserId()+"' does not contain a value for the key '"+key+"'.");
        }
        return value;
	}

	@Override
	public List<String> getKeyNames() {
		List<String> keyNameList = new ArrayList<String>();
		Enumeration<String> attEn = httpSession.getAttributeNames();
		while(attEn.hasMoreElements()){
			String keyName = (String) attEn.nextElement();
			keyNameList.add(keyName);
		}
		return keyNameList;
	}

	@Override
	public <K, V> void set(String key, V value) {
		setLastAccessedTime();
        httpSession.setAttribute(key, value);
	}

	@Override
	public void remove(String key) {
	    httpSession.removeAttribute(key);
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	@Override
	public void setLastAccessedTime() {
		lastAccessedTime = System.currentTimeMillis();
	}

	@Override
	public long getCreationTime() {
        return creationTime;
	}

	@Override
	public HttpSession getHttpSession() {
		return httpSession;
	}

	@Override
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	public String getIpAddress() {
		return ipAddress;
	}

}
