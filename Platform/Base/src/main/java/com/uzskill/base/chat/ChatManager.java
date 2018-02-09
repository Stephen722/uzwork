package com.uzskill.base.chat;

import java.util.List;

import com.light.record.Record;
import com.light.record.ResultList;
import com.uzskill.base.chat.bean.ChatMessage;

/**
 * 在线聊天管理类
 * 
 * <p>(C) 2017 www.uzwork.com (UZWork)</p>
 * Date:  2017-10-09
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface ChatManager {
	
	/**
	 * 取得用户曾发起或接受聊天的用户列表，不包含聊天记录信息。
	 * 
	 * 
	 * @param record
	 * @return list
	 */
	public ResultList<Record> getChatUserList(Record record);
	
	/**
	 * 根据两个用户ID取得他们之间的聊天记录
	 * 
	 * @param record
	 * @return list
	 */
	public ResultList<ChatMessage> getChatMessageList(Record record);
	
	/**
	 * 删除用户的聊天对象记录。
	 * 因为一条记录中包括聊天的发起者和接收者，所以采用逻辑删除。只有当双方都删除了彼此后才做最终的物理删除，并删除彼此间的聊天记录。 
	 * 
	 * @param record
	 * @return int
	 */
	public int deleteChatUser(Record record);
	
	/**
	 * 逻辑删除聊天对象。
	 * 如果是聊天发起人删除，则更新deleteB=1；如果是接收人删除，则更新deleteB=2；在这两个更新动作中如果发现deleteB已经大于0，说明另外一方已经删除，则直接删除该记录。 
	 * 
	 * @param record
	 * @return int
	 */
	public int updateChatUserDeleteFlag(Record record);
	
	/**
	 * 插入发起聊天的两个用户信息
	 * 
	 * @param messages
	 * @return int
	 */
	public int insertChatUser(Record record);
	
	
	/**
	 * 批量插入聊天记录。
	 * 两个用户之间的初次聊天记录被保存到数据库时，系统会新增加一条记录到聊天用户表中。
	 * 
	 * @param messages
	 * @return int
	 */
	public int insertChatMessages(List<ChatMessage> messages);
	
}
