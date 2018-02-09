package com.uzskill.base.message;

import com.light.record.Record;
import com.light.record.ResultList;
import com.uzskill.base.message.bean.Message;
import com.uzskill.base.message.bean.MessageSource;

/**
 * 站内消息管理类
 * 
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-05-23
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface MessageManager {

	/**
	 * 根据源通知，取得所有源通知
	 * 
	 * @param record
	 * 
	 * @return 源通知列表
	 */
	public ResultList<MessageSource> getAllMessageSource(Record record);

	/**
	 * 根据用户Id，取得其所有通知
	 * 
	 * @param record
	 * 
	 * @return 通知列表
	 */
	public ResultList<Message> getAllMessageByUser(Record record);
	
	/**
	 * 根据用户ID，取得未读通知总数
	 * 
	 * @param userId
	 * @return 未读通知总数
	 */
	public int getUnreadMessageAmountByUser(int userId);
	
	/**
	 * 发布新通知
	 * 
	 * @param messageSource
	 * @return 影响的行数
	 */
	public int insertMessageSource(MessageSource messageSource);
	
	/**
	 * 插入新的通知
	 * 
	 * @param message
	 * @return 影响的行数
	 */
	public int insertMessage(Message message);
	
	/**
	 * 同步消息
	 * 
	 * @param userId
	 * 
	 * @return 同步的消息总数
	 */
	public int synchronizeMessage(int userId);
	
	/**
	 * 更新通知的已读标识
	 * read：0 未读， 1 已读
	 * 
	 * @param record
	 * @return 影响的行数
	 */
	public int updateMessageReadFlag(Record record);
	
	/**
	 * 用户删除自己的通知
	 * 这里做逻辑删除，待管理员从后台删除其源通知时候，系统将自动将删除其对应的已经标识删除的message
	 * 
	 * @param record 
	 * 
	 * @return 影响的行数
	 */
	public int deleteMessage(Record record);
	
	/**
	 * 管理员删除已经发布通知
	 * 
	 * @param messageId 
	 * 
	 * @return 影响的行数
	 */
	public int deleteMessageSource(int messageId);

}