package com.uzskill.base.skill.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.light.redis.CRUDEnum;
import com.uzskill.base.manager.BaseManager;
import com.uzskill.base.manager.BaseMonitor;
import com.uzskill.base.skill.SkillManager;
import com.uzskill.base.skill.SkillQuery;
import com.uzskill.base.skill.bean.Skill;
import com.uzskill.base.skill.bean.SkillComment;
import com.uzskill.base.skill.bean.SkillPraise;
import com.uzskill.base.topic.TopicQuery;

public class SkillManagerImpl extends BaseManager implements SkillManager {
	
//	static {
//		BaseMonitor<Skill> insertBM = new BaseMonitor<Skill>(SkillQuery.REDIS_SKILL, CRUDEnum.INSERT, "", Skill.class);
//		BaseMonitor<Skill> deleteBM = new BaseMonitor<Skill>(SkillQuery.REDIS_SKILL, CRUDEnum.DELETE, "", Skill.class);
//		BaseMonitor<SkillComment> insertCommentBM = new BaseMonitor<SkillComment>(SkillQuery.REDIS_SKILL_COMMENT, CRUDEnum.INSERT, "", SkillComment.class);
//		BaseMonitor<SkillComment> deleteCommentBM = new BaseMonitor<SkillComment>(SkillQuery.REDIS_SKILL_COMMENT, CRUDEnum.DELETE, "", SkillComment.class);
//		BaseMonitor<SkillPraise> insertPraiseBM = new BaseMonitor<SkillPraise>(SkillQuery.REDIS_SKILL_PRAISE, CRUDEnum.INSERT, "", SkillPraise.class);
//		BaseMonitor<SkillPraise> deletePraiseBM = new BaseMonitor<SkillPraise>(SkillQuery.REDIS_SKILL_PRAISE, CRUDEnum.DELETE, "", SkillPraise.class);
//		
//		scheduledServive.scheduleAtFixedRate(insertBM, 420, 900, TimeUnit.SECONDS);
//		scheduledServive.scheduleAtFixedRate(deleteBM, 480, 900, TimeUnit.SECONDS);
//		scheduledServive.scheduleAtFixedRate(insertCommentBM, 540, 180, TimeUnit.SECONDS);
//		scheduledServive.scheduleAtFixedRate(deleteCommentBM, 600, 180, TimeUnit.SECONDS);
//		scheduledServive.scheduleAtFixedRate(insertPraiseBM, 660, 180, TimeUnit.SECONDS);
//		scheduledServive.scheduleAtFixedRate(deletePraiseBM, 720, 180, TimeUnit.SECONDS);
//	}
	
	@Override
	public Skill getSkill(int skillId) {
		Skill skill = getRedis().hGet(SkillQuery.REDIS_SKILL_MAP, String.valueOf(skillId), Skill.class);
		if(skill == null || skill.getSkillId() <= 0) {
			skill = select(SkillQuery.GET_SKILL_BY_ID, skillId);
			if(skill != null && skill.getSkillId() > 0) {
				getRedis().hSet(TopicQuery.REDIS_TOPIC_MAP, String.valueOf(skillId), Skill.class);
			}
		}
		return skill;
	}

	@Override
	public List<Skill> getTopSkills() {
		return getSkillList(true, 0);
	}

	@Override
	public List<Skill> getNextSkills(int start) {
		return getSkillList(false, start);
	}

	/**
	 * Get the Skill list
	 * 
	 * @param firstPage is first page
	 * @param start the start score in the Redis sorted set
	 * @return Skill list
	 */
	private List<Skill> getSkillList(boolean firstPage, int start) {
		return getRedis().getSortSetObject(SkillQuery.REDIS_SKILL_MAP, SkillQuery.REDIS_SKILL_SET, firstPage, start, Skill.class);
	}

	@Override
	public int insertSkill(Skill skill) {
		int newSkillId = (int) getRedis().increase(SkillQuery.REDIS_SKILL_ID);
		skill.setSkillId(newSkillId);
		return redisInsert(SkillQuery.REDIS_SKILL, String.valueOf(newSkillId), skill, true);
	}

	@Override
	public int deleteSkill(int skillId) {
		return redisDelete(SkillQuery.REDIS_SKILL, String.valueOf(skillId), true);
	}
	
	@Override
	public int insertComment(SkillComment comment) {
		long newComId = getRedis().increase(SkillQuery.REDIS_SKILL_COMMENT_ID);
		comment.setId(newComId);
		return redisInsertSub(SkillQuery.REDIS_SKILL_COMMENT, String.valueOf(newComId), comment, "sc:" + comment.getSkillId());
	}

	@Override
	public int deleteComment(int skillId, int commentId) {
		return redisDeleteSub(SkillQuery.REDIS_SKILL_COMMENT, String.valueOf(commentId), "sc" + skillId);
	}

	@Override
	public int insertPraise(SkillPraise praise) {
		long newPrsId = getRedis().increase(SkillQuery.REDIS_SKILL_PRAISE_ID);
		praise.setId(newPrsId);
		return redisInsertSub(SkillQuery.REDIS_SKILL_PRAISE, String.valueOf(newPrsId), praise, "sp:" + praise.getSkillId());
	}

	@Override
	public int deletePraise(int skillId, int praiseId) {
		return redisDeleteSub(SkillQuery.REDIS_SKILL_PRAISE, String.valueOf(praiseId), "sp" + skillId);
	}
}