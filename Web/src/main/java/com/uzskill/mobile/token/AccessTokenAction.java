package com.uzskill.mobile.token;

import org.apache.log4j.Logger;

import com.light.utils.DeviceUtils;
import com.light.utils.StringUtils;
import com.light.utils.RequestUtils;
import com.uzskill.mobile.action.MobileBaseAction;

/**
 * Access token action
 * 
 * For browser, it only supports to initialize token for "guest", the login action will update browser local token.
 * For APP, it supports both initialize token and update token (with previous user ID) when IP is changed.
 * 
 * If the access channel is invalid, system will not initialize access token.
 * The access channel is determined by the variable "Accept-Channel" in request header.
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-09-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class AccessTokenAction extends MobileBaseAction {

	private static final Logger logger = Logger.getLogger(AccessTokenAction.class);
	
	/**
	 * Initialize access token.
	 */
	public void execute() {
		try {
			int userId = 0;
			boolean isValidAccess = false;
			// determine request comes from the web browser or APP by predefined header variables.
			// Accept-Channel = {WEB:means browser, UZAPP: means APP}
			String access = request.getHeader(AccessTokenProcessor.ACCEPT_CHANNEL);
			String IP = RequestUtils.getIpAddress(request);
			if(AccessTokenProcessor.ACCESS_WEB.equals(access)) {
				// referer must be correct
				String referer = request.getHeader("Referer");
				if(!StringUtils.isBlank(referer) && referer.trim().startsWith(DeviceUtils.DOMAIN_WWW)) {
					isValidAccess = true;
					logger.debug("Initialized access token for web browser, IP:" + IP);
				}
				else {
					logger.error("Try to initilize access token for web browser by invalid referer, IP:" + IP);
				}
			}
			else if(AccessTokenProcessor.ACCESS_APP.equals(access)) {
				isValidAccess = true;
				String accessTokenId = request.getHeader(AccessTokenProcessor.ACCESS_TOKEN);
				if(!StringUtils.isBlank(accessTokenId)) {
					// ONLY FOR APP: if the IP of previous token is different from current IP, then update the token with previous user ID.
					// For login user, there are some potential risks if the APP access token has been stole by hacker,
					// the hacker is able to get user information by this token. IT MUST BE SOLVED IN THE FUTURE VERSION (V2).
					AccessToken previousToken = AccessTokenProcessor.getInstance().getToken(accessTokenId);
					if(previousToken != null) {
						userId = previousToken.getUserId();
					}
				}
				if(userId > 0) {
					logger.debug("Change access token for native APP for user:" + userId + ", IP:" + IP);
				}
				else {
					logger.debug("Initialized access token for native APP, IP:" + RequestUtils.getIpAddress(request));
				}
			}
			else {
				logger.error("Try to initilize access token by invalid access channel, IP:" + IP);
			}
			
			String tokenId = "";
			if(isValidAccess) {
				// this action only supports to initialize token with "guest", it CANNOT be any register user here, otherwise it will cause a serious bug.
				AccessToken token = AccessTokenProcessor.getInstance().initializeToken(userId, request);
				// only output token id rather than other attributes, because token contains sensitive and important user ID.
				tokenId = token.getId();
			}
			writeJSONResponse(tokenId);
		}
		catch(Exception e) {
			handleAjaxError("Failed to initialize access token", e);
		}
	}

}