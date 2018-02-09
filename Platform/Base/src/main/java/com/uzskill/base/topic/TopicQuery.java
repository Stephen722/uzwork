package com.uzskill.base.topic;

/**
 * Constants for Topic field\Redis key\DB query
 * 
 * <p>(C) 2018 www.uzwork.com (UZWork)</p>
 * Date:  2018-01-17
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class TopicQuery {
	
	/*	
	 * Topic has the following Redis objects:
	 * 
	 * topic:id - string which for auto increase id.
	 * topic:map - hash which contains the topics, key = topic id, value = topic jSon string.
	 * topic:set - sorted set which contains the topics' id, score = topic id.
	 * topic:insert - list which contains all the newly added topics.
	 * topic:delete - list which contains all the deleted topics.
	 * 
	 * topic:comment:id - string which for auto increase id.
	 * topic:comment:insert - list which contains all the newly added topic comments. 
	 * topic:comment:delete - list which contains all the deleted topic comments.
	 * tc:{topicId} - sorted set (limited size) which contains all the comments of specified topic, score = topic comment id.
	 * 
	 * topic:praise:id - string which for auto increase id.
	 * topic:praise:insert - list which contains all the newly added topic praises. 
	 * topic:praise:delete - list which contains all the deleted topic praises.
	 * tp:{topicId} - sorted set (limited size) which contains all the praise of specified topic, score = topic praise id.
	 * 
	 * */
	public static final String REDIS_TOPIC = "topic";
	public static final String REDIS_TOPIC_COMMENT = "topic:comment";
	public static final String REDIS_TOPIC_PRAISE = "topic:praise";
	public static final String REDIS_TOPIC_ID = "topic:id";
	public static final String REDIS_TOPIC_COMMENT_ID = "topic:comment:id";
	public static final String REDIS_TOPIC_PRAISE_ID = "topic:praise:id";
	public static final String REDIS_TOPIC_SET = "topic:set";
	public static final String REDIS_TOPIC_MAP = "topic:map";
	
	// DB queries
	public static final String GET_TOPIC_BY_ID = "SK_Topic.getTopicById";
	public static final String INSERT_TOPIC = "SK_Topic.insertTopic";
	public static final String DELETE_TOPIC = "SK_Topic.deleteTopic";
	
}