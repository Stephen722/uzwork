package com.uzskill.base.topic;

import java.util.List;

import com.uzskill.base.topic.bean.Topic;
import com.uzskill.base.topic.bean.TopicComment;
import com.uzskill.base.topic.bean.TopicPraise;

/**
 * Topic manager
 * 
 * <p>(C) 2016 www.uzwork.com (UZWork)</p>
 * Date:  2016-08-27
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public interface TopicManager {
	
	/**
	 * Get topic by ID
	 * 
	 * @param topicId
	 * @return topic
	 */
	public Topic getTopic(int topicId);
	
	/**
	 * Get the top 10 topics
	 * 
	 * @return
	 */
	public List<Topic> getTopTopics();
	
	/**
	 * Get the next 10 topic from start
	 * 
	 * @param start
	 * @return
	 */
	public List<Topic> getNextTopics(int start);
	
	/**
	 * Insert a new topic
	 * 
	 * @param topic
	 * @return affected rows
	 */
	public int insertTopic(Topic topic);
	
	/**
	 * Delete a topic
	 * 
	 * @param topicId
	 * @return affected rows
	 */
	public int deleteTopic(int topicId);
	
	/**
	 * Insert a new topic comment
	 * 
	 * @param topic comment
	 * @return affected rows
	 */
	public int insertComment(TopicComment comment);
	
	/**
	 * Delete a topic comment
	 * 
	 * @param topicId
	 * @param commentId
	 * @return affected rows
	 */
	public int deleteComment(int topicId, int commentId);

	/**
	 * Insert a new topic praise
	 * 
	 * @param topic praise
	 * @return affected rows
	 */
	public int insertPraise(TopicPraise praise);
	
	/**
	 * Delete a topic praise
	 * 
	 * @param topicId
	 * @param praiseId
	 * @return affected rows
	 */
	public int deletePraise(int topicId, int praiseId);
	
}
