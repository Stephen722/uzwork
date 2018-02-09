package com.uzskill.mobile.action;

import java.util.Map;

import com.light.exception.ApplicationException;
import com.light.struts.action.BaseAction;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;

/**
 * API Service BaseAction
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */ 
public class MobileBaseAction extends BaseAction {
    
	/**
	 * get the current access user ID
	 * 
	 * @return user ID
	 */
	public int getAccessUserId() {
		return getAccessToken().getUserId();
	}
	
	/**
	 * get access token ID
	 * 
	 * @return token ID
	 */
	public String getAccessTokenId() {
		return getAccessToken().getId();
	}
	
	/**
	 * get token
	 * 
	 * @return AccessToken
	 */
	public AccessToken getAccessToken() {
		AccessToken token = null;
		Map<String, Object> contextMap = getActionContext().getValueStack().getContext();
		if(contextMap.containsKey(AccessTokenProcessor.ACCESS_TOKEN)) {
			token = (AccessToken) contextMap.get(AccessTokenProcessor.ACCESS_TOKEN);
		}
		if(token == null) {
			throw new ApplicationException("The current access token is null, please restart your client to access again"); 
		}
		return token;
	}
}
