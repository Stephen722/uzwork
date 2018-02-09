package com.uzskill.mobile.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.light.utils.StringUtils;
import com.light.utils.RequestUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * Mobile service interceptor
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class MobileServiceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final String ACCESS_TOKEN_ACTION = "AccessTokenAction";
	private static final String ERROR_ACTION = "ErrorAction";

	private static Logger logger = LogManager.getLogger(MobileServiceInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		AccessToken token = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String tokenId = request.getHeader(AccessTokenProcessor.ACCESS_TOKEN);
		String channel = request.getHeader(AccessTokenProcessor.ACCEPT_CHANNEL);
		
		// exclude the predefined actions
		if (isExcludedAction(invocation)) {
			return invocation.invoke();
		}
//		else if(!StringUtils.isBlank(tokenId) && (AccessTokenProcessor.ACCESS_WEB.equals(channel) || AccessTokenProcessor.ACCESS_APP.equals(channel))) {
		else if(!StringUtils.isBlank(tokenId) && AccessTokenProcessor.ACCESS_APP.equals(channel)) {
			// the access channel and token should be valid
			token = AccessTokenProcessor.getInstance().getToken(tokenId);
		}
		
		// access forbidden if the access token is null or invalid
		String ipAddress = RequestUtils.getIpAddress(request);
		if(token != null) {
			if(ipAddress.equals(token.getIpAddress())) {
				invocation.getStack().getContext().put(AccessTokenProcessor.ACCESS_TOKEN, token);
				return invocation.invoke();
			}
			else {
				logger.error("Access forbidden because of invalid access token, IP:" + ipAddress);
				return "forbidden";
			}
		}
		else {
			logger.error("Access forbidden because of empty access token, IP:" + ipAddress);
			return "forbidden";
		}
	}
	
	/**
	 * check the predefined excluded actions
	 * 
	 * @param invocation
	 * @return true/false
	 */
	private boolean isExcludedAction(ActionInvocation invocation) {
		boolean excluded = false;
		String actionName = invocation.getAction().getClass().getName();
		if(actionName.endsWith(ACCESS_TOKEN_ACTION) || actionName.endsWith(ERROR_ACTION)) {
			excluded = true;
		}
		return excluded;
		
	}
	
}