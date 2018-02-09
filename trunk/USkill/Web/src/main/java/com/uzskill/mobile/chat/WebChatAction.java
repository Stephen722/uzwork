package com.uzskill.mobile.chat;

import org.apache.log4j.Logger;

import com.uzskill.mobile.action.MobileBaseAction;

/**
 * Web chat action
 * 
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-10-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WebChatAction extends MobileBaseAction {

	private static final Logger logger = Logger.getLogger(WebChatAction.class);
	
	/**
	 * Initialize access token.
	 */
	public void execute() {
		try {
			
			writeJSONResponse();
		}
		catch(Exception e) {
			handleAjaxError("Failed to initialize access token", e);
		}
	}

}