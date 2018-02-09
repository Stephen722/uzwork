package com.uzskill.base.message.impl;

import java.util.List;

import com.light.common.Constants;
import com.light.record.Record;
import com.light.record.ResultList;
import com.light.utils.StringUtils;
import com.uzskill.base.common.CommonFields;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.message.MessageManager;
import com.uzskill.base.message.MessageQuery;
import com.uzskill.base.message.bean.Message;
import com.uzskill.base.message.bean.MessageSource;
import com.uzskill.base.user.UserQuery;

/**
 * 站内消息管理类
 * 
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-05-23
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class MessageManagerImpl extends BaseManager implements MessageManager {

	@Override
	public ResultList<MessageSource> getAllMessageSource(Record record) {
		ResultList<MessageSource> rsList = new ResultList<MessageSource>();
		// 分页信息
		int page = record.getIntegerValue(Constants.PAGE, 0);
		int pageSize = record.getIntegerValue(Constants.PAGE_SIZE, 10);
		
		// 取得总数
		int total = StringUtils.getInt(select(MessageQuery.GET_ALL_MESSAGE_SOURCE_AMOUNT, record.getRecordMap()));
		rsList.setTotal(total);
		
		// 取得消息列表
		List<MessageSource> msgList = selectPageList(MessageQuery.GET_ALL_MESSAGE_SOURCE, record, page, pageSize);
		rsList.setResults(msgList);
		return rsList;
	}
	
	@Override
	public ResultList<Message> getAllMessageByUser(Record record) {
		ResultList<Message> rsList = new ResultList<Message>();
		// 分页信息
		int page = record.getIntegerValue(Constants.PAGE, 0);
		int pageSize = record.getIntegerValue(Constants.PAGE_SIZE, 10);
		
		int userId = record.getIntegerValue(UserQuery.USER_ID, 0);
		// 取得总数
		int total = StringUtils.getInt(select(MessageQuery.GET_ALL_MESSAGE_BY_USER_AMOUNT, userId));
		rsList.setTotal(total);
		
		// 取得消息列表
		List<Message> msgList = selectPageList(MessageQuery.GET_ALL_MESSAGE_BY_USER, record, page, pageSize);
		rsList.setResults(msgList);
		return rsList;
	}

	@Override
	public int getUnreadMessageAmountByUser(int userId) {
		int amount = StringUtils.getInt(select(MessageQuery.GET_UNREAD_MESSAGE_AMOUNT_BY_USER, userId));
		return amount;
	}

	@Override
	public int insertMessage(Message message) {
		return insert(MessageQuery.INSERT_MESSAGE, message);
	}

	@Override
	public int insertMessageSource(MessageSource messageSource) {
		return insert(MessageQuery.INSERT_MESSAGE_SOURCE, messageSource);
	}
	
	@Override
	public int synchronizeMessage(int userId) {
		return insert(MessageQuery.SYNCHRONIZA_MESSAGE, userId);
	}

	@Override
	public int updateMessageReadFlag(Record record) {
		if(!record.hasValue(CommonFields.MESSAGE_READB)) {
			record.setValue(CommonFields.MESSAGE_READB, 1);
		}
		return update(MessageQuery.UPDATE_MESSAGE_READ_FLAG, record.getRecordMap());
	}

	@Override
	public int deleteMessage(Record record) {
		int rows = 0;
		int messageId = record.getIntegerValue(CommonFields.MESSAGE_ID, 0);
		if(messageId > 0) {
			Message message = select(MessageQuery.GET_MESSAGE_BY_ID, messageId);
			if(message != null) {
				if(message.getType() == 0) {
					rows = delete(MessageQuery.DELETE_MESSAGE, record);
				}
				else {
					rows = update(MessageQuery.UPDATE_MESSAGE_DELETE_FLAG, record);
				}
			}
		}
		return rows;
	}
	
	@Override
	public int deleteMessageSource(int messageId) {
		int rows = delete(MessageQuery.DELETE_MESSAGE_SOURCE, messageId);
		if(rows > 0) {
			delete(MessageQuery.DELETE_ALL_MESSAGE_BY_SOURCE, messageId);
		}
		return rows;
	}

}