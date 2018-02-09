package com.uzskill.web.action;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.light.app.ApplicationContext;
import com.light.exception.ApplicationException;
import com.light.exception.ExceptionHelper;
import com.light.exception.ValidationException;
import com.light.record.Record;
import com.light.struts.action.BaseAction;
import com.uzskill.base.user.UserHelper;
import com.uzskill.base.user.UserSession;
import com.uzskill.base.user.UserSessionManager;
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
public class WebBaseAction extends BaseAction {
	
	protected static final String INPUT = "input";
	protected static final String ERROR = "error";
	protected static final String NONE = "none";
	protected static final String FAILURE = "failure";
	
	private static final Logger logger = Logger.getLogger(WebBaseAction.class);
	
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
	
    /**
     * 输出Record
     * 
     * @param outRecord
     */
    protected void publishOutRecord(Record outRecord) {
    	Iterator<Entry<String, Object>> iter = outRecord.getRecordMap().entrySet().iterator();
        while(iter.hasNext()) {
        	Entry<String, Object> en = iter.next();
        	publishAttribute(en.getKey(), en.getValue());
        }
    }
	
    /**
     * 取得当前用户Session
     * 当有新用户来访，系统会在来访监听器中初始化一个与之对应的Session，所有每一个正常来访的用户，其Session必定不为空。
     * 
     * @return UserSession
     */
    protected UserSession getUserSession() {
    	UserSession userSession = UserSessionManager.getInstance().getUserSession(session);
    	if(userSession == null){
    		logger.info("The current user session is null, system is trying to create a new online user.");
    		// 创建一个新的session
    		UserHelper.initializeOnlineUser(request, session);
    		userSession = UserSessionManager.getInstance().getUserSession(session);
    	}
		return userSession;
    }
    
    /**
     * 输出某个值
     * 
     * @param key
     * @param value
     */
    protected void publishAttribute(String key, Object value) {
         request.setAttribute(key, value);
    }
    
	/**
	 *  输出（提示／警告／错误）信息到页面
	 * 
	 * @param message
	 */
	protected void publishMessage(String message) {
		request.setAttribute(MESSAGE, message);
	}
	
	/**
	 * 处理验证异常。 暂时没有任何逻辑。
	 * 
	 * @param ve
	 * @return String
	 */
	protected String handleValidationException(ValidationException ve) {
		ApplicationException ae = ExceptionHelper.getInstance().handleException(ValidationException.VALIDATION_ERROR, "Validation exception", ve);
		logger.error(ae.getMessage(), ae);
		publishMessage(ApplicationContext.getInstance().getMessage(ae.getMessageKey()));
		return INPUT;
	}
	
	/**
	 * 处理异常，并保存错误信息，然后返回指定错误页面。抛出该异常的Action将根据返回信息跳转到相应页面。
	 * 
	 * @param debugMessage
	 * @param e
	 * @return String
	 */
	protected String handleError(String debugMessage, Exception e) {
		ApplicationException ae = ExceptionHelper.getInstance().handleException(debugMessage, e);
		logger.error(ae.getMessage(), ae);
		publishMessage(ApplicationContext.getInstance().getMessage(ae.getMessageKey()));
		return ERROR;
	}
}
