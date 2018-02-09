package com.light.utils;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

/**
 * Request Utils.
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class RequestUtils {
	
//	/** 获得session用户
//	 * <br>如果当前用户不存在从cookie取,如果取不到在初始化一个默认的用户
//	 * @param request 当前请求
//	 */
//	public static UserSession getSessionUser(HttpServletRequest request){
//		UserSession user = (UserSession) request.getSession(true).getAttribute(Constants.user);
//		if(user==null){
//			if(!CookieUtils.getCookie(request)){
//				OnlineProxy.creatSessionUser(request);
//			}
//			user = (UserSession) request.getSession(true).getAttribute(Constants.user);
//		}
//		return user;
//	}
//	
//	/** 获得session管理员
//	 * @param request 当前请求
//	 */
//	public static AdminUser getSessionAdminUser(HttpServletRequest request){
//		AdminUser admin = (AdminUser) request.getSession(true).getAttribute(Constants.admin);
//		return admin;			
//	}
//	
	/**
	 * Get the Ip.
	 * 
	 * @param request
	 * @return ip
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		return ip == null ? "" : ip;
	}

	/**
	 * Get request URL.
	 * 
	 * @param request
	 * @return request URL
	 */
	public static String getRequestURL(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String query = request.getQueryString();
		if (query != null && query.length() > 1){
			url = url + "?" + query;
		}
		return url;
	}

	/**
	 * Get request URI, it doesn't contain the domain.
	 * 
	 * @param request
	 * @return request internal URL
	 */
	public static String getInternalURL(HttpServletRequest request) {
		String url = request.getRequestURI().toString();
		String query = request.getQueryString();
		if (query != null && query.length() > 1){
			url = url + "?" + query;
		}
		return url;
	}

	/**
	 * Get the domain name.
	 * 
	 * @param request
	 * @return domain name
	 */
	public static String getDomainName(HttpServletRequest request) {
		String domainName;
		String contextPath = request.getContextPath();
		String serverName = "http://" + request.getServerName();
		int serverPort = request.getServerPort();
		if(serverPort != 80){
		   serverName += ":" + serverPort;
		}
		domainName =  serverName + contextPath;
		 
		return domainName;
	}
	
	/**
	 * Set charset encoding to "UTF-8".
	 * 
	 * @param request
	 */
	public static void setCharacterEncoding(HttpServletRequest request) {
		setCharacterEncoding(request, "UTF-8");
	}

	/**
	 * Set charset encoding to specified charset.
	 * 
	 * @param request
	 * @param charSet
	 */
	public static void setCharacterEncoding(HttpServletRequest request, String charSet) {
		try {
			request.setCharacterEncoding(charSet);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get string parameter from request.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value != null ? value : "";
	}

	/**
	 * Get string attribute from request.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static String getStringAttribute(HttpServletRequest request, String paramName) {
		Object obj = request.getAttribute(paramName);
		return obj == null ? "" : String.valueOf(obj);
	}

	/**
	 * Get string array from request.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static String[] getArray(HttpServletRequest request, String paramName) {
		return request.getParameterValues(paramName);
	}

	/**
	 * Get int parameter from request, defalut to zero.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static int getInt(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		int rtn = 0;
		if (value != null && value.length() > 0) {
			try {
				rtn = Integer.parseInt(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

	/**
	 * Get int attribute from request, defalut to zero.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static int getIntAttribute(HttpServletRequest request, String paramName) {
		String value = (String) request.getAttribute(paramName);
		int rtn = 0;
		if (value != null && value.length() > 0) {
			try {
				rtn = Integer.parseInt(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

	/**
	 * Get long parameter from request, defalut to zero.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static long getLong(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		long rtn = 0L;
		if (value != null && value.length() > 0) {
			try {
				rtn = Long.parseLong(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}
	
	/**
	 * Get long attribute from request, defalut to zero.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static long getLongAttribute(HttpServletRequest request, String paramName) {
		String value = (String) request.getAttribute(paramName);
		long rtn = 0L;
		if (value != null && value.length() > 0) {
			try {
				rtn = Long.parseLong(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}
	
	/**
	 * Get short parameter from request, defalut to zero.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static short getShort(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		short rtn = 0;
		if (value != null && value.length() > 0) {
			try {
				rtn = Short.parseShort(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

	/**
	 * Get short attribute from request, defalut to zero.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static short getShortAttribute(HttpServletRequest request, String paramName) {
		String value = (String) request.getAttribute(paramName);
		short rtn = 0;
		if (value != null && value.length() > 0) {
			try {
				rtn = Short.parseShort(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

	/**
	 * Get boolean parameter from request, defalut to false.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static boolean getBoolean(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		boolean rtn = false;
		if (value != null && value.length() > 0) {
			try {
				rtn = Boolean.valueOf(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}
	
	/**
	 * Get boolean attribute from request, defalut to false.
	 * 
	 * @param request
	 * @param paramName parameter name
	 * @return parameter value
	 */
	public static boolean getBooleanAttribute(HttpServletRequest request, String paramName) {
		String value = (String)request.getAttribute(paramName);
		boolean rtn = false;
		if (value != null && value.length() > 0) {
			try {
				rtn = Boolean.valueOf(value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	}

}
