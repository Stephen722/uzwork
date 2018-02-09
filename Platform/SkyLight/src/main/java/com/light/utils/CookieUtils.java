package com.light.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie Utils.
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class CookieUtils {
	
	private static final int DEFAULT_DAYS = 7;
	
	/**
	 * Set cookie
	 * @param response 
	 * @param cookieName 
	 * @param cookieValue
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue) {
		Cookie cookie = new Cookie(cookieName, StringUtils.base64Encode(cookieValue));
		int days = DEFAULT_DAYS;
		cookie.setMaxAge(24 * 60 * days);
		cookie.setPath("/");// 因为用户可以从不同url保存cookie,故要将cookie设置为站点根目录
		response.addCookie(cookie);
	}

	/**
	 * Get cookie
	 * @param request
	 * @param cookieName
	 * @return cookie value
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) {
		String cookieValue = "";
		Cookie[] cks = request.getCookies();
		if (cks != null) {
			Cookie ck = null;
			for (int i = 0; i < cks.length; i++) {
				ck = cks[i];
				if (ck.getName().equalsIgnoreCase(cookieName)) {
					cookieValue = StringUtils.base64Decode(ck.getValue());
					break;
				}
			}
		}
		return cookieValue;
	}

	/**
	 * Remove cookie
	 * @param request
	 * @param response
	 * @param cookieName
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		Cookie[] cks = request.getCookies();
		if (cks != null) {
			for (int i = 0; i < cks.length; i++) {
				Cookie ck = cks[i];
				if (ck.getName().equalsIgnoreCase(cookieName)) {
					ck.setValue("");
					ck.setMaxAge(0);
					ck.setPath("/");
					response.addCookie(ck);
				}
			}
		}
	}

	static {};
}
