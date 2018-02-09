package com.uzskill.mobile.chat.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.light.utils.StringUtils;
import com.light.utils.RequestUtils;
import com.opensymphony.xwork2.ActionContext;
import com.uzskill.mobile.interceptor.MobileServiceInterceptor;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * 字符编码过滤器，用来指定Request，response的characterEncoding为"UTF-8"。
 * 
 * <p>(C) 2016 www.uzskill.com (UZWork)</p>
 * Date:  2016-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WebSocketFilter implements Filter {

	private static Logger logger = Logger.getLogger(WebSocketFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String ipAddress = request.getRemoteAddr();
		HttpServletRequest httpRequest = null;
		AccessToken token = null;
		if(ActionContext.getContext() != null) {
			httpRequest = ServletActionContext.getRequest();
			String URI = httpRequest.getRequestURI();
			if(URI.indexOf("/uzchat/") > 0) {
				String tokenId = URI.substring(URI.lastIndexOf("/") + 1);
				if(!StringUtils.isBlank(tokenId)) {
					token = AccessTokenProcessor.getInstance().getToken(tokenId);
				}
			}
		}
		// add "token.getUserId() > 0 && "
		if(token != null && ipAddress.equals(token.getIpAddress())) {
			logger.debug("WebSocket request with valid access token, IP:" + ipAddress);
			chain.doFilter(request, response);
		}
		else {
			logger.error("WebSocket request with invalid access token, IP:" + ipAddress);
		}
	}

	@Override
	public void destroy() {

	}
}