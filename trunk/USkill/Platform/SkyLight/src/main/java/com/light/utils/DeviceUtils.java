package com.light.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.light.app.ApplicationContext;

/**
 * 判断访问设备工具类。
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-04-21
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class DeviceUtils {

	private static final String USER_AGENT = "USER-AGENT";
	private static final String DOMAIN_WWW_DEFAULT = "http://www.uzwork.com";
	private static final String DOMAIN_MOBILE_DEFAULT = "http://m.uzwork.com";
	public static final String DOMAIN_WWW = ApplicationContext.getInstance().getProperty("application.root.url", DOMAIN_WWW_DEFAULT);
	public static final String DOMAIN_MOBILE = ApplicationContext.getInstance().getProperty("application.domain.mobile", DOMAIN_MOBILE_DEFAULT);
	public static final String EXCLUDED_URL = "app-download,app-check-version";
	
	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔), 字符串在编译时会被转码一次,所以是 "\\b" \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
	private static final  String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i|windows (phone|ce)|blackberry"    
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp|laystation portable)|nokia|fennec|htc[-_]"    
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";   
    
	/**
	 * 根据User Agent判断是否为移动设备
	 * 
	 * @param userAgent
	 * @return 是否为移动设备
	 */
	public static boolean isMobile(HttpServletRequest request) {
		String userAgent = request.getHeader(USER_AGENT);
		Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE); 
        Matcher matcherPhone = phonePat.matcher(userAgent);    
        if(matcherPhone.find()){    
            return true;    
        } else {    
            return false;    
        }    
	}

}