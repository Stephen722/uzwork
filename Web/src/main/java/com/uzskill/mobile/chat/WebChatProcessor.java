package com.uzskill.mobile.chat;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.light.app.ApplicationContext;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.DateUtils;
import com.uzskill.base.chat.ChatManager;
import com.uzskill.base.chat.impl.ChatManagerImpl;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.user.UserQuery;

/**
 * Web chat processor
 *
 * <p>(C) 2017 www.uzskill.com (UZWork)</p>
 * Date:  2017-01-06
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class WebChatProcessor {
	// chat session map <userId, WebChatSession>
	private static Map<String, WebChatSession> chatSessionMap = new ConcurrentHashMap<String, WebChatSession>();
	// session ID and user ID map <session ID, user ID>
	private static Map<String, String> sessionUserIDMap = new ConcurrentHashMap<String, String>();
	// chat message map <message ID, LinkedList<WebChatMessage>>, it saves the message which not be persisted into DB
	private static Map<String, WebChatMessage> messageMap = new ConcurrentHashMap<String, WebChatMessage>();
	// user ID and message ID map <user ID, List<message ID>>
	private static Map<String, LinkedList<String>> userMessageIDMap = new ConcurrentHashMap<String, LinkedList<String>>();
	
	private static final int HISTORIC_MESSAGE_MAX = 200;
	private static final Logger logger = Logger.getLogger(WebChatProcessor.class);
	
	private static ChatManager chatManager;
	
	/**
	 * Create a new chat session
	 * 
	 * @param chatSession
	 */
	public static void putSession(WebChatSession chatSession) {
		String userId = chatSession.getUserId();
		chatSessionMap.put(userId, chatSession);
		sessionUserIDMap.put(chatSession.getId(), userId);
		logger.debug("Created a new web chat session for user:" + chatSession.getUserId());
	}
	
	/**
	 * Initialize session
	 * 
	 * @param userId
	 * @param chatSession
	 */
	public static void initSession(int userId, WebChatSession chatSession) {
		// retrieve chat user list from database
		Record record = new Record();
		record.setValue(UserQuery.USER_ID, userId);
		ResultList<Record> userList = getChatManager().getChatUserList(record);
		if(userList != null) {
			try {
				chatSession.getSession().getBasicRemote().sendText(JSON.toJSONString(userList));
				logger.debug("Sent chat user(s) successfully for user ID:" + userId);
			} catch (IOException e) {
				logger.error("Failed to send chat user(s)", e);
			}
		}
	}
	
	/**
	 * Get the historic message list
	 * 
	 * @param userId
	 * @return message list
	 */
	public static List<WebChatMessage> getHistoricMessages(int userId) {
		// get historic messages from database
//		Record record = new Record();
//		record.setValue(ShareFields.USER_ID, userId);
//		ResultList<ChatMessage> rsList = getChatManager().getChatMessageList(record);
//		List<WebChatMessage> mList = rsList.getResults();
//		return mList;
		return null;
	}
	
	/**
	 * Send message
	 * 
	 * @param session
	 * @param messageData
	 * @throws IOException 
	 */
	public static void sendMessage(Session session, String messageData) throws IOException {
		// parse message data
		JSONObject messageObj = JSON.parseObject(messageData);
		String message = messageObj.getString("message");
		String receiveUserId = messageObj.getString("toUserId");
		String receiveUsername = messageObj.getString("toUsername");
		
		// create a new chat message object
		WebChatMessage chatMessage = new WebChatMessage();
		// generate an unique UUID for newly created chat message
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		chatMessage.setMessageId(uuid);
		// get the name/ID of send user from chat session
		String fromUserId = sessionUserIDMap.get(session.getId());
		WebChatSession fromChatSession = chatSessionMap.get(fromUserId);
		String sendUserId = fromChatSession.getUserId();
		chatMessage.setSendUserId(sendUserId);
		chatMessage.setSendUsername(fromChatSession.getUsername());
		// get the name/ID of receive user from current message data 
		chatMessage.setReceiveUserId(receiveUserId);
		chatMessage.setReceiveUsername(receiveUsername);
		// set message content and time
		chatMessage.setMessage(message);
		chatMessage.setSendTime(DateUtils.getDate(System.currentTimeMillis()));
					
		// if the receive user is online, send the message directly.
		if(chatSessionMap.containsKey(receiveUserId)) {
			WebChatSession toChatSession = chatSessionMap.get(receiveUserId);
			toChatSession.getSession().getBasicRemote().sendText(JSON.toJSONString(chatMessage));
			logger.debug("Sent message successfully for web chat session:" + session.getId());
		}
		
		// save message into memory, it will be persisted into database when session close.
		messageMap.put(chatMessage.getMessageId(), chatMessage);
		
		// append message to both send and receive users
		appendMessageId(sendUserId, chatMessage.getMessageId());
		appendMessageId(receiveUserId, chatMessage.getMessageId());
		
	}
	
	/**
	 * Persist message which sent or received by user into database.
	 * Remove the message after it has be persisted successfully.
	 * 
	 * 
	 * @param session
	 */
	public static void persistMessage(Session session) {
		// get the user ID
		String userId = sessionUserIDMap.get(session.getId());
		// persist the message which sent or received by user
		if(userMessageIDMap.containsKey(userId)) {
			LinkedList<String> messageIds = userMessageIDMap.get(userId);
			for(String messageId : messageIds) {
				if(messageMap.containsKey(messageId)) {
					WebChatMessage message = messageMap.get(messageId);
					System.out.println("Persiste Message:" + message.getId());
					// remove the message after persisted successfully
					messageMap.remove(messageId);
				}
			}
			logger.info("Persisted message into database for user ID:"+userId);
		}
	}
	
	/**
	 * Remove web chat session
	 * 
	 * @param session
	 */
	public static void removeSession(Session session) {
		String sessionId = session.getId();
		String userId = sessionUserIDMap.get(sessionId);
		
		chatSessionMap.remove(userId);
		sessionUserIDMap.remove(sessionId);
		
		logger.debug("Removed a web chat session for user ID:" + userId);
	}
	
	/**
	 * get the number of online chat session
	 * 
	 * @return int
	 */
	public static int getChatSessionNumber() {
		return chatSessionMap.size();
	}
	
	/**
	 * Append message ID to user
	 * 
	 * @param userId
	 * @param messageId
	 */
	private static void appendMessageId(String userId, String messageId) {
		LinkedList<String> messageIdList = null;
		if(userMessageIDMap.containsKey(userId)) {
			messageIdList = userMessageIDMap.get(userId);
		}
		else {
			messageIdList = new LinkedList<String>();
		}
		messageIdList.add(messageId);
		userMessageIDMap.put(userId, messageIdList);
	}

	private static ChatManager getChatManager() {
		if (ApplicationContext.getInstance().hasBean("ChatManager")) {
			chatManager = (ChatManager) ApplicationContext.getInstance().getBean("ChatManager");
		} else {
			chatManager = new ChatManagerImpl();
		}
		return chatManager;
	}

}
