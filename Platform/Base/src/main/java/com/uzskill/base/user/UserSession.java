package com.uzskill.base.user;

import java.util.List;

import javax.servlet.http.HttpSession;

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
public interface UserSession {
	
	/**
     * 返回当前UserSession的唯一表示SessionId。
     * 
     * @return String
     */
    public String getSessionId();
	
	/**
     * 返回当前UserSession的唯一表示SessionId。
     * 根据参数来判断是否更新用户最后访问时间，默认更新访问时间
     * 
     * @param updateTime
     * @return String
     */
    public String getSessionId(boolean updateTime);
    
    /**
     * 返回当前UserSession的 User.
     * 
     * @return User
     */
    public User getUser();
    
    /**
     * 设置当前UserSession的 User.
     * 
     * @param User
     */
    public void setUser(User user);

    /**
     * 返回当前 User Id.
     * 
     * @return User Id
     */
    public int getUserId();
    
    /**
     * 返回当前 User Id.
     * 根据参数来判断是否更新用户最后访问时间，默认更新访问时间
     * 
     * @param updateTime
     * @return User Id
     */
    public int getUserId(boolean updateTime);
    
    /**
     * 返回当前 User Name.
     * 
     * @return String
     */
    public String getUserName();

    /**
     * 判断当前UserSession是否有给定的值。
     * 
     * @param key
     * @return boolean
     */
    public boolean has(String key);

    /**
     * 返回指定Key在当前UserSession对应的值。
     * @param <V>
     * 
     * @param key
     * @return Object
     */
    public <V> V get(String key);

    /**
     * 返回当前UserSession中的所有Key。
     * 
     * @return List
     */
    public List<String> getKeyNames();

    /**
     * 设置对应的值到UserSession。
     * @param <K>
     * @param <V>
     * 
     * @param key
     * @param value
     */
    public <K, V> void set(String key, V value);

    /**
     * 从当前UserSession中删除给定值。
     *
     * @param key
     */
    public void remove(String key);

    /**
     * 返回当前UserSession最新访问时间。
     * 
     * @return long
     */
    public long getLastAccessedTime();

    /**
     * 设置当前UserSession访问时间。
     */
    public void setLastAccessedTime();

    /**
     * 返回当前UserSession创建的时间。
     * 
     * @return long
     */
    public long getCreationTime();
    
	/**
	 * 返回HttpSession.
	 * 
	 * @return HttpSession
	 */
	public HttpSession getHttpSession();
	
	/**
	 * 设置当前HttpSession.
	 * 
	 * @param httpSession
	 */
	public void setHttpSession(HttpSession httpSession);
	
	/**
	 * 取得当前ip地址
	 * 
	 * @return ip地址
	 */
	public String getIpAddress();
}
