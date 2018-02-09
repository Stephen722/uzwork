package com.uzskill.mobile.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.light.utils.RequestUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * Workbench service interceptor
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-26
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WorkbenchServiceInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(WorkbenchServiceInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// system puts token into context already in previous MobileServiceIntercepter
		AccessToken token = (AccessToken) invocation.getStack().getContext().get(AccessTokenProcessor.ACCESS_TOKEN);
		
		// access forbidden if the user id is invalid
		if(token != null && token.getUserId() > 0) {
			return invocation.invoke();
		}
		else {
			HttpServletRequest request = ServletActionContext.getRequest();
			String ipAddress = RequestUtils.getIpAddress(request);
			logger.error("Access forbidden because of invalid user ID, IP:" + ipAddress);
			return "forbidden";
		}
	}
}