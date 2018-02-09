package com.uzskill.mobile.action;

/**
 * Error action
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-15
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ErrorAction extends MobileBaseAction {
	
	
	public void execute() {
		try {
			writeJSONResponse(false, "Access forbidden");
		}
		catch(Exception e) {
			handleAjaxError("Failed to process error", e);
		}
	}
	
	/**
	 * Access forbidden.
	 * 
	 */
	public void forbidden() {
		try {
			writeJSONResponse(false, "Access forbidden");
		}
		catch(Exception e) {
			handleAjaxError("Failed to process access fobidden", e);
		}
	}
	
}