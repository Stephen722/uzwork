package com.uzskill.base.schedule;

import java.util.concurrent.TimeUnit;

import com.light.redis.CRUDEnum;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.skill.SkillQuery;
import com.uzskill.base.skill.bean.Skill;
import com.uzskill.base.skill.bean.SkillComment;
import com.uzskill.base.skill.bean.SkillPraise;
import com.uzskill.base.topic.TopicQuery;
import com.uzskill.base.topic.bean.Topic;
import com.uzskill.base.topic.bean.TopicComment;
import com.uzskill.base.topic.bean.TopicPraise;
import com.uzskill.base.user.UserQuery;
import com.uzskill.base.user.bean.User;

public class SynchronizeScheduleProcessor {
	
	private BaseManager baseManager;
	
	public void scheduleInit() {
		startTopicTask();
//		startSkillTask();
//		startUserTask();
	}
	
	/**
	 * Start all tasks of Topic
	 * 
	 */
	private void startTopicTask() {
		MonitorCommand<Topic> insertBM = new MonitorCommand<Topic>(TopicQuery.REDIS_TOPIC, CRUDEnum.INSERT, TopicQuery.INSERT_TOPIC, Topic.class, baseManager);
//		MonitorCommand<Topic> deleteBM = new MonitorCommand<Topic>(TopicQuery.REDIS_TOPIC, CRUDEnum.DELETE, TopicQuery.DELETE_TOPIC, Topic.class, baseManager);
//		MonitorCommand<TopicComment> insertCommentBM = new MonitorCommand<TopicComment>(TopicQuery.REDIS_TOPIC_COMMENT, CRUDEnum.INSERT, TopicQuery.INSERT_TOPIC_COMMENT, TopicComment.class, baseManager);
//		MonitorCommand<TopicComment> deleteCommentBM = new MonitorCommand<TopicComment>(TopicQuery.REDIS_TOPIC_COMMENT, CRUDEnum.DELETE, TopicQuery.DELETE_TOPIC_COMMENT, TopicComment.class, baseManager);
//		MonitorCommand<TopicPraise> insertPraiseBM = new MonitorCommand<TopicPraise>(TopicQuery.REDIS_TOPIC_PRAISE, CRUDEnum.INSERT, TopicQuery.INSERT_TOPIC_PRAISE, TopicPraise.class, baseManager);
//		MonitorCommand<TopicPraise> deletePraiseBM = new MonitorCommand<TopicPraise>(TopicQuery.REDIS_TOPIC_PRAISE, CRUDEnum.DELETE, TopicQuery.DELETE_TOPIC_PRAISE, TopicPraise.class, baseManager);
		
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertCommentBM, 120, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteCommentBM, 240, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertPraiseBM, 360, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deletePraiseBM, 480, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertBM, 600, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteBM, 720, 1800, TimeUnit.SECONDS);
		
		// Testing...
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertCommentBM, 12, 180, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteCommentBM, 24, 180, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertPraiseBM, 36, 180, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deletePraiseBM, 48, 180, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertBM, 2, 90, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteBM, 72, 180, TimeUnit.SECONDS);
	}
	
	/**
	 * Start all tasks of Skill
	 * 
	 */
	private void startSkillTask() {
		MonitorCommand<Skill> insertBM = new MonitorCommand<Skill>(SkillQuery.REDIS_SKILL, CRUDEnum.INSERT, SkillQuery.INSERT_SKILL, Skill.class, baseManager);
		MonitorCommand<Skill> deleteBM = new MonitorCommand<Skill>(SkillQuery.REDIS_SKILL, CRUDEnum.DELETE, SkillQuery.DELETE_SKILL, Skill.class, baseManager);
		MonitorCommand<SkillComment> insertCommentBM = new MonitorCommand<SkillComment>(SkillQuery.REDIS_SKILL_COMMENT, CRUDEnum.INSERT, SkillQuery.INSERT_SKILL_COMMENT, SkillComment.class, baseManager);
		MonitorCommand<SkillComment> deleteCommentBM = new MonitorCommand<SkillComment>(SkillQuery.REDIS_SKILL_COMMENT, CRUDEnum.DELETE, SkillQuery.DELETE_SKILL_COMMENT, SkillComment.class, baseManager);
		MonitorCommand<SkillPraise> insertPraiseBM = new MonitorCommand<SkillPraise>(SkillQuery.REDIS_SKILL_PRAISE, CRUDEnum.INSERT, SkillQuery.INSERT_SKILL_PRAISE, SkillPraise.class, baseManager);
		MonitorCommand<SkillPraise> deletePraiseBM = new MonitorCommand<SkillPraise>(SkillQuery.REDIS_SKILL_PRAISE, CRUDEnum.DELETE, SkillQuery.DELETE_SKILL_PRAISE, SkillPraise.class, baseManager);
		
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertCommentBM, 840, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteCommentBM, 960, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertPraiseBM, 1080, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deletePraiseBM, 1200, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertBM, 1320, 1800, TimeUnit.SECONDS);
//		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteBM, 1440, 1800, TimeUnit.SECONDS);
		
		// Testing...
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertCommentBM, 84, 180, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteCommentBM, 96, 180, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertPraiseBM, 108, 180, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deletePraiseBM, 120, 180, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertBM, 132, 180, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(deleteBM, 144, 180, TimeUnit.SECONDS);
	}
	
	/**
	 * Start all tasks of User
	 * 
	 */
	private void startUserTask() {
		MonitorCommand<User> insertBM = new MonitorCommand<User>(UserQuery.REDIS_USER, CRUDEnum.INSERT, UserQuery.INSERT_USER, User.class, baseManager);
		MonitorCommand<User> updateBM = new MonitorCommand<User>(UserQuery.REDIS_USER, CRUDEnum.UPDATE, UserQuery.INSERT_USER, User.class, baseManager);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(insertBM, 1560, 1800, TimeUnit.SECONDS);
		SynchronizeScheduleService.getInstance().scheduleAtFixedRate(updateBM, 1680, 1800, TimeUnit.SECONDS);
	}
	
	public void scheduleDesctory() {
		SynchronizeScheduleService.getInstance().shutdown();
	}

	public BaseManager getBaseManager() {
		return baseManager;
	}

	public void setBaseManager(BaseManager baseManager) {
		this.baseManager = baseManager;
	}
}
