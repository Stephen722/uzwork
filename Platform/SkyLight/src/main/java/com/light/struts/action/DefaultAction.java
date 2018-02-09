package com.light.struts.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.light.utils.RequestUtils;

/**
 * Default Action.
 * 
 * <p>(C) 2015 www.uzwork.com (UZWork)</p>
 * Date:  2015-07-10
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class DefaultAction extends BaseAction {

	private static final Logger logger = LogManager.getLogger(DefaultAction.class);
	
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		logger.info("An invalid page is requested, the IP is:" + RequestUtils.getIpAddress(request) + ", and the Url is:"
				+ RequestUtils.getRequestURL(request));

		return ActionStatic.SUCCESS;
	}
}
