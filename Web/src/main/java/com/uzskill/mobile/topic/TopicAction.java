package com.uzskill.mobile.topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.utils.DateUtils;
import com.uzskill.base.topic.TopicManager;
import com.uzskill.base.topic.bean.Topic;
import com.uzskill.mobile.action.MobileBaseAction;

/**
 * Topic action
 *
 * <p>(C) 2018 www.uzskill.com (UZWork)</p>
 * Date:  2018-02-09
 * 
 * @author  Stephen Yang
 * @version UZWork-Base 1.0
 */
public class TopicAction extends MobileBaseAction {
	
	private TopicManager topicManager;
	
	private static final Logger logger = LogManager.getLogger(TopicAction.class);
	
	/**
	 * Retrieve user by ID (encrypt).
	 * 
	 * Support parameters:
	 * - userId, the encrypt user id.
	 */
	public void execute() {
		try{
//			new Thread(new Runnable() {
//	            public void run() {
//	            	int i = 0;
//	                while (true) {
//	                    try {
//	                    	i++;
//	                    	Topic topic = new Topic();
//	                    	topic.setImageB(0);
//	                    	topic.setUserId(i);
//	                    	topic.setContent("This is a test topic for sequence " + i);
//	                    	topic.setCreatedTime(DateUtils.getDate(System.currentTimeMillis()));
//	                    	getTopicManager().insertTopic(topic);
//	                    	logger.info("Insert a new topic: {}", topic.getTopicId());
//	                    	Thread.sleep(200);
//	                    } catch (Exception e) {
//	                    	logger.error("Error in insert thread... {}", e);
//	                    }
//	                }
//	            }
//	        }).start();
			
//			new Thread(new Runnable() {
//	            public void run() {
//	            	int i = 0;
//	                while (i < 20) {
//	                    try {
//	                    	i++;
//	                    	int topicId = (100 * i) - 2;
//	                    	getTopicManager().deleteTopic(topicId);
//	                    	logger.debug("Delete a topic: {}", topicId);
//	                    } catch (Exception e) {
//	                    	logger.error("Error in delete thread... {}", e);
//	                    }
//	                }
//	            }
//	        }).start();
			
			this.writeJSONResponse(true, "Starting to topic process.");
		}
		catch(Exception e) {
			handleAjaxError("Failed to retrieve user", e);
		}
	}

	public TopicManager getTopicManager() {
		return topicManager;
	}

	public void setTopicManager(TopicManager topicManager) {
		this.topicManager = topicManager;
	}
}
