package com.uzskill.mobile.topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.light.utils.DateUtils;
import com.uzskill.base.skill.SkillManager;
import com.uzskill.base.skill.bean.Skill;
import com.uzskill.base.skill.bean.SkillComment;
import com.uzskill.base.skill.bean.SkillPraise;
import com.uzskill.base.topic.TopicManager;
import com.uzskill.base.topic.bean.Topic;
import com.uzskill.base.topic.bean.TopicComment;
import com.uzskill.base.topic.bean.TopicPraise;
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
	private SkillManager skillManager;
	
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
//			
//			new Thread(new Runnable() {
//	            public void run() {
//	            	int i = 0;
//	                while (true) {
//	                    try {
//	                    	i++;
//	                    	TopicComment topicc = new TopicComment();
//	                    	topicc.setTopicId(i);
//	                    	topicc.setContent("This is the content for comment " + i);
//	                    	topicc.setTargetUserId(0);
//	                    	topicc.setPostUserId(i);
//	                    	topicc.setCreatedTime(DateUtils.getDate(System.currentTimeMillis()));
//	                    	getTopicManager().insertComment(topicc);
//	                    	logger.info("Insert a new topic comment: {}", topicc.getId());
//	                    	
//	                    	Thread.sleep(400);
//	                    } catch (Exception e) {
//	                    	logger.error("Error in insert thread... {}", e);
//	                    }
//	                }
//	            }
//	        }).start();
//			
//			new Thread(new Runnable() {
//	            public void run() {
//	            	int i = 0;
//	                while (true) {
//	                    try {
//	                    	i++;
//	                    	TopicPraise tp = new TopicPraise();
//	                    	tp.setTopicId(i);
//	                    	tp.setTargetUserId(0);
//	                    	tp.setPostUserId(i);
//	                    	tp.setCreatedTime(DateUtils.getDate(System.currentTimeMillis()));
//	                    	getTopicManager().insertPraise(tp);
//	                    	logger.info("Insert a new topic praise: {}", tp.getId());
//	                    	
//	                    	Thread.sleep(500);
//	                    } catch (Exception e) {
//	                    	logger.error("Error in insert thread... {}", e);
//	                    }
//	                }
//	            }
//	        }).start();
//			
//			new Thread(new Runnable() {
//	            public void run() {
//	            	int i = 0;
//	                while (true) {
//	                    try {
//	                    	i++;
//	                    	Skill skill = new Skill();
//	                    	skill.setImageB(0);
//	                    	skill.setUserId(i);
//	                    	skill.setContent("This is a test skill for sequence " + i);
//	                    	skill.setCreatedTime(DateUtils.getDate(System.currentTimeMillis()));
//	                    	getSkillManager().insertSkill(skill);
//	                    	logger.info("Insert a new skill: {}", skill.getSkillId());
//	                    	Thread.sleep(200);
//	                    } catch (Exception e) {
//	                    	logger.error("Error in insert thread... {}", e);
//	                    }
//	                }
//	            }
//	        }).start();
			
			new Thread(new Runnable() {
	            public void run() {
	            	int i = 0;
	                while (true) {
	                    try {
	                    	i++;
	                    	SkillComment topicc = new SkillComment();
	                    	topicc.setSkillId(i);
	                    	topicc.setContent("This is the content for skill comment " + i);
	                    	topicc.setTargetUserId(0);
	                    	topicc.setPostUserId(i);
	                    	topicc.setCreatedTime(DateUtils.getDate(System.currentTimeMillis()));
	                    	getSkillManager().insertComment(topicc);
	                    	logger.info("Insert a new skill comment: {}", topicc.getId());
	                    	
	                    	Thread.sleep(400);
	                    } catch (Exception e) {
	                    	logger.error("Error in insert thread... {}", e);
	                    }
	                }
	            }
	        }).start();
			
			new Thread(new Runnable() {
	            public void run() {
	            	int i = 0;
	                while (true) {
	                    try {
	                    	i++;
	                    	SkillPraise tp = new SkillPraise();
	                    	tp.setSkillId(i);
	                    	tp.setTargetUserId(0);
	                    	tp.setPostUserId(i);
	                    	tp.setCreatedTime(DateUtils.getDate(System.currentTimeMillis()));
	                    	getSkillManager().insertPraise(tp);
	                    	logger.info("Insert a new skill praise: {}", tp.getId());
	                    	
	                    	Thread.sleep(500);
	                    } catch (Exception e) {
	                    	logger.error("Error in insert thread... {}", e);
	                    }
	                }
	            }
	        }).start();
			
//			new Thread(new Runnable() {
//	            public void run() {
//	            	int i = 0;
//	                while (i < 20) {
//	                    try {
//	                    	i++;
//	                    	Thread.sleep(20000);
//	                    	int topicId = (100 * i) - 1;
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

	public SkillManager getSkillManager() {
		return skillManager;
	}

	public void setSkillManager(SkillManager skillManager) {
		this.skillManager = skillManager;
	}
	
}
