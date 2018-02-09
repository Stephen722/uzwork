package com.uzskill.mobile.chat;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.light.utils.DESUtils;
import com.light.utils.StringUtils;
import com.uzskill.mobile.token.AccessToken;
import com.uzskill.mobile.token.AccessTokenProcessor;
/**
 * Web chat service based on WebSocket, the web socket server address: /uzchat/{name}/{Access-Token}
 * Support parameters:
 * - name, the current user name.
 * - Access-Token, access token which has been verified in WebSocketFilter
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-10-06
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
@ServerEndpoint(value="/uzchat/{name}/{Access-Token}", configurator=WebChatConfigurator.class )
public class WebChatSocket {
	private static final Logger logger = Logger.getLogger(WebChatSocket.class);
	
	/**
	 * Establish chat connection, and retrieve the historic 20 messages with first page.
	 * 
	 * @param session   web socket session
	 * @param config    configuration
	 * @param name	    receive user name
	 * @param tokenId   Access-Token ID
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config, @PathParam("name") String name, @PathParam("Access-Token") String tokenId){
		// Access-Token has been verified in WebSocketFilter, no need to verify again.
		AccessToken token = AccessTokenProcessor.getInstance().getToken(tokenId);
		int userId = token.getUserId();
		if(userId > 0 && !StringUtils.isBlank(name)) {
			String userIdStr = DESUtils.encrypt(String.valueOf(userId));
			// new the chat session
			WebChatSession chatSession = new WebChatSession();
			chatSession.setSession(session);
			chatSession.setUserId(userIdStr);
			chatSession.setUsername(name);
			// persist the chat session
			WebChatProcessor.putSession(chatSession);
			// initialize the chat session
			WebChatProcessor.initSession(userId, chatSession);
			
		}
		else {
			onError(session, null);
		}
	}

	/**
	 * Close chat collection.
	 * Persist chat message and then remove the current chat session.
	 */
	@OnClose
	public void onClose(Session session){
		// 先持久化后删除
		WebChatProcessor.persistMessage(session);
		WebChatProcessor.removeSession(session);
	}

	/**
	 * Receive the message from client
	 * 
	 * @param message message from client
	 * @param session chat session
	 */
	@OnMessage
	public void onMessage(Session session, String message) {
		try {
			WebChatProcessor.sendMessage(session, message);
		} catch (IOException e) {
			logger.error("Failed to send message", e);
		}
	}

	/**
	 * Error for establishing web chat session
	 * 
	 * @param session chat session
	 * @param error error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		logger.error("Failed to establish the web chat session", error);
	}
}
