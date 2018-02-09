package com.uzskill.base.chat.impl;

import java.util.List;

import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.uzskill.base.chat.ChatManager;
import com.uzskill.base.chat.ChatQuery;
import com.uzskill.base.chat.bean.ChatMessage;
import com.uzskill.base.manager.BaseManager;
/**
 * 在线聊天管理类
 * 
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-10-12
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class ChatManagerImpl extends BaseManager implements ChatManager {

	@Override
	public ResultList<Record> getChatUserList(Record record) {
		ResultList<Record> rsList = new ResultList<Record>();
		
		// 分页信息
		int page = record.getIntegerValue(Constants.PAGE, 0);
		int pageSize = record.getIntegerValue(Constants.PAGE_SIZE, 10);
		
		// 取得总数
		if(record.getBooleanValue(Constants.TOTAL_B, false)) {
			int total = select(ChatQuery.GET_CHAT_USER_LIST_AMOUNT, record);
			rsList.setTotal(total);
		}
				
		// 取得列表
		List<Record> chatList = selectPageList(ChatQuery.GET_CHAT_USER_LIST, record, page, pageSize);
		rsList.setResults(chatList);
		return rsList;
	}

	@Override
	public ResultList<ChatMessage> getChatMessageList(Record record) {
		ResultList<ChatMessage> rsList = new ResultList<ChatMessage>();
		
		// 分页信息
		int page = record.getIntegerValue(Constants.PAGE, 0);
		int pageSize = record.getIntegerValue(Constants.PAGE_SIZE, 20);
		
		// 取得总数
		if(record.getBooleanValue(Constants.TOTAL_B, false)) {
			int total = select(ChatQuery.GET_CHAT_USER_LIST_AMOUNT, record);
			rsList.setTotal(total);
		}
		
		// 取得列表
		List<ChatMessage> chatList = selectPageList(ChatQuery.GET_CHAT_MESSAGE_LIST, record, page, pageSize);
		rsList.setResults(chatList);
		return rsList;
	}

	@Override
	public int deleteChatUser(Record record) {
		return delete(ChatQuery.DELETE_CHAT_USER, record);
	}

	@Override
	public int updateChatUserDeleteFlag(Record record) {
		return update(ChatQuery.UPDATE_CHAT_USER_DELETE_FLAG, record);
	}

	@Override
	public int insertChatUser(Record record) {
		return insert(ChatQuery.INSERT_CHAT_USER, record);
	}

	@Override
	public int insertChatMessages(List<ChatMessage> messages) {
		return insertBatch(ChatQuery.INSERT_CHAT_MESSAGE, messages);
	}

}
